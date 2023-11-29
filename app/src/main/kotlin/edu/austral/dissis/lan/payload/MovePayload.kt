package edu.austral.dissis.lan.payload

import edu.austral.dissis.common.Position


data class MovePayload(
    val from: Position,
    val to: Position
)