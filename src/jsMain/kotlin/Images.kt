import org.w3c.dom.Image

object Images {
    operator fun get(src: String) = images[src]!!

    private val images = mutableMapOf<String, Image?>()

    private fun isReady() = images.values.all { it != null }

    fun load(vararg imagesSrc: String, onload: () -> Unit) {
        imagesSrc.forEach { src ->
            val image = Image()
            images[src] = null
            image.src = src
            image.onload = {
                images[src] = image
                if (isReady()) {
                    onload()
                }
            }
        }
    }
}