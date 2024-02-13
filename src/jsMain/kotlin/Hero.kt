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
        if (isJumping) return // Если прыжок активен, игнорируем вызов функции движения вправо
        isWalking = true
        isFacingForward = true
        isJumping = false
        sprite.src = HERO_FORWARD_IMAGE
        x+= 0.3
    }

    fun moveLeft(){
        if (isJumping) return // Если прыжок активен, игнорируем вызов функции движения влево
        isWalking = true
        isFacingForward = false
        isJumping = false
        sprite.src = HERO_BACKWARD_IMAGE
        x-= 0.3
    }
    fun notMove() {
        isWalking = false
        isFacingForward = true
        vX = 0.0 // Скорость становится равной 0
    }
    fun jump() {
        if (!isJumping && y == 0.0) { // проверка, чтобы не было бага с застреванием в воздухе , если нажато два раза на прыжок, чтобы прыжок мог быть выполнен только в том случае, если персонаж не находится в состоянии прыжка
            vY = 14.5
            isJumping = true
        }
    }

    override val sprite
        get() = walkingAnimation.sprite

    override fun update(dt: Double) {
        if (isJumping) {    // Обновление позиции по вертикали в зависимости от скорости прыжка
            y += vY * dt
            vY -= GRAVITY_ACCELERATION * dt
            if (y < 0) {   // Проверка, чтобы объект не проваливался под землю
                y = 0.0
                vY = 0.0
                isJumping = false
            }
        }
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