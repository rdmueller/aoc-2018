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

type alias Worker =
  { id : Int
  , workRemaining : Int
  , task : Int }

getWorkers : Int -> List Worker
getWorkers n =
  List.range 0 (n-1) |> List.map (\i -> { id = i, workRemaining = -1, task = -1})

-- INIT
init : Model
init =
  let
    -- COMMON 
    startNodesCandidates = List.map Tuple.first input |> Set.fromList 
    (startNodes, prereqs, graph) = List.foldl updateGraph (startNodesCandidates, Set.empty, Dict.empty) input 

    -- PART 1
    ordered = traverseGraph (Set.toList startNodes) prereqs graph [] 
    orderAsString = List.map (\i -> Char.fromCode i) ordered |> String.fromList

    -- PART 2
    time = traverseGraph2 (Set.toList startNodes) (getWorkers 5) prereqs graph 0
  in
    { solution = (orderAsString, time) }

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

traverseGraph2 : List Int -> List Worker -> Prereqs -> Graph -> Int -> Int
traverseGraph2 workItemsFree workers prereqs graph time =
  let
    workersFree = getAvailableWorkers workers
    timeNew = time+1
    (finishedTasks, workersNew, prereqsNew) = updatePrereqs (doWork workers) prereqs
    childrenAvailableNext = availableChildrenNext graph prereqsNew finishedTasks 
  in
  
    case workItemsFree of
      [] ->
        if (List.length workersFree) == (List.length workers) then
          time
        else
          traverseGraph2
            childrenAvailableNext
            workersNew
            prereqsNew
            graph 
            timeNew
      
      _ ->
        case workersFree of
          [] ->
            let
              workItemsFreeNew = List.append (List.filter (\i -> List.member i finishedTasks == False) workItemsFree) childrenAvailableNext |> Set.fromList |> Set.toList 
            in
              -- if no worker is available, we cycle another second
              traverseGraph2
                  workItemsFreeNew
                  workersNew
                  prereqsNew
                  graph 
                  timeNew

          _ ->
            let
              (workersUpdated, workItemsFreeUpdated) = List.foldl assignNewWorker (workers, workItemsFree) workItemsFree
              (finishedTasks2, workersNew2, prereqsNew2) = updatePrereqs (doWork workersUpdated) prereqs
              childrenAvailableNext2 = availableChildrenNext graph prereqsNew2 finishedTasks2
              workItemsFreeNew = availableWorkNext childrenAvailableNext2 workItemsFreeUpdated finishedTasks2
            in
              traverseGraph2
                workItemsFreeNew
                workersNew2
                prereqsNew2
                graph 
                timeNew

availableChildrenNext : Graph -> Prereqs -> List Int -> List Int  
availableChildrenNext graph prereqsNew finishedTasks =
  List.concat (List.map (\taskFinished -> Dict.get taskFinished graph |> withDefault [] |> without prereqsNew) finishedTasks)

availableWorkNext : List Int -> List Int -> List Int -> List Int
availableWorkNext childrenAvailableNext workItemsFree finishedTasks =
  List.append (List.filter (\i -> List.member i finishedTasks == False) workItemsFree) childrenAvailableNext |> Set.fromList |> Set.toList 

assignNewWorker : Int -> (List Worker, List Int) -> (List Worker, List Int)
assignNewWorker task (workers, workItems) =
  let
    workersFree = getAvailableWorkers workers
  in
    case workersFree of
        [] ->
          (workers, workItems)
    
        free :: rest ->
          ( List.append (List.filter (\w -> free.id /= w.id) workers) [{ id = free.id, workRemaining = (getWorkDuration task), task = task }]
          , List.filter (\i -> i /= task) workItems
          )

getWorkDuration : Int -> Int
getWorkDuration task =
  task - 4


updatePrereqs : List Worker -> Prereqs -> (List Int, List Worker, Prereqs)
updatePrereqs workers prereqs =
  let
    finishedTasks = getFinishedTasks workers
    workersNew = finishWork workers finishedTasks
    prereqsNew = List.foldl removePrereqs prereqs finishedTasks
  in
    (finishedTasks, workersNew, prereqsNew)

finishWork : List Worker -> List Int -> List Worker
finishWork workers finishedTasks =
  List.map (finishWorkItem finishedTasks) workers

finishWorkItem : List Int -> Worker -> Worker
finishWorkItem finishedTasks worker =
  if List.member worker.task finishedTasks then
    { id = worker.id, workRemaining = -1, task = -1  }
  else
    worker 
                
getAvailableWorkers : List Worker -> List Worker
getAvailableWorkers workers =
  List.filter (\w -> w.workRemaining == -1) workers
  
getFinishedTasks : List Worker -> List Int
getFinishedTasks workers = 
  List.map (\w1 -> w1.task) <| List.filter (\w2 -> w2.workRemaining == 0) workers
        
getNodesBefore : Int -> Prereqs -> Set Int
getNodesBefore node prereqs =
  Set.filter (\(after, _) -> after == node) prereqs |> Set.map (\(_, before) -> before)

doWork : List Worker -> List Worker
doWork workers =
  List.map (\w -> ({ id = w.id, workRemaining = max (w.workRemaining-1) -1, task = w.task })) workers

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