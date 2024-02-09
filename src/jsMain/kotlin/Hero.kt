const val HERO_FORWARD_IMAGE = "sprites/player.png"
const val HERO_BACKWARD_IMAGE = "sprites/playerl.png"
class Hero : Entity(x = 2.0, y = 0.0, sprites = List(7) { Sprite(HERO_FORWARD_IMAGE, si = 5 + it, sj = 2) }) {
    // Дам  Марио возможность двигаться. При этом пусть он  смотрит в  сторону направления движения
    // При смене направления  нужно менять спрайт, а точнее исходное изображение в спрайте
    private var isWalking = false
    private var isFacingForward = true
    private val walkingAnimation = SpriteAnimation(sprites.toList().subList(1, 4), fps = 5.0)

    fun move(forward: Boolean) {
        isWalking = true
        isFacingForward = forward

        sprite.src = if (forward) HERO_FORWARD_IMAGE else HERO_BACKWARD_IMAGE
        x += if (forward) 0.2 else -0.2
    }

    fun notMove() {
        isWalking = false
    }

    override val sprite: Sprite
        get() = when {
            isWalking -> walkingAnimation.sprite
            else -> sprites[0]
        }.apply {
            src = if (isFacingForward) HERO_FORWARD_IMAGE else HERO_BACKWARD_IMAGE
        }
}