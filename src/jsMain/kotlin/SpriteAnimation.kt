class SpriteAnimation(val sprites: List<Sprite>, var fps: Double) {
    private var progress = 0.0

    val sprite: Sprite
        get() = sprites[progress.toInt() % sprites.size]

    fun start() {
        progress = 0.0
    }

    fun update(dt: Double) {
        progress += fps * dt
    }
}