package edu.austral.dissis.lan.payload

import edu.austral.dissis.common.piece.PieceColor

data class SuccessfulResultPayloadMove(
    val color: PieceColor,
    val piecesPayload: List<PiecePayload>
) : MoveResultPayload