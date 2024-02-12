import kotlin.math.abs
import kotlin.math.sign

const val HERO_FORWARD_IMAGE = "sprites/player.png"
const val HERO_BACKWARD_IMAGE = "sprites/playerl.png"
class Hero : Entity(x = 2.0, y = 0.0, sprites = List(7) { Sprite(HERO_FORWARD_IMAGE, si = 5 + it, sj = 2) }) {

    private var isWalking = false
    private var isFacingForward = true
    private var isJumping = false
    private val walkingAnimation = SpriteAnimation(sprites.toList().subList(1, 4), fps = 5.0)

    fun move(isDirectionForward: Boolean) {
        isFacingForward=isDirectionForward
        if (isDirectionForward){
            moveLeft()
        }else if (!isDirectionForward){
            moveRight()
        }else{
            notMove()
        }
    }
    fun moveRight(){
        isWalking = true
        sprite.src = HERO_FORWARD_IMAGE
        x+= 0.05
    }

    fun moveLeft(){
        isWalking = true
        sprite.src = HERO_BACKWARD_IMAGE
        x-= 0.05
    }
    fun notMove() {
        isWalking = false
        vX = 0.0 // Скорость становится равной 0
    }
    fun jump() {
        if (y == 0.0) {
            isJumping = false
            vY = 14.5
            sprite.src = sprites[5].src
        }
    }

    override val sprite
        get() = walkingAnimation.sprite
    override fun update(dt: Double) {
        walkingAnimation.update(dt)
    }
}



//    fun move(forward: Boolean) {
//        if (isJumping || !isStanding) return // Если Марио в воздухе или не на земле, то не реагирует на перемещение
//        isWalking = true
//        isFacingForward = forward
//
//        sprite.src = if (forward) HERO_FORWARD_IMAGE else HERO_BACKWARD_IMAGE
//        vX += if (forward) 10.0 else -10.0 // Ускорение в соответствующую сторону
//
//        if (vX * aX < 0) { // Если скорость и ускорение направлены в разные стороны
//            sprite.src = sprites[4].src // Смена направления спрайта Марио
//        }
//        if (abs(vX) > 5.0) vX = sign(vX) * 5.0 // Ограничение скорости
//    }