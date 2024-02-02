//Entity - объект, сущность. Объекты в игре характеризуются расположением  и изображением (спрайтом)

open class Entity(var x: Double, var y: Double, var sprite: Sprite) {
    constructor(i: Int, j: Int, sprite: Sprite) : this(i.toDouble(), j.toDouble(), sprite)
}