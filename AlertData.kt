package screen

data class Alert(

    val type: String,

    val location: String,

    val latitude: Double,

    val longitude: Double,

    val time: String,

    val status: String,

    val imagePath: String
)