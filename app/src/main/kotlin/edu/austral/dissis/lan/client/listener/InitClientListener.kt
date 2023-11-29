package edu.austral.dissis.lan.client.listener

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.common.piece.PieceColor
import edu.austral.dissis.lan.payload.InitPayload
import edu.austral.dissis.lan.payload.PiecePayload
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
            getPlayerColor(payload.turn)
        )
    }

    private fun getPlayerColor(turn: PieceColor): PlayerColor {
        return if (turn == PieceColor.WHITE){
            PlayerColor.WHITE
        }else PlayerColor.BLACK
    }

    private fun getPieces(payload: InitPayload): List<ChessPiece> {
        return adaptPiecesToChessPieces(payload.pieces)
    }

    private fun adaptPiecesToChessPieces(pieces: List<PiecePayload>): List<ChessPiece> {
        val chessPieces : MutableList<ChessPiece> = mutableListOf()
        for (piece in pieces){
            chessPieces.add(ChessPiece(piece.id, adaptPieceColorToPlayerColor(piece.color), adaptMyPositionToPosition(piece.position), piece.pieceId))
        }
        return chessPieces.toList()
    }

    private fun adaptMyPositionToPosition(position: edu.austral.dissis.common.Position): Position {
        return Position(position.x, position.y)
    }

    private fun adaptPieceColorToPlayerColor(color: PieceColor): PlayerColor {
        return if (color == PieceColor.WHITE){
            PlayerColor.WHITE
        }else{
            PlayerColor.BLACK
        }
    }
}