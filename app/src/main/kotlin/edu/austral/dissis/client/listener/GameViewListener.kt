package edu.austral.dissis.client.listener

import edu.austral.dissis.chess.gui.GameEventListener
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.client.MyClient

class GameViewListener(private val client: MyClient) : GameEventListener {
    override fun handleMove(move: Move) {
        client.applyMove(move.from, move.to)
    }
}