package edu.austral.dissis.payload

import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.chess.gui.Position

data class PiecePayload(
    val id: String,
    val color: PlayerColor,
    val position: Position,
    val pieceId: String
)