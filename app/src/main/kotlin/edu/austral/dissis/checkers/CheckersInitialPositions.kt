package edu.austral.dissis.checkers

import edu.austral.dissis.checkers.factory.CheckersPieceFactory
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.piece.Piece
import edu.austral.dissis.common.piece.PieceColor
import org.yaml.snakeyaml.Yaml
import java.io.File

class CheckersInitialPositions {
    companion object {
        fun readInitialPositions(fileName: String, pieceTypes: List<String>): Map<Position, Piece> {
            val yaml = Yaml()
            val data = yaml.load(File(fileName).inputStream()) as Map<String, List<Map<String, List<Map<String, Any>>>>>
            val piecesPositions: MutableMap<Position, Piece> = mutableMapOf()

            for (pieceType in pieceTypes) {
                val pieceData = data[pieceType] as List<Map<String, List<Map<String, Any>>>>
                for (color in 0 .. 1) {
                    val positionsKey = "${PieceColor.fromInt(color).toString().lowercase()}_${pieceType}_positions"
                    val positions = pieceData[color][positionsKey]
                    positions?.forEach { positionData ->
                        val id = positionData["id"] as Int
                        val position = createPositionList(positionData["position"] as List<Int>)
                        val piece = CheckersPieceFactory.createPiece(pieceType, PieceColor.fromInt(color), id)
                        piecesPositions[position] = piece
                    }
                }
            }
            return piecesPositions
        }

        private fun createPositionList(coordinates: List<Int>): Position {
            if (coordinates.size == 2) {
                return Position(coordinates[0], coordinates[1])
            } else {
                throw IllegalArgumentException("Coordinates must have exactly two values.")
            }
        }
    }

}