package edu.austral.dissis.client

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.client.listener.FailureResultListener
import edu.austral.dissis.client.listener.GameViewListener
import edu.austral.dissis.client.listener.InitClientListener
import edu.austral.dissis.client.listener.SuccessfulResultListener
import edu.austral.dissis.payload.FailureResultPayload
import edu.austral.dissis.payload.InitPayload
import edu.austral.dissis.payload.MovePayload
import edu.austral.dissis.payload.SuccessfulResultPayload
import edu.austral.ingsis.clientserver.Client
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import java.net.InetSocketAddress

class MyClient() {
    private lateinit var client: Client

    fun start(root: GameView){
        client = buildClient(root)
        client.connect()
        client.send(Message("request-init", Unit))
        root.addListener(GameViewListener(this))
    }

    fun applyMove(from: edu.austral.dissis.common.Position, to: edu.austral.dissis.common.Position){
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
                object : TypeReference<Message<SuccessfulResultPayload>>() {},
                SuccessfulResultListener(root)
            )
            .addMessageListener(
                "invalid-movement",
                object : TypeReference<Message<FailureResultPayload>>() {},
                FailureResultListener(root)
            )
            .build()
    }
}