import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Image
import org.w3c.dom.events.KeyboardEvent

val sourceImage = Image()
lateinit var context: CanvasRenderingContext2D
fun main() {
    window.onload =
        {
            val canvas: HTMLCanvasElement = document.getElementById("canvas") as HTMLCanvasElement
            context = canvas.getContext("2d") as CanvasRenderingContext2D
            context.scale(
                3.0,
                3.0
            )
            context.fillStyle = "#7974FF"
            context.fillRect(0.0, 0.0, 762.0, 720.0)

            sourceImage.src = TILES_IMAGE
            sourceImage.onload = {
                with(level) {
                    document.addEventListener(
                        "keydown",
                        { event ->
                            val keyboardEvent = event as KeyboardEvent
                            when (keyboardEvent.code) {
                                "ArrowLeft" -> hero.move(true)
                                "ArrowRight" -> hero.move(false)
                                "KeyZ" -> hero.jump()
                            }
                            render()
                        }
                    )
                }
            }
            Images.load(
                TILES_IMAGE,
                HERO_FORWARD_IMAGE,
                HERO_BACKWARD_IMAGE,
                GOOMBA_IMAGE
            ) {
                window.requestAnimationFrame(::update)
            }
        }
    Unit

}

fun update(timestamp: Double) {
    level.update(timestamp)
    rendeir()
    window.requestAnimationFrame(::update)
}

const val CELL_SIZE = 16.0
const val CANVAS_WIDTH = 768.0
const val CANVAS_HEIGHT = 720.0
const val BACKGROUND_COLOR = "#7974FF"
const val SCALE = 3.0
const val LEVEL_LENGTH = 213 - (CANVAS_WIDTH / (CELL_SIZE * SCALE))
fun rendeir() {
    context.clearRect(0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)
    context.fillStyle = BACKGROUND_COLOR
    context.fillRect(0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)
    level.render()
}

fun drawSprite(sprite: Sprite, x: Double, y: Double) {
    context.drawImage(
        Images[sprite.src],
        sx = sprite.si * CELL_SIZE + 1 / 3.0,
        sy = sprite.sj * CELL_SIZE + 1 / 3.0,
        sw = sprite.w * CELL_SIZE - 2 / 3.0,
        sh = sprite.h * CELL_SIZE - 2 / 3.0,
        dw = sprite.w * CELL_SIZE,
        dh = sprite.h * CELL_SIZE,
        dx = x * CELL_SIZE,
        dy = (13 - y - sprite.h) * CELL_SIZE,
    )
}

fun drawSprite(sprite: Sprite, i: Int, j: Int) {
    drawSprite(sprite, i.toDouble(), j.toDouble())
}
