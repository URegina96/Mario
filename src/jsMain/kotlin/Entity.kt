//Entity - объект, сущность. Объекты в игре характеризуются расположением  и изображением (спрайтом)

class Entity(val x: Double, val y: Double, val sprite: Sprite) {
    constructor(i: Int, j: Int, sprite: Sprite) : this(i.toDouble(), j.toDouble(), sprite)
}