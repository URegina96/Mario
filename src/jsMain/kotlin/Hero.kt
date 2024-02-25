import KeyboardInput.isJumpPressed
import KeyboardInput.isLeftPressed
import KeyboardInput.isRightPressed
import kotlin.math.abs
import kotlin.math.sign

const val HERO_FORWARD_IMAGE = "sprites/player.png"
const val HERO_BACKWARD_IMAGE = "sprites/playerl.png"
var hero=Hero()
var levelFloor=level.floor

class Hero : Actor(x = 2.0, y = 0.0, sprites = List(7) { Sprite(HERO_FORWARD_IMAGE, si = 5 + it, sj = 2) }) {

    private var isWalking = false
    private var isFacingForward = true
    var isJumping = false
    private var isSurrendering = false
    private var surrenderingTime = 0.0
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
        isFacingForward = true
        isJumping = false
        sprite.src = HERO_FORWARD_IMAGE
        x+= 0.3
    }

    fun moveLeft(){
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
             vY = 14.5
            isJumping = true
    }
    fun surrender() {
        var lengthHero = 1.0
        var leftLegHero = levelFloor.any { x.toInt() in it }
        var rightLegHero = levelFloor.any { (x + lengthHero).toInt() in it }
        var overTheAbyssHero = !leftLegHero && !rightLegHero
        if (!isSurrendering && overTheAbyssHero && y<=0) {
            isSurrendering = true
            surrenderingTime = 0.0
        }
    }

    override val sprite: Sprite
        get() = when {
            isJumping->sprites[5]
            isSurrendering->sprites[6]
            isWalking -> walkingAnimation.sprite
            else -> sprites[0]
        }.apply {
            src = if (isFacingForward) HERO_FORWARD_IMAGE else HERO_BACKWARD_IMAGE
        }

    override fun update(dt: Double) {
        if (y > 0) { // Применение гравитации, если персонаж находится в воздухе
            y += vY * dt
            vY -= (GRAVITY_ACCELERATION ) * dt
        }
        if (isJumping) {    // Обновление позиции по вертикали в зависимости от скорости прыжка
            y += vY * dt
            vY -= (GRAVITY_ACCELERATION ) * dt
            if (y <= 0) {   // Проверка, чтобы объект не проваливался под землю
                y = 0.0
                vY = 0.0
                isJumping = false
            }
        }
        surrender()
        if (isSurrendering) {
            surrenderingTime += dt
            when (surrenderingTime) {
                in 0.0..0.5 -> {
                    y = 0.0
                }

                in 0.5..1.0 -> {
                    y = 3.0 + dt
                }

                in 1.0..2.0 -> {
                    y += -GRAVITY_ACCELERATION * (surrenderingTime - 1.0) * dt
                }
            }
        }
            walkingAnimation.update(dt)
        }

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
            isStanding=true
        }
    }
    override fun onBottomSideCollisionWith(that: Entity) {
        if (y>0){
            y=that.bottom-that.height ////марио бъется головой по поверхностям и прыгает
            vY=0.0
            isStanding=false
        }
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