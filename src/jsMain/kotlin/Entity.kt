import kotlin.math.abs

//Entity - объект, сущность. Объекты в игре характеризуются расположением  и изображением (спрайтом)
const val GRAVITY_ACCELERATION = 25.0 //  константа гравитационного ускорения
open class Entity(open var x: Double, open var y: Double, open val sprites: List<Sprite>) {

    open var vX: Double = 0.0
    open var vY: Double = 0.0
    open var aX: Double = 0.0
    open val aY = -GRAVITY_ACCELERATION // на Марио действует сила тяжести, поэтому нужно задать ему ускорение

    val width
        get() = sprite.w

    val height
        get() = sprite.h

    val left
        get() = x

    val right
        get() = x + width

    val bottom
        get() = y

    val top
        get() = y + height

    val centerX
        get() = (left + right)/2.0

    val centerY
        get() = (top + bottom)/2.0

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
    fun onRightSideCollisionWith(that: Entity) {
        x = that.left - width // Перемещаем Марио влево от объекта, чтобы они были вплотную
        vX = 0.0 // Сбрасываем горизонтальную скорость до нуля
    }

    fun onLeftSideCollisionWith(that: Entity) {
        x = that.right // Перемещаем Марио вправо от объекта, чтобы они были вплотную
        vX = 0.0 // Сбрасываем горизонтальную скорость до нуля
    }

    fun onTopSideCollisionWith(that: Entity) {
        y = that.bottom
        if (vY < 0) {
            vY = 0.0
        }
    }

    fun onBottomSideCollisionWith(that: Entity) {
        y = that.top - this.height
        if (vY > 0) {
            vY = 0.0
        }
        hero.isStanding = true
    }
    fun checkCollisionWith(that: Entity) { //  Обработка столкновений
        if(this == that) { return }

        val distanceX = this.centerX - that.centerX
        val distanceY = this.centerY - that.centerY

        val overlapX = (this.width + that.width) / 2 - abs(distanceX)
        val overlapY = (this.height + that.height) / 2 - abs(distanceY)

        if (overlapX >= 0.1 && overlapY >= 0) {
            when {
                overlapX <= overlapY && distanceX <= 0 -> {
                    this.onRightSideCollisionWith(that)
                    that.onLeftSideCollisionWith(this)
                }
                overlapX <= overlapY && distanceX >= 0 -> {
                    this.onLeftSideCollisionWith(that)
                    that.onRightSideCollisionWith(this)
                }
                overlapX >= overlapY && distanceY <= 0 -> {
                    this.onBottomSideCollisionWith(that)
                    that.onTopSideCollisionWith(this)
                }
                overlapX >= overlapY && distanceY >= 0 -> {
                    this.onTopSideCollisionWith(that)
                    that.onBottomSideCollisionWith(this)
                }
            }
        }
    }

}