package edu.austral.dissis.server

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.common.Engine
import edu.austral.dissis.payload.*
import edu.austral.dissis.server.listener.InitServerListener
import edu.austral.dissis.server.listener.MoveListener
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.Server
import edu.austral.ingsis.clientserver.ServerBuilder
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder

class MyServer(
    private val engine: Engine,
    private val builder: ServerBuilder = NettyServerBuilder.createDefault()
) {
    private val server: Server
    init {
        server = buildServer()
        server.start()
    }

    private fun buildServer(): Server {
        return builder
            .withPort(8080)
            .withConnectionListener(InitServerListener(this))
            .addMessageListener(
                "move",
                object : TypeReference<Message<Move>>() {},
                MoveListener(this))
            .build()
    }

    fun init(clientId: String, payload: InitPayload){
        server.sendMessage(clientId, Message("init", payload))
    }

    fun broadcast(payload: MoveResultPayload){
        when(payload){
            is NewGameStatePayloadMove -> server.broadcast(Message("new-game-state", payload))
            is InvalidMovePayloadMove -> server.broadcast(Message("invalid-movement", payload))
        }
    }

    fun applyMove(move: Move): MoveResult {
        return engine.applyMove(move)
    }

    fun getEngine(): Engine{
        return engine
    }

}