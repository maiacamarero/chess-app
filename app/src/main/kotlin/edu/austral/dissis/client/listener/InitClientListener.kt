package edu.austral.dissis.client.listener

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.piece.PieceColor
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
            getBoardSize(payload),
            getPieces(payload),
            getPlayerColor(payload.color)
        )
    }

    private fun getPieces(payload: InitPayload): List<ChessPiece> {
        return adaptPiecesToChessPieces(payload.pieces)
    }

    private fun adaptPiecesToChessPieces(pieces: List<PiecePayload>): List<ChessPiece> {
        val chessPieces : MutableList<ChessPiece> = mutableListOf()
        for (piece in pieces){
            chessPieces.add(ChessPiece(piece.id, getPlayerColor(piece.color), getPiecePosition(piece.position), piece.pieceId))
        }
        return chessPieces.toList()
    }

    private fun getPiecePosition(position: Position): edu.austral.dissis.chess.gui.Position {
        return edu.austral.dissis.chess.gui.Position(position.x, position.y)
    }

    private fun getPlayerColor(color: PieceColor): PlayerColor {
        return if (color == PieceColor.WHITE) PlayerColor.WHITE else PlayerColor.BLACK
    }

    private fun getBoardSize(payload: InitPayload): BoardSize {
        return BoardSize(payload.board.getSizeX(), payload.board.getSizeY())
    }
}