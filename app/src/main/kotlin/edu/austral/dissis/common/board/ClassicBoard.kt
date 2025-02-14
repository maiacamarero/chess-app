package edu.austral.dissis.common.board

import edu.austral.dissis.common.Position
import edu.austral.dissis.common.exception.PieceNotFoundException
import edu.austral.dissis.common.piece.Piece

data class ClassicBoard(
    private val sizeX : Int,
    private val sizeY : Int,
    private val piecesPositions : Map<Position, Piece>,
    private val positions : List<Position> ) :
    Board {

    override fun getSizeX(): Int {
        return sizeX
    }

    override fun getSizeY(): Int {
        return sizeY
    }

    override fun getPiecesPositions(): Map<Position, Piece> {
        return piecesPositions
    }

    override fun isInBounds(position: Position): Boolean {
        return (position.x in 1..sizeX) && (position.y in 1..sizeY)
    }

    override fun getPositionByPiece(piece: Piece) : Position {
        for (i in piecesPositions.keys){
            if (piece == piecesPositions[i]){
                return i
            }
        }
        throw PieceNotFoundException("Piece not found")
    }

    override fun getPiece(position: Position): Piece?{
        if (piecesPositions.containsKey(position)){
            return piecesPositions[position]
        }
        return null
    }

    override fun getPositions(): List<Position> {
        return positions
    }

    override fun getOccupiedPositions(): List<Piece>{
        return piecesPositions.values.toList()
    }
}