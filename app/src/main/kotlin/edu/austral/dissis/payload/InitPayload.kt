package edu.austral.dissis.payload

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.piece.PieceColor

data class InitPayload(
    val board: Board,
    val pieces: List<PiecePayload>,
    val color: PieceColor
)