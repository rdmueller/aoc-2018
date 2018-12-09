module Main exposing (..)

import Browser
import Html exposing (..)
import Dict exposing (Dict)
import Maybe exposing (..)
import Set exposing (Set)

type alias Model =
  { solution : (String, Int) }

type alias Graph =
  Dict Int (List Int)

type alias Prereqs =
  Set (Int, Int)

type alias StartNodes =
  Set Int

-- INIT
init : Model
init =
  let
    -- PART 1
    startNodesCandidates = List.map Tuple.first input |> Set.fromList 
    (startNodes, prereqs, graph) = List.foldl updateGraph (startNodesCandidates, Set.empty, Dict.empty) input 

    ordered = traverseGraph (Set.toList startNodes) prereqs graph [] 
    orderAsString = List.map (\i -> Char.fromCode i) ordered |> String.fromList
  in
    { solution = (orderAsString, 0) }

updateGraph : (Int, Int) -> (StartNodes, Prereqs, Graph) -> (StartNodes, Prereqs, Graph)
updateGraph (k, v) (startNodes, prereqs, graph) =
  (Set.remove v startNodes, Set.insert (v, k) prereqs, updateGraphAt (k, v) graph )

updateGraphAt : (Int, Int) -> Graph -> Graph
updateGraphAt (k, v) graph =
  if Dict.member k graph then
    Dict.update k (updateChildren v) graph
  else
    Dict.insert k [v] graph 

updateChildren : Int -> Maybe (List Int) -> Maybe (List Int)
updateChildren newChild mbChildren =
  Maybe.map (\children -> List.append children [newChild]) mbChildren
  
traverseGraph : List Int -> Prereqs -> Graph -> List Int -> List Int
traverseGraph stackNodes prereqs graph orderedSteps =
  case stackNodes of
    [] ->
      -- done  
      orderedSteps
      
    n :: rest ->
      let
        prereqsNew = removePrereqs n prereqs
        children = Dict.get n graph |> withDefault [] |> without prereqsNew 
      in
        traverseGraph 
          (List.append rest children |> List.sort) 
          prereqsNew
          graph 
          (List.append orderedSteps [n])
        
getNodesBefore : Int -> Prereqs -> Set Int
getNodesBefore node prereqs =
  Set.filter (\(after, _) -> after == node) prereqs |> Set.map (\(_, before) -> before)

removePrereqs : Int -> Prereqs -> Prereqs
removePrereqs prereqToRemove prereqs =
  Set.filter (\(after, before) -> before /= prereqToRemove) prereqs 

without : Prereqs -> List Int -> List Int
without prereqs children =
  List.filter (hasPrereq prereqs) children

hasPrereq : Prereqs -> Int -> Bool
hasPrereq prereqs child =
  Set.toList prereqs |> List.all (\(after, before) -> after /= child)

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
  [ div [] [ text <| "Part1:" ++ (Tuple.first model.solution ) ]
  , div [] [ text <| "Part2:" ++ (String.fromInt <| Tuple.second model.solution ) ]
  ]

-- MAIN

main = Browser.sandbox
  { init = init
  , update = update
  , view = view }


-- INPUT DATA


parse : String -> List (Int, Int)
parse data =
  let
    dataAsList = String.lines data
    tuples = List.map parseLineAt dataAsList |> List.filter (\(c1, c2) -> if c1 /= -1 && c2 /= -1 then True else False )
  in
    tuples


parseLineAt : String -> (Int, Int)
parseLineAt line =
  let
    char1 = String.dropLeft 5 line |> String.toList
    char2 = String.dropLeft 36 line |> String.toList
  in
    case (char1, char2) of
      (c1 :: c1s, c2 :: c2s) ->
        (Char.toCode c1, Char.toCode c2)

      (_, _) ->
        (-1, -1)  


test1 =
  parse testData1

input =
  parse inputData


testData1 = 
  """Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin."""

inputData =
  """Step H must be finished before step C can begin.
Step R must be finished before step S can begin.
Step F must be finished before step M can begin.
Step S must be finished before step Z can begin.
Step X must be finished before step Z can begin.
Step Q must be finished before step G can begin.
Step M must be finished before step Z can begin.
Step G must be finished before step V can begin.
Step N must be finished before step Z can begin.
Step I must be finished before step J can begin.
Step Z must be finished before step T can begin.
Step B must be finished before step A can begin.
Step L must be finished before step T can begin.
Step E must be finished before step D can begin.
Step U must be finished before step Y can begin.
Step W must be finished before step O can begin.
Step C must be finished before step V can begin.
Step O must be finished before step J can begin.
Step T must be finished before step D can begin.
Step A must be finished before step J can begin.
Step J must be finished before step V can begin.
Step D must be finished before step P can begin.
Step P must be finished before step V can begin.
Step K must be finished before step Y can begin.
Step V must be finished before step Y can begin.
Step D must be finished before step V can begin.
Step W must be finished before step Y can begin.
Step I must be finished before step U can begin.
Step B must be finished before step V can begin.
Step U must be finished before step D can begin.
Step M must be finished before step C can begin.
Step H must be finished before step Z can begin.
Step B must be finished before step P can begin.
Step X must be finished before step N can begin.
Step G must be finished before step O can begin.
Step I must be finished before step C can begin.
Step B must be finished before step K can begin.
Step J must be finished before step Y can begin.
Step M must be finished before step E can begin.
Step T must be finished before step J can begin.
Step O must be finished before step P can begin.
Step P must be finished before step Y can begin.
Step R must be finished before step D can begin.
Step N must be finished before step W can begin.
Step H must be finished before step G can begin.
Step I must be finished before step K can begin.
Step L must be finished before step O can begin.
Step X must be finished before step K can begin.
Step B must be finished before step J can begin.
Step Z must be finished before step C can begin.
Step Z must be finished before step O can begin.
Step F must be finished before step U can begin.
Step F must be finished before step Q can begin.
Step U must be finished before step K can begin.
Step T must be finished before step V can begin.
Step O must be finished before step D can begin.
Step R must be finished before step B can begin.
Step U must be finished before step J can begin.
Step U must be finished before step A can begin.
Step T must be finished before step K can begin.
Step F must be finished before step N can begin.
Step J must be finished before step P can begin.
Step Z must be finished before step A can begin.
Step L must be finished before step A can begin.
Step R must be finished before step V can begin.
Step F must be finished before step Y can begin.
Step C must be finished before step A can begin.
Step H must be finished before step P can begin.
Step A must be finished before step K can begin.
Step C must be finished before step J can begin.
Step X must be finished before step T can begin.
Step L must be finished before step D can begin.
Step L must be finished before step J can begin.
Step N must be finished before step B can begin.
Step Z must be finished before step B can begin.
Step G must be finished before step P can begin.
Step E must be finished before step P can begin.
Step L must be finished before step P can begin.
Step T must be finished before step Y can begin.
Step S must be finished before step U can begin.
Step M must be finished before step U can begin.
Step D must be finished before step K can begin.
Step L must be finished before step U can begin.
Step F must be finished before step S can begin.
Step N must be finished before step L can begin.
Step W must be finished before step P can begin.
Step G must be finished before step I can begin.
Step L must be finished before step Y can begin.
Step D must be finished before step Y can begin.
Step K must be finished before step V can begin.
Step B must be finished before step O can begin.
Step P must be finished before step K can begin.
Step R must be finished before step C can begin.
Step G must be finished before step L can begin.
Step O must be finished before step A can begin.
Step M must be finished before step L can begin.
Step E must be finished before step K can begin.
Step F must be finished before step C can begin.
Step B must be finished before step L can begin.
Step O must be finished before step T can begin.
Step S must be finished before step O can begin."""