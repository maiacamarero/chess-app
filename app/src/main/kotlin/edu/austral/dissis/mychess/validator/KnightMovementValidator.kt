package edu.austral.dissis.mychess.validator

import edu.austral.dissis.common.Position
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.result.FailureResult
import edu.austral.dissis.common.result.SuccessfulResult
import edu.austral.dissis.common.result.ValidatorResult
import edu.austral.dissis.common.commonValidators.Movement
import edu.austral.dissis.common.commonValidators.Validator
import kotlin.math.abs

class KnightMovementValidator: Validator {
    override fun validateMovement(board: Board, movement: Movement): ValidatorResult {
        val pieceActualPosition : Position = board.getPositionByPiece(movement.piece)
        // el caballo se mueve 1 en filas y 2 en columnas o viceversa.
        if (isValidForKnight(pieceActualPosition, movement.finalPosition)){
            // come siempre
            return SuccessfulResult("Valid movement")
        }
        return FailureResult("Invalid movement")
    }

    private fun isValidForKnight(currentPositions: Position, finalPosition : Position) : Boolean{
        val difRow : Int = abs(currentPositions.y - finalPosition.y)
        val difCol : Int = abs(currentPositions.x - finalPosition.x)
        return (difRow == 1 && difCol == 2) || (difRow == 2 && difCol == 1)
    }
}