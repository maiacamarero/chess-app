package edu.austral.dissis.payload

import edu.austral.dissis.chess.gui.BoardSize

data class InitPayload(
    val boardSize: BoardSize,
    val pieces: List<PiecePayload>
)