module Main exposing (..)

import Browser
import Html exposing (..)
import Dict exposing (Dict)
import Maybe exposing (..)
import Array exposing (Array)

type alias Model =
  { solution : Int }

type alias Board = 
  { left : Dict Int Int, current : Int, right : Dict Int Int }

type alias Game =
  { board : Board, luckyNumber : Int, players : Array Int }

-- INIT
init : Model
init =
  let
    -- COMMON
    (nPlayers, nMarbles) = inputPart1

    -- PART 1 & 2
    gameEnd = List.range 0 nMarbles |> List.foldl play (initGame 23 nPlayers) 
    highscore = Array.toList gameEnd.players |> List.maximum |> withDefault 0
    
  in
    { solution = highscore }

-- INIT GAME

initGame : Int -> Int ->  Game
initGame luckyNumber nPlayers = 
  { board = initBoard, luckyNumber = luckyNumber, players = (initPlayers nPlayers) }

initBoard : Board
initBoard =
  { left = Dict.empty, current = 0, right = Dict.empty }

initPlayers : Int -> Array Int
initPlayers nPlayers =
  Array.initialize nPlayers (\i -> 0)

-- PLAY

play : Int -> Game -> Game
play round game =
  let
    idxPlayer = getPlayerIndex game round
    isLuckyNumber = isLucky game round
  in
    if isLuckyNumber then
      handleLuckyMarble game round idxPlayer 
    else
      placeMarbleNormal game round

getPlayerIndex : Game -> Int -> Int
getPlayerIndex game round =
  let
    nPlayers = Array.length game.players
  in
    if nPlayers == 0 then
      0
    else  
      modBy nPlayers round  

getNextR : Board -> Int
getNextR board =
  Dict.get board.current board.right |> withDefault 0

getNextL : Board -> Int
getNextL board =
  Dict.get board.current board.left |> withDefault 0

shiftRight : Int -> Board -> Board
shiftRight offset board =
  if offset == 0 then
    board
  else 
    shiftRight (offset-1) { board | current = getNextR board }

shiftLeft : Int -> Board -> Board
shiftLeft offset board =
  if offset == 0 then
    board
  else 
    shiftLeft (offset-1) { board | current = getNextL board }        

placeMarbleNormal : Game -> Int -> Game
placeMarbleNormal game round = 
  if round == 0 then
    let
      boardOld = game.board
      boardNew = { boardOld | current = 0 }
    in
      { game | board = boardNew }
  else 
    { game | board = shiftRight 1 game.board |> insertAt round }

handleLuckyMarble : Game -> Int -> Int -> Game
handleLuckyMarble game round idxPlayer =
  let
    (points, boardNew) = shiftLeft 7 game.board |> cutOutValueAt 
    playersNew = Array.set idxPlayer (updatedScore idxPlayer round points game) game.players
  in
    { game | board = boardNew, players = playersNew }

updatedScore : Int -> Int -> Int -> Game -> Int
updatedScore idx round points game =
  (Array.get idx game.players |> withDefault 0) + round + points
  
cutOutValueAt : Board -> (Int, Board)
cutOutValueAt board =
  let
    lMarble = getNextL board
    rMarble = getNextR board

    rightNew = updateDict lMarble rMarble board.right
    leftNew = updateDict rMarble lMarble board.left
    value = board.current
  in
    (value, {left = leftNew, current = rMarble, right = rightNew})

updateDict : Int -> Int -> Dict Int Int -> Dict Int Int
updateDict key value dict =
  Dict.update key (\mb -> Just value) dict
  
isLucky : Game -> Int -> Bool
isLucky game round =
  if round == 0 then 
    False
  else
    (remainderBy game.luckyNumber round) == 0

insertAt : Int -> Board -> Board
insertAt val board =
  let
    lMarble = getNextL board
    rMarble = getNextR board

    rightNew = updateDict board.current val board.right |> Dict.insert val rMarble 
    leftNew = updateDict rMarble val board.left |> Dict.insert val board.current
  in 
    { left = leftNew, current = val, right = rightNew}
  

-- UPDATE (no op)

type Msg = Nothing

update : Msg -> Model -> Model
update msg model =
  case msg of
    Nothing -> model 
      
-- VIEW

view : Model -> Html Msg
view model =
  div [] 
  [ div [] [ text <| "Part1:" ++ (String.fromInt model.solution ) ]
  --, div [] [ text <| "Part2:" ++ (String.fromInt model.solution ) ]
  ]

-- MAIN

main = Browser.sandbox
  { init = init
  , update = update
  , view = view }


-- INPUT DATA

testData1 = 
  (9, 25)

testData2 = 
  (10, 1618)

testData3 = 
  (13, 7999)

testData4 = 
  (17, 1104)

testData5 = 
  (21, 6111)

testData6 = 
  (30, 5807)

inputPart1 =
  (439, 71307)

inputPart2 =
  (439, 7130700)