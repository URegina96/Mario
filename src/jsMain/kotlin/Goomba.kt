
class Goomba(i: Int, j: Int, sprites: List<Sprite>, val onDisappear: Goomba.() -> Unit) : Actor(i,j, sprites){

/*
Внутри колбэка this будет этой гумбой
свойство isDisappearing для таких ситуаций, когда объект еще есть, но уже не реагирует ни на что
 */
    override fun onTopSideCollisionWith(that: Entity) {
        super.onTopSideCollisionWith(that)
        if (that is Hero) {
            isDisappearing = true
        }
    }
    }