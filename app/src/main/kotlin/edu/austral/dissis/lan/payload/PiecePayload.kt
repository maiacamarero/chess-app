package edu.austral.dissis.lan.payload

import edu.austral.dissis.common.Position
import edu.austral.dissis.common.piece.PieceColor

data class PiecePayload(
    val id: String,
    val color: PieceColor,
    val position: Position,
    val pieceId: String
)