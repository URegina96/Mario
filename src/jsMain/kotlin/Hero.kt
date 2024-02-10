import kotlin.math.abs
import kotlin.math.sign

const val HERO_FORWARD_IMAGE = "sprites/player.png"
const val HERO_BACKWARD_IMAGE = "sprites/playerl.png"
class Hero : Entity(x = 2.0, y = 0.0, sprites = List(7) { Sprite(HERO_FORWARD_IMAGE, si = 5 + it, sj = 2) }) {
    // Дам  Марио возможность двигаться. При этом пусть он  смотрит в  сторону направления движения
    // При смене направления  нужно менять спрайт, а точнее исходное изображение в спрайте
    /* В классе `Entity` свойство `isWalking` объявлено как `open val`, что означает, что оно доступно для переопределения в подклассах и имеет тип `Boolean`.
    Чтобы исправить ошибки,нужно добавить модификатор `override` перед свойством `isWalking` в классе `Hero` и оставить его доступность и тип такими же, как в классе `Entity`
     */
    override var isWalking: Boolean = true
        get() = super.isWalking
    private var isFacingForward = true
    private var isJumping = false
    private val walkingAnimation = SpriteAnimation(sprites.toList().subList(1, 4), fps = 5.0)

    fun move(forward: Boolean) {
        if (isJumping || !isStanding) return // Если Марио в воздухе или не на земле, то не реагирует на перемещение
        isWalking = true
        isFacingForward = forward

        sprite.src = if (forward) HERO_FORWARD_IMAGE else HERO_BACKWARD_IMAGE
        vX += if (forward) 10.0 else -10.0 // Ускорение в соответствующую сторону

        if (vX * aX < 0) { // Если скорость и ускорение направлены в разные стороны
            sprite.src = sprites[4].src // Смена направления спрайта Марио
        }
        if (abs(vX) > 5.0) vX = sign(vX) * 5.0 // Ограничение скорости
    }

    fun notMove() {
        isWalking = false
        vX = 0.0 // Скорость становится равной 0
    }
    fun jump() {
        if (y == 0.0) {
            isJumping = true
            vY = 14.5
            sprite.src = sprites[5].src
        }
    }

    override val sprite: Sprite
        get() = when {
            isWalking -> walkingAnimation.sprite
            isJumping -> sprites[5]
            else -> sprites[0]
        }.apply {
            src = if (isFacingForward) HERO_FORWARD_IMAGE else HERO_BACKWARD_IMAGE
        }
}