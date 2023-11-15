package edu.austral.dissis.payload

import edu.austral.dissis.chess.gui.PlayerColor

data class NewGameStatePayloadMove(
    val color: PlayerColor,
    val piecesPayload: List<PiecePayload>
) : MoveResultPayload