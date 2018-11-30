#!/usr/bin/env ts-node

function greeter(person:String):String {
    return "Hello, " + person;
}

let user:String = "John User";

console.log (greeter(user));