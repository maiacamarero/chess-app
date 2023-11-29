package edu.austral.dissis.lan.client.listener

import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.lan.payload.InvalidMovePayloadMove
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform

class InvalidMoveListener(private val root : GameView) : MessageListener<InvalidMovePayloadMove> {
    override fun handleMessage(message: Message<InvalidMovePayloadMove>) {
        Platform.runLater { root.handleMoveResult(InvalidMove(message.payload.message)) }
    }
}