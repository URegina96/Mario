// Level - уровень. Уровень содержит  коллекцию объектов и может нарисовать их в нужном месте - функция render
// Теперь  вместо того, чтобы рисовать какой-то объект или его часть, нужно добавлять  в коллекцию  entities += Entity(x, y, sprite)

const val CANVAS_WIDTH = 762.0
const val CANVAS_HEIGHT = 720.0
const val BACKGROUND_COLOR = "#7974FF"
typealias IntPair = Pair<Int, Int>
typealias IntTriple = Pair<Pair<Int, Int>, Int>
class Level(    //отвечает за то как часто используются  спрайты  на уровне
                val floor: List<IntRange> = emptyList(),
                val bushes: List<IntPair> = emptyList(),
                val hills: List<IntPair> = emptyList(),
                val clouds: List<IntTriple> = emptyList(),
                val pipes: List<IntPair> = emptyList(),
                val bricks: List<IntTriple> = emptyList(),  // Дорожки из кирпичей  i x j x длина
                val pandoras: List<IntPair> = emptyList(),   // Ящик Пандоры - это блок с вопросом
                //отвечает за внешний вид, т.е. под какими индаксами спрайты берутся   (замена кода "игровые элементы" из Game.kt)
                val forwardSteps: List<IntTriple> = emptyList(),
                val backwardSteps: List<IntTriple> = emptyList(),
                val cloudSprites: List<Sprite> = emptyList(),
                val bushSprites: List<Sprite> = emptyList(),
                val hillSprites: List<Sprite> = emptyList(),
                val pipeSprites: List<Sprite> = emptyList(),
                val floorSprite: Sprite,
//                val brickSprite: Sprite,
//                val wallSprite: Sprite,
    ) {

    private var entities = setOf<Entity>()
    var windowX: Double = 0.0 // начальное положение окна

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
    }
    fun render() { //функция render -  может нарисовать коллекцию объектов  в нужном месте
        for (entity in entities) {
            if (entity.x + entity.sprite.w > windowX && entity.x < windowX + 16) {
                drawSprite(entity.sprite, entity.x - windowX, entity.y)
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

    private fun drawHillSection(i: Int, j: Int, size: Int) {//склон
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

    fun addPipes(i: Int,j: Int) { // труба
        entities += Entity(i, 0, pipeSprites[2])  // left side - leg
        entities += Entity(i +1, 0, pipeSprites[3]) // right side - leg

        entities += Entity(i, 1, pipeSprites[0])  // left side - top
        entities += Entity(i +1, 1, pipeSprites[1]) // right side - top
    }
}

//----------------------------------------------------настройка уровня---------------------------------------------------------//
infix fun <A,B>A.x(b: B) = Pair(this,b)
val level = Level(
    floor = listOf(0..68, 71..85, 89..152, 155..212), // пол

    bushes = listOf(11 x 3, 23 x 1, 59 x 3, 89 x 2, 137 x 2, 71 x 1, 106 x 3, 118 x 1, 167 x 1), //курсты

    clouds = listOf( // облака
        7 x 8 x 1, 19 x 9 x 1, 27 x 8 x 3, 36 x 9 x 2, 56 x 8 x 1, 67 x 9 x 1, 75 x 8 x 3,
        87 x 9 x 1, 103 x 9 x 1, 123 x 8 x 3, 132 x 9 x 2, 152 x 8 x 1, 163 x 9 x 1,
        171 x 8 x 3, 180 x 9 x 2, 200 x 8 x 1
    ),

    bricks = listOf( 20 x 3 x 5, 77 x 3 x 3, 80 x 7 x 8, 91 x 7 x 4, 94 x 3 x 1, // кирпичи
        117 x 3 x 1, 100 x 3 x 2,  120 x 7 x 4,  128 x 7 x 4,  129 x 3 x 2,  168 x 3 x 4
    ),

    hills = listOf(0 x 2, 16 x 1, 48 x 2, 64 x 1, 96 x 2, 111 x 1, 144 x 2, 160 x 1, 192 x 2), //холмы  холм/склон

    pipes = listOf(10 x 2, 21 x 3, 24 x 3, 38 x 3, 46 x 4, 163 x 2, 179 x 2), // трубы

    pandoras = listOf( // ящик с вопросом
        16 x 3, 21 x 3, 22 x 7, 23 x 3, 78 x 3, 94 x 7, 105 x 3,
        108 x 3, 108 x 7, 111 x 3, 129 x 7, 130 x 7, 170 x 3
    ),
    //----------------------------------------------------координаты спрайтов---------------------------------------------------------//

    forwardSteps = listOf(134 x 4 x 0, 148 x 4 x 1, 181 x 8 x 1), // шаги вперед

    backwardSteps = listOf(140 x 4 x 0, 155 x 4 x 0), // шаги назад

    floorSprite = Sprite.tile(0,0), // пол Спрайт

    bushSprites = Sprite.bush(11,9), // кустовые спрайты

    cloudSprites = Sprite.cloud(0,20), // облачные спрайты

    hillSprites = Sprite.hill(8,10), // холмовые спрайты

    pipeSprites = Sprite.pipe(0, 10), // трубные спрайты
)