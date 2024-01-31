import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.KeyboardEvent
object KeyboardInput {
    fun isUpPressed() = "ArrowUp" in pressedKeys

    fun isDownPressed() = "ArrowDown" in pressedKeys

    fun isJumpPressed() = "KeyA" in pressedKeys

    fun isRunPressed() = "KeyD" in pressedKeys
}
