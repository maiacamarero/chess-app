package edu.austral.dissis.client.listener

import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.payload.FailureResultPayload
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform

class FailureResultListener(private val root : GameView) : MessageListener<FailureResultPayload> {
    override fun handleMessage(message: Message<FailureResultPayload>) {
        Platform.runLater { root.handleMoveResult(InvalidMove(message.payload.message)) }
    }
}