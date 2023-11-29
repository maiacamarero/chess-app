package edu.austral.dissis.lan.server.listener

import edu.austral.dissis.chess.gui.BoardSize
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.lan.payload.InitPayload
import edu.austral.dissis.lan.payload.PiecePayload
import edu.austral.dissis.lan.server.MyServer
import edu.austral.ingsis.clientserver.ServerConnectionListener


class InitServerListener(private val myServer: MyServer) : ServerConnectionListener {
    override fun handleClientConnection(clientId: String) {
        val currentGame = myServer.getGame()
        val payload = getPayload(currentGame)
        myServer.init(clientId, payload)
    }

    private fun getPayload(currentGame: Game): InitPayload {
        return InitPayload(
            getBoardSize(currentGame.getBoard()),
            getPieces(currentGame.getBoard()),
            currentGame.getTurn()
        )
    }

    private fun getPieces(board: Board): List<PiecePayload> {
        val pieces = board.getPiecesPositions().keys.toList()
        return pieces.map {
            val piece = board.getPiece(it)!!
            PiecePayload(piece.id, piece.color, it, piece.pieceType.name.lowercase())
        }
    }

    private fun getBoardSize(board: Board): BoardSize {
        return BoardSize(board.getSizeX(), board.getSizeY())
    }

    override fun handleClientConnectionClosed(clientId: String) {
        TODO("Not yet implemented")
    }

}