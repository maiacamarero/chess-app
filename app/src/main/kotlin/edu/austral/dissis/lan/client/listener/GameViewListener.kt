package edu.austral.dissis.lan.client.listener

import edu.austral.dissis.chess.gui.GameEventListener
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.lan.client.MyClient
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.commonValidators.Movement

class GameViewListener(private val client: MyClient) : GameEventListener {
    override fun handleMove(move: Move) {
        val movement = Movement(Position(move.from.row, move.from.column), Position(move.to.row, move.to.column))
        client.applyMove(movement.from, movement.to)
    }
}