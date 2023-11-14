package edu.austral.dissis.client.listener

import edu.austral.dissis.chess.gui.GameEventListener
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.client.MyClient
import edu.austral.dissis.common.Position
import edu.austral.ingsis.clientserver.Client

class GameViewListener(private val client: MyClient) : GameEventListener {
    override fun handleMove(move: Move) {
        client.applyMove(Position(move.from.row, move.from.column), Position(move.to.row, move.to.column))
    }
}