package edu.austral.dissis.client

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.chess.gui.Position
import edu.austral.dissis.client.listener.InvalidMoveListener
import edu.austral.dissis.client.listener.GameViewListener
import edu.austral.dissis.client.listener.InitClientListener
import edu.austral.dissis.client.listener.NewGameStateListener
import edu.austral.dissis.payload.InvalidMovePayloadMove
import edu.austral.dissis.payload.InitPayload
import edu.austral.dissis.payload.MovePayload
import edu.austral.dissis.payload.NewGameStatePayloadMove
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
                object : TypeReference<Message<NewGameStatePayloadMove>>() {},
                NewGameStateListener(root)
            )
            .addMessageListener(
                "invalid-movement",
                object : TypeReference<Message<InvalidMovePayloadMove>>() {},
                InvalidMoveListener(root)
            )
            .build()
    }
}