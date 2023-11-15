package edu.austral.dissis.server.listener

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.payload.InvalidMovePayloadMove
import edu.austral.dissis.payload.PiecePayload
import edu.austral.dissis.payload.NewGameStatePayloadMove
import edu.austral.dissis.server.MyServer
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class MoveListener(private val myServer: MyServer) : MessageListener<Move> {
    override fun handleMessage(message: Message<Move>) {
        when (val result = myServer.applyMove(message.payload)) {
            is NewGameState -> myServer.broadcast(getNewGameStatePayload(result))
            is InvalidMove -> myServer.broadcast(InvalidMovePayloadMove(result.reason))
            is GameOver -> TODO()
        }
        myServer.applyMove(message.payload)
    }

    private fun getNewGameStatePayload(game: NewGameState): NewGameStatePayloadMove {
        return NewGameStatePayloadMove(
            game.currentPlayer,
            adaptChessPiecesToPiecesPayload(game.pieces)
        )
    }

    private fun adaptChessPiecesToPiecesPayload(pieces: List<ChessPiece>): List<PiecePayload> {
        val piecesPayload : MutableList<PiecePayload> = mutableListOf()
        for (piece in pieces){
            piecesPayload.add(PiecePayload(piece.id, piece.color, piece.position, piece.pieceId))
        }
        return piecesPayload
    }
}