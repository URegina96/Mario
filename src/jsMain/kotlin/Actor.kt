import kotlin.math.abs
import kotlin.math.floor

open class Actor(override var x: Double, override var y: Double, override val sprites: List<Sprite>) : Entity(x, y, sprites) {
    override var vX: Double = 0.0
    override var vY: Double = 0.0
    override var aX: Double = 0.0
    override var aY: Double = -GRAVITY_ACCELERATION

    constructor(i: Int, j: Int, sprites: List<Sprite>) : this(i.toDouble(), j.toDouble(), sprites)

    constructor(i: Int, j: Int, sprite: Sprite) : this(i.toDouble(), j.toDouble(), listOf(sprite))

    override fun onRightSideCollisionWith(that: Entity) {
        x = that.left - this.width
        if (vX > 0) {
            vX = 0.0
        }
    }

    override fun onLeftSideCollisionWith(that: Entity) {
        x = that.right
        if (vX < 0) {
            vX = 0.0
        }
    }

    override fun onTopSideCollisionWith(that: Entity) {
        if (y>0) {
            y = that.top //марио ходит и прыгает по поверхностям
            vY = 0.0
        }
    }
    /*
    y = that.top --> верх спрайтов
    y = that.bottom --> низ спрайтов
     */

    override fun onBottomSideCollisionWith(that: Entity) {
          if (y>0){
              y=that.bottom-that.height ////марио бъется головой по поверхностям и прыгает
              vY=0.0
          }
    }


    override fun update(dt: Double) {
        vX += aX * dt
        vY += aY * dt
        x += vX * dt
        y += vY * dt
    }
}