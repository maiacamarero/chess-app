package edu.austral.dissis.client.listener

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.payload.InitPayload
import edu.austral.dissis.payload.PiecePayload
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform

class InitClientListener(private val root : GameView) : MessageListener<InitPayload> {
    override fun handleMessage(message: Message<InitPayload>) {
        val initialState = getInitialState(message.payload)
        Platform.runLater { root.handleInitialState(initialState) }
    }

    private fun getInitialState(payload: InitPayload): InitialState {
        return InitialState(
            payload.boardSize,
            getPieces(payload),
            PlayerColor.WHITE
        )
    }

    private fun getPieces(payload: InitPayload): List<ChessPiece> {
        return adaptPiecesToChessPieces(payload.pieces)
    }

    private fun adaptPiecesToChessPieces(pieces: List<PiecePayload>): List<ChessPiece> {
        val chessPieces : MutableList<ChessPiece> = mutableListOf()
        for (piece in pieces){
            chessPieces.add(ChessPiece(piece.id, piece.color, piece.position, piece.pieceId))
        }
        return chessPieces.toList()
    }
}