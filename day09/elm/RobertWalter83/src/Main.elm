module Main exposing (..)

import Browser
import Html exposing (..)
import Dict exposing (Dict)
import Maybe exposing (..)
import Array exposing (Array)

type alias Model =
  { solution : Int }

type alias Board = 
  { left : Array Int, current : Int, right : Array Int }

type alias Game =
  { board : Board, luckyNumber : Int, players : Array Int }

-- INIT
init : Model
init =
  let
    -- COMMON
    (nPlayers, nMarbles) = inputPart2

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
  { left = Array.empty, current = 0, right = Array.empty }

initPlayers : Int -> Array Int
initPlayers nPlayers =
  Array.initialize nPlayers (\i -> 0)

play : Int -> Game -> Game
play round game =
  let
    idxPlayer = getPlayerIndex game round
    isLuckyNumber = isLucky game round
    r = if (modBy 1000 round) == 0 then Debug.log "round: " round   else round
  in
    if isLuckyNumber then
      handleLuckyMarble game round idxPlayer --  |> Debug.log "lucky:  " 
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

shiftRight : Int -> Board -> Board
shiftRight offset board =
  if offset == 0 then
    board
  else 
    let
      boardNew = 
        if Array.isEmpty board.right then
          if Array.isEmpty board.left then
            board
          else
            { left = Array.empty
            , current = Array.get 0 board.left |> withDefault 0
            , right = Array.initialize 1 (\i -> board.current)
              |> Array.append (Array.slice 1 ((Array.length board.left) + 1) board.left)
            }
        else
          { left = Array.append board.left (Array.fromList [board.current])
          , current = Array.get 0 board.right |> withDefault 0
          , right = Array.slice 1 ((Array.length board.right) + 1) board.right
          }
        

    in
      shiftRight (offset-1) boardNew

shiftLeft : Int -> Board -> Board
shiftLeft offset board =
  if offset == 0 then
    board
  else 
    let
      boardNew = 
        if Array.isEmpty board.left then
          if Array.isEmpty board.right then
            board
          else
            { left = Array.slice 0 ((Array.length board.right) - 1) board.right
              |> Array.append (Array.initialize 1 (\i -> board.current))
            , current = Array.get ((Array.length board.right) - 1) board.right |> withDefault 0
            , right = Array.empty
            } --|> Debug.log "leftShift: ____< "
        else
          { left = Array.slice 0 ((Array.length board.left) - 1) board.left 
          , current = Array.get ((Array.length board.left) - 1) board.left |> withDefault 0 
          , right = Array.append (Array.fromList [board.current]) board.right 
          }
        
    in
      shiftLeft (offset-1) boardNew
       

placeMarbleNormal : Game -> Int -> Game
placeMarbleNormal game round = 
  if round == 0 then
    let
      boardOld = game.board
      boardNew = { boardOld | current = 0 }
    in
      { game | board = boardNew }
  else 
    let
      boardNew = shiftRight 1 game.board --|> Debug.log "shifted: "
      boardNew2 = insertAt boardNew round --|> Debug.log "normal:  "
    in
      { game | board = boardNew2 }

handleLuckyMarble : Game -> Int -> Int -> Game
handleLuckyMarble game round idxPlayer =
  let
    boardNew = shiftLeft 7 game.board 
    
    (points, boardNew2) = cutOutValueAt boardNew 
    --a = Debug.log ("Player " ++ (String.fromInt idxPlayer) ++ " gets " ++ (String.fromInt (round+points)) ++ " points in round ") round
    
    playersNew = Array.indexedMap (\i val -> if i == idxPlayer then val+round+points else val) game.players
  in
    { game | board = boardNew2, players = playersNew }

  
cutOutValueAt : Board -> (Int, Board)
cutOutValueAt board =
  let
    value = board.current
    boardNew =
      if Array.isEmpty board.right then
        if Array.isEmpty board.left then
          board -- should never happen
        else
          { left = Array.empty
          , current = Array.get 0 board.left |> withDefault 0
          , right = Array.slice 1 ((Array.length board.left) + 1) board.left
          }
      else
        { left = board.left
        , current = Array.get 0 board.right |> withDefault 0
        , right = Array.slice 1 ((Array.length board.right) + 1) board.right
        }
    
  in
    (value, boardNew)
  
isLucky : Game -> Int -> Bool
isLucky game round =
  if round == 0 then 
    False
  else
    (remainderBy game.luckyNumber round) == 0

insertAt : Board -> Int -> Board
insertAt board val =
  { left = Array.append board.left (Array.fromList [board.current]) 
  , current = val
  , right = board.right
  }
  


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
  (10, 107)

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