/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package edu.austral.dissis.mychess

import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.common.Adapter
import edu.austral.dissis.mychess.factory.createClassicChessGame
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage


fun main() {
    launch(ChessApp::class.java)
}

class ChessApp : Application() {
    private val gameAdapter = Adapter(createClassicChessGame())
    private val imageResolver = CachedImageResolver(DefaultImageResolver())

    companion object {
        const val GameTitle = "Chess"
    }


    override fun start(primaryStage: Stage) {
        primaryStage.title = GameTitle

        val root = GameView(imageResolver)
        primaryStage.scene = Scene(root)

        primaryStage.show()
    }
}