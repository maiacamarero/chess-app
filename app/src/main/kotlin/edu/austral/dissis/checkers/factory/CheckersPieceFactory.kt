package edu.austral.dissis.checkers.factory

import edu.austral.dissis.checkers.validators.CaptureMovementValidator
import edu.austral.dissis.checkers.validators.CheckerQueenMovementValidator
import edu.austral.dissis.checkers.validators.RegularCheckerMovementValidator
import edu.austral.dissis.common.PieceFactory
import edu.austral.dissis.common.commonValidators.*
import edu.austral.dissis.common.piece.Piece
import edu.austral.dissis.common.piece.PieceColor
import edu.austral.dissis.common.piece.PieceType

class CheckersPieceFactory : PieceFactory{

    override fun createPiece(type: String, color: PieceColor, id: Int): Piece {
        return CheckersPieceFactory.createPiece(type, color, id)
    }

    companion object {
        fun createPiece(pieceType: String, color: PieceColor, id: Int): Piece {
            return when (pieceType) {
                "pawn" -> createPawn(color, id)
                "queen" -> createQueen(color, id)
                else -> throw IllegalArgumentException("Tipo de pieza desconocido: $pieceType")
            }
        }

        private fun createQueen(color: PieceColor, id: Int): Piece {
            return Piece("queen$id", color, PieceType.QUEEN,
                OrValidator(listOf(
                AndValidator(
                    listOf(DiagonalMovementValidator(), CheckerQueenMovementValidator(), CaptureMovementValidator(), ToPositionIsEmpty())
                ),
                AndValidator(
                    listOf(DiagonalMovementValidator(), CheckerQueenMovementValidator(), ToPositionIsEmpty()))
            )))
        }

        private fun createPawn(pieceColor: PieceColor, id: Int): Piece {
            return Piece(
                "pawn$id", pieceColor, PieceType.PAWN,
                OrValidator(listOf(
                    AndValidator(
                        listOf(DiagonalMovementValidator(), CaptureMovementValidator(), ToPositionIsEmpty())
                    ),
                    AndValidator(
                        listOf(DiagonalMovementValidator(), RegularCheckerMovementValidator(), ToPositionIsEmpty()))
                ))

            )
        }
    }
}