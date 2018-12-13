module Main exposing (..)

import Browser
import Html exposing (..)
import Dict exposing (Dict)
import Maybe exposing (..)
import Array exposing (Array)

type alias Model =
  { solution : Int }

type alias Board = 
  { circle : List Int, idxCurrent : Int }

type alias Game =
  { board : Board, luckyNumber : Int, players : Array Int }

-- INIT
init : Model
init =
  let
    -- COMMON
    (nPlayers, nMarbles) = testData2

    gameEnd =  List.range 0 nMarbles |> List.foldl play (initGame 23 nPlayers) 

    -- PART 1
    highscore = Array.toList gameEnd.players |> List.maximum |> withDefault 0
  
    -- PART 2
    
  in
    { solution = highscore }

initGame : Int -> Int ->  Game
initGame luckyNumber nPlayers = 
  { board = initBoard, luckyNumber = luckyNumber, players = (initPlayers nPlayers) }

initBoard : Board
initBoard =
  { circle = [], idxCurrent = 0 }

initPlayers : Int -> Array Int
initPlayers nPlayers =
  Array.initialize nPlayers (\i -> 0)

getNewBoardIndex : Board -> Int -> Bool -> Int
getNewBoardIndex board idxRel forInsertion =
  let
    length = List.length board.circle
    idxShifted = board.idxCurrent + idxRel
  in
    if length == 0 then 
      0 
    else 
      if length == 1 then
        if (modBy 2 idxRel == 0) then 1 else 0 
      else
        if forInsertion && idxShifted == length then
          length
        else
          modBy length idxShifted

play : Int -> Game -> Game
play round game =
  let
    idxPlayer = getPlayerIndex game round
    isLuckyNumber = isLucky game round
  in
    if isLuckyNumber then
      handleLuckyMarble game round idxPlayer -- |> Debug.log "lucky:  " 
    else
      placeMarbleNormal game round -- |> Debug.log "normal: "

getPlayerIndex : Game -> Int -> Int
getPlayerIndex game round =
  let
    nPlayers = Array.length game.players
  in
    if nPlayers == 0 then
      0
    else  
      modBy nPlayers round    

placeMarbleNormal : Game -> Int -> Game
placeMarbleNormal game round = 
  if round == 0 then
    let
      boardOld = game.board
      boardNew = { boardOld | circle = [0] }
    in
      { game | board = boardNew }
  else 
    let
      idxNew = getNewBoardIndex game.board 2 True
      circleNew = insertAt game.board.circle idxNew round 
    in
      { game | board = { circle = circleNew, idxCurrent = idxNew } }

handleLuckyMarble : Game -> Int -> Int -> Game
handleLuckyMarble game round idxPlayer =
  let
    idxToRemove = getNewBoardIndex game.board -7 False
    
    (points, circleNew) = cutOutValueAt game.board.circle idxToRemove
    a = Debug.log ("Player " ++ (String.fromInt idxPlayer) ++ " gets " ++ (String.fromInt (round+points)) ++ " points in round ") round
    
    playersNew = Array.indexedMap (\i val -> if i == idxPlayer then val+round+points else val) game.players

    boardNew = { circle = circleNew, idxCurrent = idxToRemove }
  in
    { game | board = boardNew, players = playersNew }

  
cutOutValueAt : List Int -> Int -> (Int, List Int)
cutOutValueAt circle idx =
  let
    front = List.take idx circle
    back = List.drop idx circle
    toRemove = List.head back |> withDefault 0
    backToKeep = List.tail back |> withDefault []
  in
    (toRemove, List.append front backToKeep)
  
isLucky : Game -> Int -> Bool
isLucky game round =
  if round == 0 then 
    False
  else
    (remainderBy game.luckyNumber round) == 0

insertAt : List Int -> Int -> Int -> List Int
insertAt circle idx val =
  let
    front = List.take idx circle  
    back = List.drop idx circle 
  in
    List.append (List.append front [val]) back
  

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

inputData =
  (439, 71307)