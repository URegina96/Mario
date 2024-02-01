import org.w3c.dom.Image

const val TILES_IMAGE = "sprites/tiles.png"
const val HERO_FORWARD_IMAGE = "sprites/player.png"
const val HERO_BACKWARD_IMAGE = "sprites/playerl.png"
val cloudSprite = Sprite(TILES_IMAGE, si = 0, sj = 20, w = 3, h = 2)

//sj -спрайт вниз, si -спрайт вбок
data class Sprite(var src: String, val si: Int, val sj: Int, val w: Int = 1, val h: Int = 1) {
    companion object {
        fun tile(si: Int, sj: Int, w: Int = 1, h: Int = 1) = Sprite(TILES_IMAGE, si, sj, w, h) // пол
        fun bush(si: Int, sj: Int) = listOf(
            //куст
            tile(si, sj),
            tile(si + 1, sj),
            tile(si + 2, sj),
        )

        fun cloud(si: Int, sj: Int) = List(3) {// облако
            tile(si + it, sj)    //it пробегает значения от 0 до 2
        }

        fun hill(si: Int, sj: Int) = listOf(
            // холм/склон
            tile(si, sj),
            tile(si + 1, sj),
            tile(si + 2, sj),
            tile(si, sj + 1),
            tile(si + 1, sj + 1),
            tile(si + 2, sj + 1),
        )

        fun pipe(si: Int, sj: Int) = listOf(
            // труба
            tile(si, sj), //0|10
            tile(si + 1, sj), //1|10

            tile(si, sj + 1), //0|11
            tile(si + 1, sj + 1), //1|11
        )

        fun bricks(si: Int, sj: Int) = listOf(
            //кирпичики
            tile(si, sj),
        )

        fun pandoras(si: Int, sj: Int) = listOf(
            // ящик с вопросом
            tile(si, sj),
        )
        fun forwardSteps(si: Int, sj: Int) = listOf(
            //  отдельные списки для ступеней вперед
            tile(si, sj),
        )
        fun backwardSteps(si: Int, sj: Int) = listOf(
            //  отдельные списки для ступеней назад
            tile(si, sj),
        )
    }
}

object Images {
    operator fun get(src: String) = images[src]!!

    private val images = mutableMapOf<String, Image?>()

    private fun isReady() = images.values.all { it != null }

    fun load(vararg imagesSrc: String, onload: () -> Unit) {
        imagesSrc.forEach { src ->
            val image = Image()
            images[src] = null
            image.src = src
            image.onload = {
                images[src] = image
                if (isReady()) {
                    onload()
                }
            }
        }
    }
}