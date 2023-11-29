package edu.austral.dissis.lan.client

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.common.Position
import edu.austral.dissis.lan.client.listener.*
import edu.austral.dissis.lan.payload.*
import edu.austral.ingsis.clientserver.Client
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import java.net.InetSocketAddress

class MyClient {
    private lateinit var client: Client

    fun start(root: GameView){
        client = buildClient(root)
        client.connect()
        client.send(Message("init", Unit))
        root.addListener(GameViewListener(this))
    }

    fun applyMove(from: Position, to: Position){
        client.send(Message("move", MovePayload(from, to)))
    }

    private fun buildClient(root: GameView): Client {
        return NettyClientBuilder
            .createDefault()
            .withAddress(InetSocketAddress(8080))
            .addMessageListener(
                "init",
                object : TypeReference<Message<InitPayload>>() {},
                InitClientListener(root)
            )
            .addMessageListener(
                "new-game-state",
                object : TypeReference<Message<SuccessfulResultPayloadMove>>() {},
                SuccessfulResultListener(root)
            )
            .addMessageListener(
                "invalid-movement",
                object : TypeReference<Message<InvalidMovePayloadMove>>() {},
                InvalidMoveListener(root)
            )
            .addMessageListener(
                "finish-game",
                object : TypeReference<Message<FinishGameResultPayload>>() {},
                FinishGameListener(root)
            )
            .build()
    }
}