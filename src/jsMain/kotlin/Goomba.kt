class Goomba(i: Int, j: Int, sprites: List<Sprite> = List(3) { Sprite(GOOMBA_IMAGE, si = 0 + it, sj = 1) }, val onDisappear: Goomba.() -> Unit) : Actor(i,j, sprites){

    private var isWalking = true
    private var isCollidingRight =  false
    private var isCollidingLeft =  false
    private var isFacingForward = true
    private var isJumping = false
    private var isDisappearing = false
    private var isSurrendering= false
    override fun onBottomSideCollisionWith(that: Entity) {
            super.onBottomSideCollisionWith(that)
            if (that is Hero) {
                isWalking = false
                isStanding=true
                isSurrendering=true
                isDisappearing = true
                sprites = listOf(Sprite(GOOMBA_IMAGE, 2, 1))
            }
        }
        override fun onRightSideCollisionWith(that: Entity) {
            super.onRightSideCollisionWith(that)
            sprites = listOf(Sprite(GOOMBA_IMAGE, 0, 1))
            isCollidingRight = false
            if (isWalking){
                x = that.left - that.width
                vX = 0.0
                isFacingForward=false
            }
        }

        override fun onLeftSideCollisionWith(that: Entity) {
            super.onLeftSideCollisionWith(that)
            sprites = listOf(Sprite(GOOMBA_IMAGE, 1, 1))
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
            x += 0.01
        }
        fun moveLeft() {
            isWalking = true
            isFacingForward = false
            isJumping = false
            x -= 0.01
        }
        override fun update(dt: Double) {
            if (isWalking) {
                if (isFacingForward) {
                    moveRight()
                } else {
                    moveLeft()
                }
            }
            super.update(dt)
        }
    }
