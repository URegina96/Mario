import kotlin.math.abs

//Entity - объект, сущность. Объекты в игре характеризуются расположением  и изображением (спрайтом)
const val GRAVITY_ACCELERATION = 25.0 //  константа гравитационного ускорения
open class Entity(open var x: Double, open var y: Double, open val sprites: List<Sprite>) {

    open var vX: Double = 0.0
    open var vY: Double = 0.0
    open var aX: Double = 0.0
    open val aY = -GRAVITY_ACCELERATION // на Марио действует сила тяжести, поэтому нужно задать ему ускорение

    constructor(i: Int, j: Int, sprites: List<Sprite>) : this(i.toDouble(), j.toDouble(), sprites)

    constructor(i: Int, j: Int, sprite: Sprite) : this(i.toDouble(), j.toDouble(), listOf(sprite))

    open val sprite: Sprite
        get() = sprites[0]

    open fun update(dt: Double) {
        vX += aX * dt
        vY += aY * dt
        x += vX * dt
        y += vY * dt

        if (y <= 0.0) {
            vY = 0.0
            y = 0.0
        }
    }
}