module Main exposing (..)

import Browser
import Html exposing (..)
import Dict exposing (Dict)
import Maybe exposing (..)
import Set exposing (Set)
import Array exposing (Array)
import List.Extra exposing (..)

type alias Model =
  { solution : (Int, Int) }

type alias Coord =
  (Int, Int)

type alias Distance =
  Dict Coord Int

type alias CoordToDistances =
  Dict Coord Distance

type alias CoordToPointDistance =
  Dict Coord (Int, List Coord)

-- INIT
init : Model
init =
  let
    (xMax, yMax) = findDim coordinates
    
    -- part1
    
    coordMap = List.foldl (calcShortestDistances2 coordinates) Dict.empty (createGrid (xMax, yMax)) 
    appearances = List.map (\v -> Tuple.second v) (Dict.values coordMap) 
    
    finiteAreas = Dict.foldl (findFiniteAreas (xMax, yMax)) Dict.empty coordMap 
    
    part1 = Dict.values finiteAreas |> List.filter (\mbval -> mbval /= Maybe.Nothing) |> List.map (\mbval -> Maybe.withDefault -1 mbval) |> List.foldl (\val cur -> max val cur) -1
    
    -- part2
    part2 = 0
  in
    { solution = (part1, part2)}

findFiniteAreas : Coord -> Coord -> (Int, List Coord) -> Dict Coord (Maybe Int) -> Dict Coord (Maybe Int)
findFiniteAreas coordMax coordKey (_, points) areas =
  let
    fEdge = isEdge coordKey coordMax 
    pointsLength = List.length points 
  in
    if fEdge && pointsLength /= 2 then
      -- this means we found that our areas are infinite
      List.foldl declareInfinite areas points 
    else
      case points of
        [] ->    
          areas 
        point :: [] ->
          -- only count as part of area if coord belongs to one point
          if Dict.member point areas then
            Dict.update point updateSize areas 
          else 
            Dict.insert point (Just 1) areas 
        point :: rest -> 
          areas 

declareInfinite : Coord -> Dict Coord (Maybe Int) -> Dict Coord (Maybe Int) 
declareInfinite point areas =
  if Dict.member point areas then
    Dict.update point (\mbVal -> Just (Maybe.Nothing)) areas
  else
    Dict.insert point Maybe.Nothing areas


  
updateSize : Maybe (Maybe Int) -> Maybe (Maybe Int)
updateSize mbmbSize =
  case mbmbSize of
      Just mbSize ->
        case mbSize of
            Just size ->
              Just (Just (size+1))  
        
            Maybe.Nothing ->
              Just (Maybe.Nothing)

      Maybe.Nothing ->
           Maybe.Nothing

createGrid : Coord -> List Coord
createGrid (xMax, yMax) =
   lift2 (\a b -> (a, b)) (List.range 0 xMax) (List.range 0 yMax) 

isEdge : Coord -> Coord -> Bool
isEdge (x, y) (xMax, yMax) =
  x == 0 || y == 0 || x == xMax || y == yMax

calcShortestDistances2 : List Coord -> Coord -> CoordToPointDistance -> CoordToPointDistance
calcShortestDistances2 points coord coordMap =
  let
    (_, coordMapNew) = List.foldl (calcShortestDistance coord) (-1, coordMap) points 
  in
    coordMapNew
  

calcShortestDistance : Coord -> Coord -> (Int, CoordToPointDistance) -> (Int, CoordToPointDistance)
calcShortestDistance (xC, yC) (xP, yP) (dMin, coordMap) =
  let
    dVal = abs (xP-xC) + abs (yP-yC)  
  in
    if dMin == -1 then
      -- first point we check => add new entry
      (dVal, Dict.insert (xC, yC) (dVal, [(xP, yP)]) coordMap)
    else
      if dVal > dMin then 
        -- this distance is too big, nothing to do
        (dMin, coordMap)
      else
        if dVal == dMin then
          -- we found the same distance => add an entry to current list
          (dVal, Dict.update (xC, yC) (updateValue (xP, yP))  coordMap)
        else
          (dVal, Dict.insert (xC, yC) (dVal, [(xP, yP)]) coordMap )
        
updateValue : Coord -> Maybe (Int, List Coord) -> Maybe (Int, List Coord)
updateValue coord mbValue =
  case mbValue of
    Just (dMin, coords) ->
      Just (dMin, List.append coords <| List.singleton coord)

    Maybe.Nothing ->
      Maybe.Nothing
      
findDim : List Coord -> Coord
findDim coords =
  List.foldl 
    (\(x, y) (xMax, yMax) -> 
      (Basics.max x xMax, Basics.max y yMax)
    ) 
    (0, 0)
    coords

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
  [ div [] [ text <| "Part1:" ++ (String.fromInt <| Tuple.first model.solution ) ]
  , div [] [ text <| "Part2:" ++ (String.fromInt <| Tuple.second model.solution ) ]
  ]

-- MAIN

main = Browser.sandbox
  { init = init
  , update = update
  , view = view }


-- INPUT DATA

coordinates : List Coord
coordinates  =
  String.lines inputData |> 
    List.map 
    (\line -> 
      let
        coordAsArray = String.split ", " line |> Array.fromList
      in
        (toIntAt coordAsArray 0, toIntAt coordAsArray 1)
    )

test1 : List Coord
test1  =
  String.lines testData1 |> 
    List.map 
    (\line -> 
      let
        coordAsArray = String.split ", " line |> Array.fromList
      in
        (toIntAt coordAsArray 0, toIntAt coordAsArray 1)
    )

-- iterate over grid and get distance to each coord: only remember the shortest ones and add them to the corresponding dictionary!


toIntAt : Array String -> Int -> Int
toIntAt array index =
  withDefault -1 <| String.toInt <| withDefault "-1" <| Array.get index array


testData1 = 
  """1, 1
1, 6
8, 3
3, 4
5, 5
8, 9"""

inputData =
  """83, 153
201, 74
291, 245
269, 271
222, 337
291, 271
173, 346
189, 184
170, 240
127, 96
76, 46
92, 182
107, 160
311, 142
247, 321
303, 295
141, 310
147, 70
48, 41
40, 276
46, 313
175, 279
149, 177
181, 189
347, 163
215, 135
103, 159
222, 304
201, 184
272, 354
113, 74
59, 231
302, 251
127, 312
259, 259
41, 244
43, 238
193, 172
147, 353
332, 316
353, 218
100, 115
111, 58
210, 108
101, 175
185, 98
256, 311
142, 41
68, 228
327, 194"""