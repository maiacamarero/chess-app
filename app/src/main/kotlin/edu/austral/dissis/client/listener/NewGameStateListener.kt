package edu.austral.dissis.client.listener

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.piece.PieceColor
import edu.austral.dissis.payload.PiecePayload
import edu.austral.dissis.payload.NewGameStatePayloadMove
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform

class NewGameStateListener(private val root : GameView) : MessageListener<NewGameStatePayloadMove> {
    override fun handleMessage(message: Message<NewGameStatePayloadMove>) {
        val moveResult = getMoveResult(message.payload.color, message.payload.piecesPayload)
        Platform.runLater{ root.handleMoveResult(moveResult) }
    }

    private fun getMoveResult(color: PlayerColor, piecesPayload: List<PiecePayload>): MoveResult {
        return NewGameState(adaptPiecesToChessPieces(piecesPayload), color)
    }

    private fun adaptPiecesToChessPieces(pieces: List<PiecePayload>): List<ChessPiece> {
        val chessPieces : MutableList<ChessPiece> = mutableListOf()
        for (piece in pieces){
            chessPieces.add(ChessPiece(piece.id, piece.color, piece.position, piece.pieceId))
        }
        return chessPieces.toList()
    }

    private fun getPiecePosition(position: Position): edu.austral.dissis.chess.gui.Position {
        return edu.austral.dissis.chess.gui.Position(position.x, position.y)
    }

    private fun getPlayerColor(color: PieceColor): PlayerColor {
        return if (color == PieceColor.WHITE) PlayerColor.WHITE else PlayerColor.BLACK
    }


}