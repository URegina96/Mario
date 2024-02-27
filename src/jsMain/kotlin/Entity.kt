import kotlin.math.abs

const val GRAVITY_ACCELERATION = 25.0

open class Entity(open var x: Double, open var y: Double, open val sprites: List<Sprite>) {

    open var vX: Double = 0.0
    open var vY: Double = 0.0
    open var aX: Double = 0.0
    var isStanding = false
    open val aY = -GRAVITY_ACCELERATION

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
        get() = (left + right) / 2.0

    val centerY
        get() = (top + bottom) / 2.0

    constructor(i: Int, j: Int, sprites: List<Sprite>) : this(i.toDouble(), j.toDouble(), sprites)

    constructor(i: Int, j: Int, sprite: Sprite) : this(i.toDouble(), j.toDouble(), listOf(sprite))

    open val sprite: Sprite
        get() = sprites[0]

    open fun onRightSideCollisionWith(that: Entity) {
    }

    open fun onLeftSideCollisionWith(that: Entity) {
    }

    open fun onTopSideCollisionWith(that: Entity) {
    }

    open fun onBottomSideCollisionWith(that: Entity) {
    }

    fun checkCollisionWith(that: Entity) {
        isStanding = false
        if (this == that) {
            return
        }

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