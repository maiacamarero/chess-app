package edu.austral.dissis.payload

import edu.austral.dissis.chess.gui.Position

data class MovePayload(
    val from: Position,
    val to: Position
)