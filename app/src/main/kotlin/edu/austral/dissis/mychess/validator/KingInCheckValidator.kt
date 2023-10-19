package edu.austral.dissis.mychess.validator

import edu.austral.dissis.mychess.Position
import edu.austral.dissis.mychess.board.Board
import edu.austral.dissis.mychess.piece.PieceColor

class KingInCheckValidator {
    // chequeo en todos los movimientos si mi rey esta en jaque, si es asi solo me puedo mover para
    // defenderlo sino es movimiento invalido. Si no puedo defenderlo mas es jaque mate entonces
    // game over.
    fun isKingInCheck(board: Board, kingPosition: Position, kingColor: PieceColor): Boolean {
        val opponentColor : PieceColor = if (kingColor == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE
        val opponentPieces = board.getPiecesPositions().values.filter { it.getPieceColor() == opponentColor }
        for (opponentPiece in opponentPieces){
            for (opponentPieceRule in opponentPiece.getRuleList()){
                if (opponentPieceRule.isValidRule(board, Movement(opponentPiece, kingPosition))::class.simpleName.equals("SuccessfulRuleResult")){
                    return true
                }
            }
        }
        return false
    }
}