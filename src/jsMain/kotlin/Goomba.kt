
class Goomba(i: Int, j: Int, sprites: List<Sprite>, val onDisappear: Goomba.() -> Unit) : Actor(i,j, sprites){
/*
Внутри колбэка this будет этой гумбой
свойство isDisappearing для таких ситуаций, когда объект еще есть, но уже не реагирует ни на что
 */
    private var isWalking = true
    private var isCollidingRight =  false
    private var isCollidingLeft =  false
    private var isFacingForward = true
    private var isJumping = false
    private var isDisappearing = false


    override fun onBottomSideCollisionWith(that: Entity) {
        super.onBottomSideCollisionWith(that)
        if (that is Hero) { // Останавливаем движение гумбы
            isWalking = false
            isStanding=true
        }
    }
    override fun onRightSideCollisionWith(that: Entity) {
        super.onRightSideCollisionWith(that)
        isCollidingRight = false
        if (isWalking){
            x = that.left - that.width
            vX = 0.0
            isFacingForward=false
        }
    }

    override fun onLeftSideCollisionWith(that: Entity) {
        super.onLeftSideCollisionWith(that)
        isCollidingLeft = true
        if (isWalking){
            x=that.right
            vX=0.0
            isFacingForward=true
        }
    }

    fun moveRight() {
        isWalking = true
        isFacingForward = true
        isJumping = false
        sprite.src = GOOMBA_IMAGE
        x += 0.01
    }

    fun moveLeft() {
        isWalking = true
        isFacingForward = false
        isJumping = false
        sprite.src = GOOMBA_IMAGE
        x -= 0.01
    }

    override fun update(dt: Double) {
        super.update(dt) // Добавить здесь логику обновления состояния гумбы
        if (isWalking) {
            if (isFacingForward) {
                moveRight()
            } else {
                moveLeft()
            }
        }
    }
}
//    override fun onTopSideCollisionWith(that: Entity) { //при столкновении сверху с
//        super.onTopSideCollisionWith(that)
//        if (that is Hero) {
//            isWalking = false // Останавливаем движение гумбы
//            isStanding=true
//            isDisappearing = true
//            onDisappear()
//            vX = 0.0 // Остановка скорости по оси X
//        }
//    }












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