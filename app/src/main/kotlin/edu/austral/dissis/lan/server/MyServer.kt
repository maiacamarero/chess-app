package edu.austral.dissis.lan.server

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.commonValidators.Movement
import edu.austral.dissis.common.result.FailureResult
import edu.austral.dissis.common.result.FinishGameResult
import edu.austral.dissis.common.result.Result
import edu.austral.dissis.common.result.SuccessfulResult
import edu.austral.dissis.lan.payload.*
import edu.austral.dissis.lan.server.listener.InitServerListener
import edu.austral.dissis.lan.server.listener.MoveListener
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.Server
import edu.austral.ingsis.clientserver.ServerBuilder
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder

class MyServer(
    private var game: Game,
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
                object : TypeReference<Message<MovePayload>>() {},
                MoveListener(this)
            )
            .build()
    }

    fun init(clientId: String, payload: InitPayload){
        server.sendMessage(clientId, Message("init", payload))
    }

    fun broadcast(payload: MoveResultPayload){
        when(payload){
            is SuccessfulResultPayloadMove -> server.broadcast(Message("new-game-state", payload))
            is InvalidMovePayloadMove -> server.broadcast(Message("invalid-movement", payload))
            is FinishGameResultPayload -> server.broadcast(Message("finish-game", payload))
        }
    }

    fun applyMove(move: MovePayload): Result {
        val from = move.from
        val to = move.to
        return when (val result = game.move(Movement(from, to))){
            is SuccessfulResult -> {
                game = result.game
                result
            }
            is FailureResult -> result
            is FinishGameResult -> result
        }
    }

    fun getGame(): Game{
        return game
    }

}