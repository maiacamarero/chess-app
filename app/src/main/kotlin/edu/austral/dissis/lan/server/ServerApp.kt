package edu.austral.dissis.lan.server

import edu.austral.dissis.mychess.factory.createClassicChessGame

fun main(){
    MyServer(createClassicChessGame())
}