package edu.austral.dissis.lan.client.listener

import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.common.piece.PieceColor
import edu.austral.dissis.lan.payload.FinishGameResultPayload
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform

class FinishGameListener(private val root : GameView) : MessageListener<FinishGameResultPayload> {
    override fun handleMessage(message: Message<FinishGameResultPayload>) {
        Platform.runLater {
            root.handleMoveResult(GameOver(adaptPieceColorToPlayerColor(message.payload.winner)))
        }
    }

    private fun adaptPieceColorToPlayerColor(color: PieceColor): PlayerColor {
        return if (color == PieceColor.WHITE){
            PlayerColor.WHITE
        }else{
            PlayerColor.BLACK
        }
    }
}