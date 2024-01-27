const val TILES_IMAGE = "sprites/tiles.png"
val cloudSprite = Sprite(TILES_IMAGE, si = 0, sj = 20, w = 3, h = 2)
data class Sprite(var src: String, val si: Int, val sj: Int, val w: Int = 1, val h: Int = 1) {
    companion object {

        fun tile(si: Int, sj: Int, w: Int = 1, h: Int = 1) = Sprite(TILES_IMAGE, si, sj, w, h)

        fun bush(si: Int, sj: Int) = List(3) {
            tile(si + it, sj)
        }
    }
}