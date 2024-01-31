import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.KeyboardEvent
object KeyboardInput {
    fun isLeftPressed() =  "ArrowLeft" in pressedKeys

    fun isRightPressed() = "ArrowRight" in pressedKeys

    fun isUpPressed() = "ArrowUp" in pressedKeys

    fun isDownPressed() = "ArrowDown" in pressedKeys

    fun isJumpPressed() = "KeyZ" in pressedKeys

    fun isRunPressed() = "KeyX" in pressedKeys
}
