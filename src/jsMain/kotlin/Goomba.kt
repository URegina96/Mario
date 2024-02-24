
class Goomba(i: Int = 0, j: Int = 0, sprites: List<Sprite> = listOf(), val onDisappear: Goomba.() -> Unit) : Actor(i, j, sprites){
/*
Внутри колбэка this будет этой гумбой
свойство isDisappearing для таких ситуаций, когда объект еще есть, но уже не реагирует ни на что
 */
    private var isWalking = false
    private var isFacingForward = true
    var isJumping = false

    override fun onTopSideCollisionWith(that: Entity) {
        super.onTopSideCollisionWith(that)
        if (that is Hero) {
            isDisappearing = true
            onDisappear()
        }
    }

    fun moveRight() {
        isWalking = true
        isFacingForward = true
        isJumping = false
        sprite.src = GOOMBA_IMAGE
        x += 0.3
    }

    fun moveLeft() {
        isWalking = true
        isFacingForward = false
        isJumping = false
        sprite.src = GOOMBA_IMAGE
        x -= 0.3
    }

    override fun update(dt: Double) {
        // Добавить здесь логику обновления состояния гумбы
        if (isWalking) {
            if (isFacingForward) {
                moveRight()
            } else {
                moveLeft()
            }
        }
    }
}












//private var isWalking = false
//    private var isFacingForward = true
//    var isJumping = false
//
//    override fun update(dt: Double) {
//        super.update(dt)
//
//        if (!isWalking) {
//            moveRight()
//        }
//
//        if (isColliding()) {
//            isFacingForward = !isFacingForward
//            if (isFacingForward) {
//                moveRight()
//            } else {
//                moveLeft()
//            }
//        }
//    }
//
//    private fun isColliding(): Boolean {
//        // Логика проверки столкновений с преградами или другими гумбами
//        return false
//    }
//
//    override fun onTopSideCollisionWith(that: Entity) {
//        super.onTopSideCollisionWith(that)
//        if (that is Hero) {
//            isDisappearing = true
//            onDisappear()
//        }
//    }
//
//    fun moveRight() {
//        isWalking = true
//        isFacingForward = true
//        isJumping = false
//        sprite.src = GOOMBA_IMAGE
//        x += 0.3
//    }
//
//    fun moveLeft() {
//        isWalking = true
//        isFacingForward = false
//        isJumping = false
//        sprite.src = GOOMBA_IMAGE
//        x -= 0.3
//    }
//}