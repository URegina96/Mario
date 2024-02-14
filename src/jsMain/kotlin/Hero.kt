import kotlin.math.abs
import kotlin.math.sign

const val HERO_FORWARD_IMAGE = "sprites/player.png"
const val HERO_BACKWARD_IMAGE = "sprites/playerl.png"
var hero=Hero()
var levelFloor=level.floor
class Hero : Entity(x = 2.0, y = 0.0, sprites = List(7) { Sprite(HERO_FORWARD_IMAGE, si = 5 + it, sj = 2) }) {

    private var isWalking = false
    private var isFacingForward = true
    private var isJumping = false
    private var isSurrendering = false
    private var surrenderingTime = 0.0
    private var lengthHero = 0.1
    private var leftLegHero = levelFloor.any { x.toInt() in it }
    private var rightLegHero = levelFloor.any { (x+lengthHero).toInt() in it }
    private var overTheAbyssHero = !leftLegHero && !rightLegHero
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
    fun surrender() {
        if(!isSurrendering) {
            isSurrendering = true
            surrenderingTime = 0.0
        }
    }

    override val sprite: Sprite
        get() = when {
            isJumping->sprites[5]
            isWalking -> walkingAnimation.sprite
            else -> sprites[0]
        }.apply {
            src = if (isFacingForward) HERO_FORWARD_IMAGE else HERO_BACKWARD_IMAGE
        }

    override fun update(dt: Double) {
        if (overTheAbyssHero) {
            surrender()
            if (isSurrendering) {
                surrenderingTime += dt
                when (surrenderingTime) {
                    in 0.0..0.5 -> { y == 0.0 }
                    in 0.5..1.0 -> { y = 0.3 + dt}
                    in 1.0..2.0 -> { y += -GRAVITY_ACCELERATION * (surrenderingTime - 1.0) * dt }
                }
            }
        }



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



/*
Спрайт 0 - Марио стоит
1, 2, 3 - шагает/бежит
4 - меняет направление движения
5 - прыгает
6 - сдаётся
При ходьбе/беге нам нужно чередовать три изображения,причем скорость смены кадров анимации может зависеть от скорости перемещения
*/