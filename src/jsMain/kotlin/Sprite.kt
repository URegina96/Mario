const val TILES_IMAGE = "sprites/tiles.png"
const val GOOMBA_IMAGE = "sprites/goomba.png"

data class Sprite(var src: String, val si: Int, val sj: Int, val w: Int = 1, val h: Int = 1) {
    companion object {
        fun tile(si: Int, sj: Int, w: Int = 1, h: Int = 1) = Sprite(TILES_IMAGE, si, sj, w, h)
        fun tileGoomba(si: Int, sj: Int, w: Int = 1, h: Int = 1) = Sprite(GOOMBA_IMAGE, si, sj, w, h)
        fun bush(si: Int, sj: Int) = listOf(
            tile(si, sj),
            tile(si + 1, sj),
            tile(si + 2, sj),
        )

        fun cloud(si: Int, sj: Int) = List(3) {
            tile(si + it, sj)
        }

        fun hill(si: Int, sj: Int) = listOf(
            tile(si, sj),
            tile(si + 1, sj),
            tile(si + 2, sj),
            tile(si, sj + 1),
            tile(si + 1, sj + 1),
            tile(si + 2, sj + 1),
        )

        fun pipe(si: Int, sj: Int) = listOf(
            tile(si, sj),
            tile(si + 1, sj),

            tile(si, sj + 1),
            tile(si + 1, sj + 1),
        )

        fun bricks(si: Int, sj: Int) = listOf(
            tile(si, sj),
        )

        fun pandoras(si: Int, sj: Int) = listOf(
            tile(si, sj),
        )

        fun goomba(si: Int, sj: Int) = listOf(
            tileGoomba(si, sj),
        )
    }
}
