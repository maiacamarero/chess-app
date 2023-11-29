package edu.austral.dissis.lan.payload

import edu.austral.dissis.common.piece.PieceColor

class FinishGameResultPayload(val winner: PieceColor) : MoveResultPayload