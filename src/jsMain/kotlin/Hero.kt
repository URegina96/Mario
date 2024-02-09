const val HERO_FORWARD_IMAGE = "sprites/player.png"
const val HERO_BACKWARD_IMAGE = "sprites/playerl.png"
class Hero : Entity(x = 2.0, y = 0.0, sprites = List(7) { Sprite(HERO_FORWARD_IMAGE, si = 5 + it, sj = 2) }) {
    // Дам  Марио возможность двигаться. При этом пусть он  смотрит в  сторону направления движения
    // При смене направления  нужно менять спрайт, а точнее исходное изображение в спрайте
    fun moveRight(){
        sprite.src = HERO_FORWARD_IMAGE
        x+= 0.2
    }

    fun moveLeft(){
        sprite.src = HERO_BACKWARD_IMAGE
        x-= 0.2
    }
    override val sprite
        get() = walkingAnimation.sprite
    private val walkingAnimation = SpriteAnimation(sprites.toList().subList(1, 4), fps = 5.0)
    override fun update(dt: Double) {
        walkingAnimation.update(dt)
    }
}