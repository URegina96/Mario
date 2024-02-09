const val HERO_FORWARD_IMAGE = "sprites/player.png"
const val HERO_BACKWARD_IMAGE = "sprites/playerl.png"
class Hero : Entity(x = 2.0, y = 0.0, Sprite(HERO_FORWARD_IMAGE,si = 5, sj = 2)){
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
}