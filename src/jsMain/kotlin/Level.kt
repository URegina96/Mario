import kotlinx.browser.document
import org.w3c.dom.events.KeyboardEvent

typealias IntPair = Pair<Int, Int>
typealias IntTriple = Pair<Pair<Int, Int>, Int>

class Level(
    //отвечает за то как часто используются  спрайты  на уровне
    val floor: List<IntRange> = emptyList(),
    val bushes: List<IntPair> = emptyList(),
    val hills: List<IntPair> = emptyList(),
    val clouds: List<IntTriple> = emptyList(),
    val pipes: List<IntPair> = emptyList(),
    val bricks: List<IntTriple> = emptyList(),  // Дорожки из кирпичей  i x j x длина
    val pandoras: List<IntPair> = emptyList(),   // Ящик Пандоры - это блок с вопросом
    //отвечает за внешний вид, т.е. под какими индаксами спрайты берутся   (замена кода "игровые элементы" из Game.kt)
    val forwardSteps: List<IntTriple> = emptyList(), // отдельные списки для ступеней вперед
    val backwardSteps: List<IntTriple> = emptyList(), // отдельные списки для ступеней назад
    val cloudSprites: List<Sprite> = emptyList(),
    val bushSprites: List<Sprite> = emptyList(),
    val hillSprites: List<Sprite> = emptyList(),
    val pipeSprites: List<Sprite> = emptyList(),
    val floorSprite: Sprite,
    val brickSprite: List<Sprite> = emptyList(),
    val pandorasSprite: List<Sprite> = emptyList(),
    val forwardStepsSprite: List<Sprite> = emptyList(),
    val backwardStepsSprite: List<Sprite> = emptyList(),
//                val wallSprite: Sprite,
) {

    private var entities = setOf<Entity>()
    var windowX: Double = 0.0 // начальное положение окна
    private var gameTime: Double = Double.NaN
    val hero = Hero()
    private var backgroundEntities = setOf<Entity>()
    private var staticEntities = setOf<Entity>()
    private var dynamicEntities = setOf<Actor>()

    init {
        floor.forEach { range -> // пол
            for (j in -1 downTo -2) {
                for (i in range) {
                    addFloor(i, j)
                }
            }
        }

        clouds.forEach { (indices, size) -> // облако
            val (i, j) = indices
            addCloud(i, j, size)
        }

        bushes.forEach { (i, size) ->  // куст
            addBush(i, size)
        }

        hills.forEach { (i, size) -> // склон
            addHill(i, size)
        }
        pipes.forEach { (i, j) -> // трубы
            addPipes(i, j)
        }
        bricks.forEach { (indices, length) ->  // кирпичи
            val (i, j) = indices
            addBricks(i, j, length)
        }
        pandoras.forEach { (i, j) ->  // ящик с вопросом
            addPandoras(i, j)
        }
        forwardSteps.forEach { (indices, height) ->   // отдельные списки для ступеней вперед
            val (i, j) = indices
            addForwardSteps(i, j, height)
        }
        backwardSteps.forEach { (indices, height) ->  // отдельные списки для ступеней назад
            val (i, j) = indices
            addBackwardSteps(i, j, height)
        }
    }
    fun render() {
        for (entity in backgroundEntities) {
            renderEntity(entity)
        }

        for (entity in staticEntities) {
            renderEntity(entity)
        }

        for (entity in dynamicEntities) {
            renderEntity(entity)
        }
        renderEntity(hero)
    }

    private fun renderEntity(entity: Entity) {
        for (entity in entities) {
            if (entity.x + entity.sprite.w > windowX && entity.x < windowX + 16) {
                drawSprite(entity.sprite, entity.x - windowX, entity.y)
                drawSprite(hero.sprite,hero.x-windowX, hero.y)
            }
        }
    }

    //----------------------------------------------------игровые элементы---------------------------------------------------------//
    fun addFloor(i: Int, j: Int) {  //пол
        entities += Entity(i, j, floorSprite)
    }

    fun addBush(i: Int, size: Int) {  //куст
        entities += Entity(i, 0, bushSprites[0])
        for (n in i + 1..i + size - 2) {
            entities += Entity(n, 0, bushSprites[1]) // middle
        }
        entities += Entity(i + size - 1, 0, bushSprites[2]) // right side
    }

    fun addCloud(i: Int, j: Int, size: Int) { //облако
        entities += Entity(i, j, cloudSprites[0]) // left side
        for (n in i + 1..i + 1 + size) {
            entities += Entity(n, j, cloudSprites[1]) // middle
        }
        entities += Entity(i + size + 2, j, cloudSprites[2]) // right side
    }

    fun drawHillSection(i: Int, j: Int, size: Int) {//склон
        for (cell in 1..size) {
            when (cell) {
                1 -> entities += Entity(i, j, hillSprites[0])
                size -> entities += Entity(i + size - 1, j, hillSprites[2])
                else -> entities += Entity(i + cell - 1, j, hillSprites[4])
            }
        }
    }

    fun addHill(i: Int, height: Int) {  //склон
        for (j in 0 until height) {
            val size = (height - j) * 2 + 1 // calculate section size
            drawHillSection(i + j, j, size)
        }
        drawSprite(hillSprites[1], i = i + height, j = height) // top
    }

    fun addPipes(i: Int, j: Int) { // труба
        entities += Entity(i, 0, pipeSprites[2])  // left side - leg
        entities += Entity(i + 1, 0, pipeSprites[3]) // right side - leg

        entities += Entity(i, 1, pipeSprites[0])  // left side - top
        entities += Entity(i + 1, 1, pipeSprites[1]) // right side - top
    }

    fun addBricks(i: Int, j: Int, length: Int) { // кирпичи
        for (n in 0 until length) {
            when (n) {
                else -> entities += Entity(i + n, j, brickSprite[0])
            }
        }
    }

    fun addPandoras(i: Int, j: Int) {  // ящик с вопросом
        entities += Entity(i, j, pandorasSprite[0])
    }

    fun addForwardSteps(i: Int, j: Int, height: Int) {  //  отдельные списки для ступеней вперед
    }

    fun addBackwardSteps(i: Int, j: Int, height: Int) {  //  отдельные списки для ступеней назад
    }
    //----------------------------------------------------настройка уровня---------------------------------------------------------//
    fun update(timestamp: Double) { //  на каждом кадре чуть сдвигать видимое окно уровня
        val dt = (if (gameTime.isNaN()) 0.0 else timestamp - gameTime) / 1000
        gameTime = timestamp

        windowX += dt * 0.01
        hero.update(dt)

        if (hero.x < 0) hero.x = 0.0
        if (hero.x > (LEVEL_LENGTH - 1)) hero.x = (LEVEL_LENGTH - 1).toDouble()
        if (hero.x >= windowX + 5) {
            windowX = hero.x-5
        } else if (hero.x <= windowX + 3) {
            windowX = hero.x-3
        }
        if (windowX < 0) windowX = 0.0
        if (windowX > (LEVEL_LENGTH - 16)) windowX = (LEVEL_LENGTH - 16).toDouble()

        for (entity in staticEntities + dynamicEntities + hero) {
            if (entity.left > windowX - 16 && entity.right < windowX + 32) {
                entity.update(dt)
            }
        }

        for (entity1 in dynamicEntities + hero) {
            for (entity2 in staticEntities + dynamicEntities) {
                if (entity1 != entity2) {
                    entity1.checkCollisionWith(entity2)
                }
            }
        }
    }
}

//----------------------------------------------------настройка уровня---------------------------------------------------------//
infix fun <A, B> A.x(b: B) = Pair(this, b)
val level = Level(
    floor = listOf(0..30,33..68, 71..85, 89..152, 155..212), // пол
    bushes = listOf(11 x 3, 23 x 1, 59 x 3, 89 x 2, 137 x 2, 71 x 1, 106 x 3, 118 x 1, 167 x 1), //курсты
    clouds = listOf(7 x 8 x 1, 19 x 9 x 1, 27 x 8 x 3, 36 x 9 x 2, 56 x 8 x 1, 67 x 9 x 1, 75 x 8 x 3, 87 x 9 x 1, 103 x 9 x 1, 123 x 8 x 3, 132 x 9 x 2, 152 x 8 x 1, 163 x 9 x 1, 171 x 8 x 3, 180 x 9 x 2, 200 x 8 x 1), // облака
    bricks = listOf(3 x 5 x 5, 20 x 3 x 7, 77 x 3 x 3, 80 x 7 x 8, 91 x 7 x 4, 94 x 3 x 1, 117 x 3 x 1, 100 x 3 x 2, 120 x 7 x 4, 128 x 7 x 4, 129 x 3 x 2, 168 x 3 x 4), // кирпичи
    hills = listOf(0 x 2, 16 x 1, 48 x 2, 64 x 1, 96 x 2, 111 x 1, 144 x 2, 160 x 1, 192 x 2), //холмы  холм/склон
    pipes = listOf(10 x 2, 21 x 3, 24 x 3, 38 x 3, 46 x 4, 163 x 2, 179 x 2), // трубы
    pandoras = listOf(5 x 5, 16 x 3, 21 x 3, 22 x 7, 23 x 3, 78 x 3, 94 x 7, 105 x 3, 108 x 3, 108 x 7, 111 x 3, 129 x 7, 130 x 7, 170 x 3),// ящик с вопросом
    forwardSteps = listOf(5 x 4 x 0, 134 x 4 x 0, 148 x 4 x 1, 181 x 8 x 1), //  отдельные списки для ступеней вперед
    backwardSteps = listOf(15 x 4 x 0, 140 x 4 x 0, 155 x 4 x 0), //  отдельные списки для ступеней назад
    //----------------------------------------------------координаты спрайтов---------------------------------------------------------//
    floorSprite = Sprite.tile(0, 0), // пол Спрайт
    bushSprites = Sprite.bush(11, 9), // кустовые спрайты
    cloudSprites = Sprite.cloud(0, 20), // облачные спрайты
    hillSprites = Sprite.hill(8, 10), // холмовые спрайты
    pipeSprites = Sprite.pipe(0, 10), // трубные спрайты
    brickSprite = Sprite.bricks(1, 0), // кирпичные спрайты
    pandorasSprite = Sprite.pandoras(24, 0),  // ящик с вопросом
    forwardStepsSprite = Sprite.forwardSteps(0, 3),  //  отдельные списки для ступеней вперед
    backwardStepsSprite = Sprite.backwardSteps(0, 5),  //  отдельные списки для ступеней назад
)