"use strict";

const { MarbleGame } = require("./part1");

// tag::bigGame[]
function bigGame(smallGame, lastMarbleFactor = 100) {
  const bigGame = new MarbleGame(smallGame.players.length, smallGame.circle.lastAddedMarble * lastMarbleFactor)
  return bigGame;
}
// end::bigGame[]

module.exports = {
  bigGame
};
