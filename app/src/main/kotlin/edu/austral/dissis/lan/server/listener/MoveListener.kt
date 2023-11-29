package edu.austral.dissis.lan.server.listener

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.result.FailureResult
import edu.austral.dissis.common.result.FinishGameResult
import edu.austral.dissis.common.result.SuccessfulResult
import edu.austral.dissis.lan.payload.*
import edu.austral.dissis.lan.server.MyServer
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class MoveListener(private val myServer: MyServer) : MessageListener<MovePayload> {
    override fun handleMessage(message: Message<MovePayload>) {
        when (val result = myServer.applyMove(message.payload)) {
            is SuccessfulResult -> myServer.broadcast(getSuccessfulResultPayload(result.game))
            is FailureResult -> myServer.broadcast(InvalidMovePayloadMove(result.reason))
            is FinishGameResult -> myServer.broadcast(FinishGameResultPayload(result.winner))
        }
        //myServer.applyMove(message.payload)
    }

    private fun getSuccessfulResultPayload(game: Game): SuccessfulResultPayloadMove {
        return SuccessfulResultPayloadMove(
            game.getTurn(),
            adaptPiecesToPiecesPayload(game.getBoard())
        )
    }

    private fun adaptPiecesToPiecesPayload(board: Board): List<PiecePayload> {
        val pieces = board.getPiecesPositions().keys.toList()
        return pieces.map {
            val piece = board.getPiece(it)!!
            PiecePayload(piece.id, piece.color, it, piece.pieceType.name.lowercase())
        }
    }
}