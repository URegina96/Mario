import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Image

val sourceImage = Image()
lateinit var context: CanvasRenderingContext2D
var level: Level = Level()

fun main() {
    window.onload = {
        val canvas: HTMLCanvasElement = document.getElementById("canvas") as HTMLCanvasElement
        context = canvas.getContext("2d") as CanvasRenderingContext2D
        context.scale(
            3.0,
            3.0
        ) // увеличение всех изображений в три раза при отрисовке их на холсте  -context.scale(3.0, 3.0)
        context.fillStyle = "#7974FF"
        context.fillRect(0.0, 0.0, 762.0, 720.0)

        sourceImage.src = TILES_IMAGE
        sourceImage.onload = {
            with(level) {
                addFloor()
                val lengthBush = 5
                addBush(15 - lengthBush + 1, lengthBush)
                addCloud(7, 8, 2)
                addHill(0,2)
                render()
            }
        }
        Unit
    }
}

const val CELL_SIZE = 16.0

/*
Все изображения объектов вписываются в квадратную секту - как в тетради в клетку. Размеры клетки на исходной картинке - 16 х 16  пикселей, в игре увеличим размеры в три раза.
Весь экран - это 15 клеток в высоту и 16 клеток в ширину
 */
fun drawSprite(sprite: Sprite, x: Double, y: Double) {
    context.drawImage(
        sourceImage,
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
val cloudSprites = listOf( //облако
    Sprite(TILES_IMAGE, si = 0, sj = 20),
    Sprite(TILES_IMAGE, si = 1, sj = 20),
    Sprite(TILES_IMAGE, si = 2, sj = 20),
)
val floorSprite = Sprite(TILES_IMAGE, 0, 0)
val bushSprites = listOf( //куст
    Sprite(TILES_IMAGE, si = 11, sj = 9),
    Sprite(TILES_IMAGE, si = 12, sj = 9),
    Sprite(TILES_IMAGE, si = 13, sj = 9),
)
val hillSprites = listOf( //склон
    Sprite(TILES_IMAGE, si = 8, sj = 10),
    Sprite(TILES_IMAGE, si = 9, sj = 10),
    Sprite(TILES_IMAGE, si = 10, sj = 10),
    Sprite(TILES_IMAGE, si = 8, sj = 11),
    Sprite(TILES_IMAGE, si = 9, sj = 11),
    Sprite(TILES_IMAGE, si = 10, sj = 11),
)

