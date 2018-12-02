module Main exposing (..)

import Browser
import Html exposing (..)
import Set exposing (Set)
import Dict exposing (Dict)
import Maybe exposing (Maybe)
import List.Extra exposing (..)

inputList : List String
inputList =
  [ "mvgowxqubnhaefjslkjlrptzyi", "pvgowlqubnhaefmslkjdrpteyi", "ovgowoqubnhaefmslkjnrptzyi", "cvgowxqubnrxefmslkjdrptzyo", "cvgowxqubnhaefmsokjdrprzyf", "cvgowxqubnhjeflslkjgrptzyi", "cvgowxqvbnhaefmslkhdrotzyi", "hvgowxqmbnharfmslkjdrptzyi", "cvgoaxqubqhaefmslkjdrutzyi", "cvxowxqdbnhaefmslkjdgptzyi", "cvgikxqubnhaefmslkjdrptzyz", "cvgnwxqubnhaqfjslkjdrptzyi", "cqgowxqubnhaecmslkjgrptzyi", "cvpowxqucnhaefmslkjdrptzyz", "fvuoexqubnhaefmslkjdrptzyi", "svgowxqubnhaefmsvkjdrttzyi", "cvgowxqubnhaefmblkjdfpbzyi", "cvkoyxqubnhaefsslkjdrptzyi", "bvgowxqublhaefmslkjdrptzfi", "xvgewxqubnhaefmslkjdrztzyi", "cvgowxqubzhaefmslkkrrptzyi", "cvgowxqubnhaefmslkudruuzyi", "cvgowxqubnhaefmvlkjdrptwyl", "cvgoyxqubnhaefmslkjvrotzyi", "cvgowxoubnhaewmslkjdrpbzyi", "cvgowxgubnhaefmslijdrptzxi", "lvgowxqkbnhaefmslkjdrptzqi", "xvgowxqubyhaefmflkjdrptzyi", "wvnowxgubnhaefmslkjdrptzyi", "cvgowxguwnhaefhslkjdrptzyi", "cvgowfquxnhaefmdlkjdrptzyi", "cvgywxqubnuaefmsldjdrpfzyi", "cvkowxqzbrhaefmslkjdrptzyi", "cviowxzubnhaefmslkjdrptqyi", "cvgowxqubnhaefmsozjdrptzyc", "cvglwxuubnhaewmslkjdrptzyi", "cvgowxquknhaebmsfkjdrptzyi", "vvgowxqubnhaesmslkjdrptzri", "cvgowxoubndaefmslkjdrftzyi", "cvgowxqubghaefmslkjdeptzyw", "cvgowxqubnhaetmhlkjdrpvzyi", "cvgowmquunhaefmslkjdrptzyt", "cvgooxqpbniaefmslkjdrptzyi", "cvgowxqubnhaeumslkjdkptiyi", "cvgrwxqsbnhaemmslkjdrptzyi", "cvrowxqubnhaefmslkjdrctcyi", "dvgcwxqubnhaefmslkjdrptzyq", "cugowxqubnhasfmmlkjdrptzyi", "cwgowxqobzhaefmslkjdrptzyi", "cvgowxquwnhaefmulkjdrptbyi", "nvgowxqmbnhaefmslyjdrptzyi", "cvgowxqubniakvmslkjdrptzyi", "cvyowxqubnhaefmslejdrptzyx", "cvgobxqubghaefeslkjdrptzyi", "cvgowxiubnhaebmslkjdfptzyi", "cvgosbqubnhaefmslkvdrptzyi", "cvgpwxqubnhaefvslkjdrptzyh", "cvgowxqubnyaefmslgjdsptzyi", "cvgowxqubnhaefmslkjdrprzzp", "cvgowxqubwhaemmslkjdrpazyi", "cvgowxqpbnhaemmslkjdrpczyi", "cvgoqxqubnhaelmslkjdrptzye", "cvgowxqubnhaefmslbjdrttzvi", "cvgowxqubnhlefmslkvurptzyi", "cvgowxqujngaefmslktdrptzyi", "cvgowxqubnhaefmsckjdcwtzyi", "cvcowxqubnhaetmslkjorptzyi", "jvnowxqubnhaefmslkjdrptzyf", "cygowxqkbnhaefmslejdrptzyi", "cvmowxqubnhaefmslkjdritzoi", "cvgowxqubnpaefmslkjdrpnnyi", "cvgowxqubnhaefmolkjdrpnzyy", "uvgowxoubnhaefmslkjdrptzvi", "cvgowxbabehaefmslkjdrptzyi", "cvgokxqubnhaefmsckjdrjtzyi", "cvgoxwqubahaefmslkjdrptzyi", "cvgowxqusnhaefmslijdrptyyi", "cvgowxqubmhaeqmslkxdrptzyi", "cvgouxhubnhaefmslkjdrjtzyi", "cvgowxqubnhaefmslrjdqptzyk", "cvgowxiublhaefsslkjdrptzyi", "cvgowxqubnxgefmslkadrptzyi", "ovgowxqugshaefmslkjdrptzyi", "cvgowxquznhaeemslsjdrptzyi", "cvkowxqubnhaeomslkjdeptzyi", "cvgvwxqubxhaefmslkjdrptzyu", "cvglwxqybnhaefmslkjdrptzyb", "cvgowxqubnlfwfmslkjdrptzyi", "cvaowxqubnhaefmslkjdrvtzbi", "cvgowxqubnrmefaslkjdrptzyi", "cvgowxqubnhaefmsnkjdfpwzyi", "cvgawxqmbnhaefmsykjdrptzyi", "chgowmqubnhaefmslkjdrptwyi", "cogowxqubnhaefmslkjxrptzri", "cvgohxqubnoaesmslkjdrptzyi", "cvdowxqubnhaofmslkjdrpvzyi", "vvgowrqubnhaefmslkjdrpthyi", "cvgowxquknhuefmslkjdoptzyi", "cvyowxeubnhaefmslhjdrptzyi", "cvglwxqubnhaefmslkjdrptdyq", "cvgowxqubnhaefmsikgdrptayi", "cvgowxqubnhaefjhlkjdrpczyi", "cvgzwxkubnhaefmslkjdjptzyi", "cxgowxqubnhaefmslkjdrptwyy", "cvgowxqubnhaefeslkjdrmqzyi", "cvgowxvubnhaefmilijdrptzyi", "cvgowxqzbthaeomslkjdrptzyi", "cvgowhqubndaefmglkjdrptzyi", "cvgowxvubnhaeamylkjdrptzyi", "cvgowiqubnhgefmslkjdrctzyi", "cvgowxqubchaefmslksdritzyi", "cvgowxqubnhaefmsnkjdreyzyi", "cvgowxqubihaefmslkgdrutzyi", "cvgowxqjbnhaeamslkjdrptzwi", "cvgowxzubnhaefmsxkjdrrtzyi", "cvgowxqubyhaetmslnjdrptzyi", "cvgowxquhnhaebmslkjdxptzyi", "cvgowxqubnhanfmslujdxptzyi", "cvgowxqublhnefaslkjdrptzyi", "cvgmwxqtbnhaefmslkjsrptzyi", "jvgowxqubnhaeamslkjdrpmzyi", "cvgowxqubhiaefmsljjdrptzyi", "svgowxqubnhaefmswkjdrpozyi", "cvgowxqebnhaeqmslkjdiptzyi", "cveowxqubnhayzmslkjdrptzyi", "cvglwxqubnhaefmxlkjdiptzyi", "cvgowkqubdhaefmszkjdrptzyi", "cvgowxkxbnhaeffslkjdrptzyi", "cugowxqubnnaefmslujdrptzyi", "cqgowxwubnhaepmslkjdrptzyi", "cvgowxqubnhayfmmlkjwrptzyi", "cvgowxquenhaefmsskxdrptzyi", "cvgowxqubnhiefmsrkjdtptzyi", "mvgowxkubnhaefmjlkjdrptzyi", "cvgowkquunhaefmglkjdrptzyi", "cvgowxqubqhaexmslgjdrptzyi", "jvgowxqubnhaefmslkjddptlyi", "cvgiwxqubnhaefmslkjdpptmyi", "czgowxqubntaevmslkjdrptzyi", "cvgotmqubnhaefmslkjdrpazyi", "cvgowxtubnhaefmslkqdtptzyi", "cvbowxqhnnhaefmslkjdrptzyi", "cvgowkqubshaefmstkjdrptzyi", "cvgowqqrbnaaefmslkjdrptzyi", "cvgoixqubnhaefmslkjdrpmryi", "cvgoxxqubnhaeimsxkjdrptzyi", "cvgowxqubzhaebmslkjyrptzyi", "cjgewxqubnhaefsslkjdrptzyi", "cvgowxqdbnkaefmslwjdrptzyi", "cvgowxqzbnhaeamslkjdrftzyi", "cvgoixqubnsaewmslkjdrptzyi", "cvgswxqubnhaxfmslkjdrptzni", "cvwowxmubnhgefmslkjdrptzyi", "cvggwxqubnhaefmslqjdbptzyi", "cvgzwxqjbnhaefaslkjdrptzyi", "cvgowzqubnharfmspkjdrptzyi", "cvgowxqubnhawfmslkjdeptzyb", "cvuowequbnhaefmslkjdrntzyi", "gvgowxqubnxaefmslkjdrjtzyi", "cvgowxqubnhmetmsldjdrptzyi", "cvgowxqubnhamfmsqkjdrptyyi", "cvgoqxqubnhaefmslkjtrpazyi", "cvgoexqubhhaefmslkjdrhtzyi", "cvgowwqubnhaeflslkjdrptzyf", "cvgowlpubnhaefmslkjdrptvyi", "cvgowxouunhaebmslkjdrptzyi", "cvdowhqubnhaefmslijdrptzyi", "cvgowxqubnkatfmslkjdrhtzyi", "cvgowxqpbnhxeumslkjdrptzyi", "cvgowxqubnhaefmsukjjrptzyn", "cvgowxqubnhmefmslzjdrvtzyi", "cvtowxqubihaefmclkjdrptzyi", "chgowcqubnhayfmslkjdrptzyi", "cvguwxqubnhaefmblkjarptzyi", "cvgowoqubnhaefmsikjdrytzyi", "cvgkwxqubnhaefmslkjdrptchi", "cvhowxqubnhaefmslkjdrvlzyi", "cvlowxfubnhaefmslkjkrptzyi", "cvgowxqubhhaefoslkjdrytzyi", "cvgowxsubqhaefmslpjdrptzyi", "cvgowxpubnhaefmslhjdrptzyb", "cvgowxqubnhrefmjlkddrptzyi", "cvgowxqubnhaxfmykkjdrptzyi", "mvgowxqubnhakfmslkjdrptnyi", "cwgowxqubnhaffmslkadrptzyi", "chgowxquwnhaefmslsjdrptzyi", "cvgowxqubnhaefmslkjdwpnsyi", "cvgawxqubnhaefmslkldyptzyi", "cvgowxqubnhiefmslkjdiprzyi", "cvgkqxqubnhaefcslkjdrptzyi", "cvgovoqubnhaefmslkjdrpuzyi", "cvgowxqubnhaefmszkjdrjtzyk", "cvgopxqubnhaefmslkjdqpnzyi", "cvgtwxqubnhaefmslkjnrptzri", "cvgowxqurnhaedmslfjdrptzyi", "cvpowxqubnhaefmswkjdrltzyi", "cvgowxqujnpaefmslkjdrptdyi", "cvgowgqubnhzifmslkjdrptzyi", "lvgowxqubnhaenmslkjdbptzyi", "ebgowxqubnhaeymslkjdrptzyi", "cvgowxtubqhaefmslkedrptzyi", "cvgowxqubshaesmslkjdrptryi", "cvgowxqubnhaefmflkjmrpkzyi", "cvgowxqubngaefmslkjdrytzgi", "cvgowxqubnhaefmslklhzptzyi", "cveowxqubnhgefmslkjdrpezyi", "cvgowxqubnhaeomslkjdrqtzym", "cvgowxqubzhaefmslwjdrptfyi", "cmgowxqubnhaefmsdkjdrptzui", "cvlowxqubnhaefmslsjdrptzwi", "cvhowxpubnhaefmslkjhrptzyi", "cveosxqurnhaefmslkjdrptzyi", "cvgowxqubnhaefgsdkjdrptjyi", "cvgvwxqubnhaefmslzjdmptzyi", "cviowxqubnhalfmslkjdrptzyr", "cvgowxqubchqefmslkjdrptzoi", "cvgownqubnhaefmsyktdrptzyi", "cvgywxqubnuaefmslkjdrpfzyi", "cvgobxqunnhaefmslkjdrptzbi", "cvgowxqubshaefgslkjdrxtzyi", "cvghwxqubnhaefmslkjdrbtmyi", "cvhowxqubnhaefmslkjdrpnzys", "cvgowxqubnmaefmslejdrptzyq", "cvmrwxqubnhaefmslkjdrpzzyi", "cvgowxqubshaefmslkfdrptzyu", "cvgowqqubnhaefmslkodrpjzyi", "cvgnwnquknhaefmslkjdrptzyi", "cvgowxquxnhacfmflkjdrptzyi", "ovgowxqubnhaefmslkjmrmtzyi", "cvgowxqubneaefmslkedrptzqi", "cvgowxqubphweflslkjdrptzyi", "cvgowxqudnhaefmplkjdrptdyi", "cvwowxbubnhaefmslkjurptzyi", "cvgowxtubnhaefmslkjdrwwzyi", "cvgowxqubnhkefmslajdrptzyn", "cvgowxqxbphaefmslkjdrptzsi", "cvgowxquenhaefmslmjwrptzyi", "zvgowdqubnhaeftslkjdrptzyi", "csgowxqubnhgefmslkjdrptzyy", "cvgolxqubahaefmslkjdrpvzyi", "cvgoqxquhwhaefmslkjdrptzyi", "cvgawxqubghaefmsrkjdrptzyi", "cvgozxqubnhaefmslkwdfptzyi", "cvgowxqubnhaefmslhjdkptzzi", "cvnowxqubnhaefmsqkjdrptqyi", "cvpowxqubnhaefmslkpdrptdyi", "cvgowxoubnhaermslkjdrctzyi", "cvgowxqubnheefmslkjdrctzyr", "cvgowxqunnhaqfhslkjdrptzyi", "cvgowxqulnhaefmslrjdrntzyi" ]

-- MODEL

type alias Model = 
  { checkSum : Int
  , idWithMinDistance : String }

-- INIT

init : Model
init =
  { checkSum = calcCheckSum inputList
  , idWithMinDistance = findIdWithMinDistance inputList }

-- COMMON UTIL

toCharLists : List String -> List (List Char)
toCharLists input =
  List.map String.toList input

-- PART 1

calcCheckSum : List String -> Int
calcCheckSum input =
    let
      listOfCharLists = toCharLists input
      dicts = List.map (List.foldl countOfLetters Dict.empty) listOfCharLists
      (factor1, factor2) = List.foldl calcFactors (0, 0) dicts
    in
      factor1 * factor2


countOfLetters : Char -> Dict Char Int -> Dict Char Int
countOfLetters letter dictLetterToCount = 
  Dict.update 
    letter
    (\existingCount ->
        case existingCount of
            Just count ->
                Just (count + 1)

            Maybe.Nothing ->
                Just 1
    )
    dictLetterToCount 

calcFactors : Dict Char Int -> (Int, Int) -> (Int, Int)
calcFactors dict (factor1, factor2) =
  let
    hasPair = Dict.values dict |> List.member 2
    hasTriple = Dict.values dict |> List.member 3
    incFactor1 = if hasPair then 1 else 0
    incFactor2 = if hasTriple then 1 else 0
  in
    (factor1 + incFactor1, factor2 + incFactor2)
  
-- PART 2 

findIdWithMinDistance : List String -> String
findIdWithMinDistance input =
  let
    asCharLists = toCharLists input
    comparisonList = lift2 compareCharLists asCharLists asCharLists
    resultList = List.filter filterForResult comparisonList
  in
    case resultList of
      [] ->
        ""
      (distance, id) :: xs ->
        id

compareCharLists : List Char -> List Char -> (Bool, String)
compareCharLists input1 input2 =
  if input1 == input2 then
    (False, (String.fromList input1))
  else
    let 
      (distance, id) = List.foldl foldDistanceAndString (0, "") (List.map2 compareChars input1 input2)
    in
      if distance == 1 then
        (True, id)
      else
        (False, id)


compareChars : Char -> Char -> (Int, Char)
compareChars a b = 
  if a == b then (0, a) else (1, '_')

foldDistanceAndString : (Int, Char) -> (Int, String) -> (Int, String)
foldDistanceAndString (distance, letter) (distanceSum, id) = 
  (distanceSum + distance, if distance == 0 then id ++ (String.fromChar letter) else id)

filterForResult : (Bool, String) -> Bool
filterForResult (isResult, _) =
  isResult


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
  [ div [] [ text <| "CheckSum:" ++ (String.fromInt model.checkSum ) ]
  , div [] [ text <| "id with distance 1 (same characters only): " ++ model.idWithMinDistance ]
  ]

-- MAIN

main = Browser.sandbox
  { init = init
  , update = update
  , view = view }
