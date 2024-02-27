typealias IntPair = Pair<Int, Int>
typealias IntTriple = Pair<Pair<Int, Int>, Int>

class Level(
    val floor: List<IntRange> = emptyList(),
    val bushes: List<IntPair> = emptyList(),
    val hills: List<IntPair> = emptyList(),
    val clouds: List<IntTriple> = emptyList(),
    val pipes: List<IntPair> = emptyList(),
    val bricks: List<IntTriple> = emptyList(),
    val pandoras: List<IntPair> = emptyList(),
    val goomba: List<IntPair> = emptyList(),
    val cloudSprites: List<Sprite> = Sprite.cloud(0, 20),
    val bushSprites: List<Sprite> = Sprite.bush(11, 9),
    val hillSprites: List<Sprite> = Sprite.hill(8, 10),
    val pipeSprites: List<Sprite> = Sprite.pipe(0, 10),
    val floorSprite: Sprite = Sprite.tile(0, 0),
    val brickSprite: List<Sprite> = Sprite.bricks(1, 0),
    val pandorasSprite: List<Sprite> = Sprite.pandoras(24, 0),
    val goombaSprites: List<Sprite> = Sprite.goomba(1, 1),
) {
    var windowX: Double = 0.0
    private var gameTime: Double = Double.NaN
    val hero = Hero()
    private var backgroundEntities = setOf<Entity>()
    private var staticEntities = setOf<Entity>()
    var dynamicEntities = setOf<Actor>()

    init {
        floor.forEach { range ->
            for (j in -1 downTo -2) {
                for (i in range) {
                    addFloor(i, j)
                }
            }
        }

        clouds.forEach { (indices, size) ->
            val (i, j) = indices
            addCloud(i, j, size)
        }

        bushes.forEach { (i, size) ->
            addBush(i, size)
        }

        hills.forEach { (i, size) ->
            addHill(i, size)
        }
        pipes.forEach { (i, j) ->
            addPipes(i, j)
        }
        bricks.forEach { (indices, length) ->
            val (i, j) = indices
            addBricks(i, j, length)
        }
        pandoras.forEach { (i, j) ->
            addPandoras(i, j)
        }
        goomba.forEach { (i, j) ->
            addGoomba(i, j)
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
        if (entity in backgroundEntities) {
            if (entity.x + entity.sprite.w > windowX && entity.x < windowX + 16) {
                drawSprite(entity.sprite, entity.x - windowX, entity.y)
            }
        }

        if (entity in staticEntities) {
            if (entity.x + entity.sprite.w > windowX && entity.x < windowX + 16) {
                drawSprite(entity.sprite, entity.x - windowX, entity.y)
            }
        }

        if (entity in dynamicEntities) {
            if (entity.x + entity.sprite.w > windowX && entity.x < windowX + 16) {
                drawSprite(entity.sprite, entity.x - windowX, entity.y)
            }
        }

        if (entity is Hero) {
            if (entity.x + entity.sprite.w > windowX && entity.x < windowX + 16) {
                drawSprite(entity.sprite, entity.x - windowX, entity.y)
            }
        }
    }

    fun addFloor(i: Int, j: Int) {
        backgroundEntities += Entity(i, j, floorSprite)
    }

    fun addBush(i: Int, size: Int) {
        backgroundEntities += Entity(i, 0, bushSprites[0])
        for (n in i + 1..i + size - 2) {
            backgroundEntities += Entity(n, 0, bushSprites[1])
        }
        backgroundEntities += Entity(i + size - 1, 0, bushSprites[2])
    }

    fun addCloud(i: Int, j: Int, size: Int) {
        backgroundEntities += Entity(i, j, cloudSprites[0])
        for (n in i + 1..i + 1 + size) {
            backgroundEntities += Entity(n, j, cloudSprites[1])
        }
        backgroundEntities += Entity(i + size + 2, j, cloudSprites[2])
    }

    fun addDrawHillSection(i: Int, j: Int, size: Int) {
        for (cell in 1..size) {
            when (cell) {
                1 -> backgroundEntities += Entity(i, j, hillSprites[0])
                size -> backgroundEntities += Entity(i + size - 1, j, hillSprites[2])
                else -> backgroundEntities += Entity(i + cell - 1, j, hillSprites[4])
            }
        }
    }

    fun addHill(i: Int, height: Int) {
        for (j in 0 until height) {
            val size = (height - j) * 2 + 1
            addDrawHillSection(i + j, j, size)
        }
    }

    fun addPipes(i: Int, j: Int) { // труба
        staticEntities += Entity(i, 0, pipeSprites[2])
        staticEntities += Entity(i + 1, 0, pipeSprites[3])

        staticEntities += Entity(i, 1, pipeSprites[0])
        staticEntities += Entity(i + 1, 1, pipeSprites[1])
    }

    fun addBricks(i: Int, j: Int, length: Int) {
        for (n in 0 until length) {
            when (n) {
                else -> staticEntities += Entity(i + n, j, brickSprite[0])
            }
        }
    }

    fun addPandoras(i: Int, j: Int) {
        staticEntities += Entity(i, j, pandorasSprite[0])
    }

    fun addGoomba(i: Int, j: Int) {
        dynamicEntities += Goomba(i, j, goombaSprites, onDisappear = {
            dynamicEntities -= this
            onDisappear()
        })
    }

    fun update(timestamp: Double) {
        val dt = (if (gameTime.isNaN()) 0.0 else timestamp - gameTime) / 1000
        gameTime = timestamp

        windowX += dt * 0.01
        hero.update(dt)

        if (hero.x < 0) hero.x = 0.0
        if (hero.x > (LEVEL_LENGTH - 1)) hero.x = (LEVEL_LENGTH - 1)
        if (hero.x >= windowX + 5) {
            windowX = hero.x - 5
        } else if (hero.x <= windowX + 3) {
            windowX = hero.x - 3
        }
        if (windowX < 0) windowX = 0.0
        if (windowX > (LEVEL_LENGTH - 16)) windowX = (LEVEL_LENGTH - 16)

        for (entity in staticEntities + dynamicEntities + hero) {
            if (entity.left > windowX - 16 && entity.right < windowX + 32) {
                if (entity is Goomba) {
                    entity.update(dt)
                }
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

infix fun <A, B> A.x(b: B) = Pair(this, b)
val level = Level(
    floor = listOf(0..30,33..68, 71..85, 89..152, 155..212),
    bushes = listOf(11 x 3, 23 x 1, 59 x 3, 89 x 2, 137 x 2, 71 x 1, 106 x 3, 118 x 1, 167 x 1),
    clouds = listOf(7 x 8 x 1, 19 x 9 x 1, 27 x 8 x 3, 36 x 9 x 2, 56 x 8 x 1, 67 x 9 x 1, 75 x 8 x 3, 87 x 9 x 1, 103 x 9 x 1, 123 x 8 x 3, 132 x 9 x 2, 152 x 8 x 1, 163 x 9 x 1, 171 x 8 x 3, 180 x 9 x 2, 200 x 8 x 1),
    bricks = listOf(3 x 5 x 5, 20 x 3 x 7, 77 x 3 x 3, 80 x 7 x 8, 91 x 7 x 4, 94 x 3 x 1, 117 x 3 x 1, 100 x 3 x 2, 120 x 7 x 4, 128 x 7 x 4, 129 x 3 x 2, 168 x 3 x 4),
    hills = listOf(0 x 2, 16 x 1, 48 x 2, 64 x 1, 96 x 2, 111 x 1, 144 x 2, 160 x 1, 192 x 2),
    pipes = listOf(10 x 2, 21 x 3, 24 x 3, 38 x 3, 46 x 4, 163 x 2, 179 x 2),
    pandoras = listOf(5 x 5, 16 x 3, 21 x 3, 22 x 7, 23 x 3, 78 x 3, 94 x 7, 105 x 3, 108 x 3, 108 x 7, 111 x 3, 129 x 7, 130 x 7, 170 x 3),
    goomba = listOf(15 x 0,  22 x 0, 40 x 0, 50 x 0, 51 x 0, 82 x 8, 84 x 8, 100 x 0, 102 x 0, 114 x 0, 115 x 0, 122 x 0, 123 x 0, 125 x 0, 126 x 0, 170 x 0, 172 x 0),
)