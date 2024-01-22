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
            render()
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
//        dy = y * CELL_SIZE,
        dy = (13 - y - sprite.h) * CELL_SIZE,
    )
}

fun drawSprite(sprite: Sprite, i: Int, j: Int) {
    drawSprite(sprite, i.toDouble(), j.toDouble())
}
//----------------------------------------------------------------------//

val cloudSprites = listOf(
    //облако
    Sprite(TILES_IMAGE, si = 11, sj = 9),
    Sprite(TILES_IMAGE, si = 12, sj = 9),
    Sprite(TILES_IMAGE, si = 13, sj = 9),
)

fun drawCloud(i: Int, j: Int) { //облако
    drawSprite(cloudSprite, i, j)
}

val floorSprite = Sprite(TILES_IMAGE, 0, 0)
fun drawFloor() { //пол
    for (j in -1 downTo -2) {
        for (i in 0..15) {
            drawSprite(floorSprite, i, j)
        }
    }
}

val bushSprites = listOf(
    //куст
    Sprite(TILES_IMAGE, si = 11, sj = 9),
    Sprite(TILES_IMAGE, si = 12, sj = 9),
    Sprite(TILES_IMAGE, si = 13, sj = 9),
)

fun drawBush(i: Int, length: Int) {
    drawSprite(bushSprites[0], i = 11, j = 0) // left side
    for (n in 11..14) {
        drawSprite(bushSprites[1], i = 12, j = 0) // middle
        drawSprite(bushSprites[1], i = 13, j = 0) // middle
        drawSprite(bushSprites[1], i = 14, j = 0) // middle
    }
    drawSprite(bushSprites[2], i = 15, j = 0) // right side
}

val hillSprites = listOf( //склон
    Sprite(TILES_IMAGE, si = 8, sj = 10),
    Sprite(TILES_IMAGE, si = 9, sj = 10),
    Sprite(TILES_IMAGE, si = 10, sj = 10),
    Sprite(TILES_IMAGE, si = 8, sj = 11),
    Sprite(TILES_IMAGE, si = 9, sj = 11),
    Sprite(TILES_IMAGE, si = 10, sj = 11),
)

fun drawHillSection(i: Int, j: Int, size: Int) { //склон
    drawSprite(hillSprites[0], i, j)
    for (n in 1..size - 2) {
        drawSprite(hillSprites[4], i + n, j)
    }
    drawSprite(hillSprites[2], i + size - 1, j)
}

fun drawHill(i: Int, height: Int) {
    for (j in 0 until height) {
        val size = (height * 2 + 1) - 2 * j
        drawHillSection(i + j, j, size)
    }
    drawSprite(hillSprites[1], i = i + height, j = height)
}

//----------------------------------------------------------------------//
fun render() {
    drawCloud(7, 7)//облако
    drawFloor() //пол
    drawBush(11, 5) //куст
//    drawHillSection(0,0,5)//склон
    drawHill(0, 2)//склон
}
