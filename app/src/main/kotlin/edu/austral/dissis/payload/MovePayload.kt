package edu.austral.dissis.payload

import edu.austral.dissis.common.Position


data class MovePayload(val from: Position, val to: Position)