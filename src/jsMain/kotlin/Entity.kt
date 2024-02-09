//Entity - объект, сущность. Объекты в игре характеризуются расположением  и изображением (спрайтом)

open class Entity(var x: Double, var y: Double, val sprites: List<Sprite>) {

    constructor(i: Int, j: Int, sprites: List<Sprite>) :
            this(i.toDouble(), j.toDouble(), sprites)

    constructor(i: Int, j: Int, sprite: Sprite) :
            this(i.toDouble(), j.toDouble(), listOf(sprite))

    open val sprite: Sprite
        get() = sprites[0]

    open fun update(dt: Double) {}
}