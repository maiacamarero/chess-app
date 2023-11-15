package edu.austral.dissis.server.listener

import edu.austral.dissis.chess.gui.BoardSize
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.gameState.GameState
import edu.austral.dissis.common.piece.PieceColor
import edu.austral.dissis.payload.InitPayload
import edu.austral.dissis.payload.PiecePayload
import edu.austral.dissis.server.MyServer
import edu.austral.ingsis.clientserver.ServerConnectionListener


class InitServerListener(private val myServer: MyServer) : ServerConnectionListener {
    override fun handleClientConnection(clientId: String) {
        myServer.getEngine().init()
        val currentGameState = myServer.getEngine().getAdapter().getLastState()
        val payload = getPayload(currentGameState)
        myServer.init(clientId, payload)
    }

    private fun getPayload(currentGameState: GameState): InitPayload {
        return InitPayload(
            getBoardSize(currentGameState.getLastBoard()),
            getPieces(currentGameState.getLastBoard())
        )
    }

    private fun getPieces(board: Board): List<PiecePayload> {
        val pieces = board.getPiecesPositions().values.toList()
        val piecesPayload : MutableList<PiecePayload> = mutableListOf()
        for (piece in pieces){
            val pieceNumber : String = piece.id.dropWhile { it.isLetter() }
            val pieceName : String = piece.id.takeWhile { it.isLetter() }
            val position = board.getPositionByPiece(piece)
            piecesPayload.add(PiecePayload(pieceNumber, getPlayerColor(piece.color), getPosition(position), pieceName))
        }
        return piecesPayload
    }

    private fun getPosition(position: Position): edu.austral.dissis.chess.gui.Position {
        return edu.austral.dissis.chess.gui.Position(position.x, position.y)
    }

    private fun getBoardSize(board: Board): BoardSize {
        return BoardSize(board.getSizeX(), board.getSizeY())
    }

    private fun getPlayerColor(color: PieceColor): PlayerColor {
        return if (color == PieceColor.WHITE) PlayerColor.WHITE else PlayerColor.BLACK
    }

    override fun handleClientConnectionClosed(clientId: String) {
        TODO("Not yet implemented")
    }

}