package edu.austral.dissis.lan.client.listener

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.common.piece.PieceColor
import edu.austral.dissis.lan.payload.PiecePayload
import edu.austral.dissis.lan.payload.SuccessfulResultPayloadMove
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform

class SuccessfulResultListener(private val root : GameView) : MessageListener<SuccessfulResultPayloadMove> {
    override fun handleMessage(message: Message<SuccessfulResultPayloadMove>) {
        val result = getMoveResult(message.payload.color, message.payload.piecesPayload)
        Platform.runLater{ root.handleMoveResult(result) }
    }

    private fun getMoveResult(color: PieceColor, piecesPayload: List<PiecePayload>): NewGameState {
        return NewGameState(adaptPiecesToChessPieces(piecesPayload), adaptPieceColorToPlayerColor(color))
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