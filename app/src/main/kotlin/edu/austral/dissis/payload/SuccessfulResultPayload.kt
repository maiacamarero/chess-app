package edu.austral.dissis.payload

import edu.austral.dissis.common.piece.PieceColor

data class SuccessfulResultPayload(val color: PieceColor, val piecesPayload: List<PiecePayload>) : ResultPayload