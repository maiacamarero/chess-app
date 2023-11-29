package edu.austral.dissis.lan.payload

import edu.austral.dissis.chess.gui.BoardSize
import edu.austral.dissis.common.piece.PieceColor

data class InitPayload(
    val boardSize: BoardSize,
    val pieces: List<PiecePayload>,
    val turn: PieceColor
)