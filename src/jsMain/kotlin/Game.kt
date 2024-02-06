import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Image
import org.w3c.dom.events.KeyboardEvent

val sourceImage = Image()
lateinit var context: CanvasRenderingContext2D
private var entities = setOf<Entity>()
fun main() {  // ... инициализация и отрисовка игровой сцены
    window.onload =
        { //Между вызовом main и моментом, когда document будет готов, есть задержка по времени. window.onload - это колбэк - код, который будет исполнен после того, как происходит какое-то событие
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
                with(level) { //отрисвока элементов уровня
                    render()
                }
                level.addHero() // после загрузки картинок и отрисовки всех деталей добавляется герой
            }
            Images.load(
                TILES_IMAGE,
                HERO_FORWARD_IMAGE,
                HERO_BACKWARD_IMAGE
            ) {
                window.requestAnimationFrame(::update)
            }
}
    Unit

}
fun update(timestamp: Double){ //  Эта функция будет обновлять состояние игры и показывать обновленное изображение и снова запрашивать  следующий кадр анимации
    level.update(timestamp)
    rendeir()
    window.requestAnimationFrame(::update)
}
const val CANVAS_WIDTH = 762.0
const val CANVAS_HEIGHT = 720.0
const val BACKGROUND_COLOR = "#7974FF"
fun rendeir() { // После того как картинка загружена, можно нарисовать сцену - для этого добавили функцию render
    context.clearRect(0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT) // Чтобы в начале функции render очистить окно
    context.fillStyle = BACKGROUND_COLOR
    context.fillRect(0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)
    level.render()
}

const val CELL_SIZE =
    16.0 //Все изображения объектов вписываются в квадратную секту - как в тетради в клетку. Размеры клетки на исходной картинке - 16 х 16  пикселей, в игре увеличим размеры в три раза. Весь экран - это 15 клеток в высоту и 16 клеток в ширину

fun drawSprite(sprite: Sprite, x: Double, y: Double) { // функция drawSprite, которая рисует спрайт от точки с координатами (x, y), нарисовать  изображения объектов в 2D игре, которые накладываются поверх  фонового изображения
    context.drawImage(
        Images[sprite.src],
//        sourceImage,
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

fun drawSprite(
    sprite: Sprite,
    i: Int,
    j: Int
) { // статичные элементы игре  располагаются в игре строго по клеточкам, поэтому давайте добавим еще версию drawSprite  с целочисленными координатами  - индексами (i, j)
    drawSprite(sprite, i.toDouble(), j.toDouble())
}
