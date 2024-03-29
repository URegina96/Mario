open class Actor(override var x: Double, override var y: Double, override var sprites: List<Sprite>) :
    Entity(x, y, sprites) {
    override var vX: Double = 0.0
    override var vY: Double = 0.0
    override var aX: Double = 0.0
    override var aY: Double = -GRAVITY_ACCELERATION

    constructor(i: Int, j: Int, sprites: List<Sprite>) : this(i.toDouble(), j.toDouble(), sprites)

    constructor(i: Int, j: Int, sprite: Sprite) : this(i.toDouble(), j.toDouble(), listOf(sprite))

    override fun onRightSideCollisionWith(that: Entity) {
    }

    override fun onLeftSideCollisionWith(that: Entity) {
    }

    override fun onTopSideCollisionWith(that: Entity) {
    }

    override fun onBottomSideCollisionWith(that: Entity) {

    }


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