package camp.codelab.anime.models

import com.google.gson.annotations.SerializedName

class Anime {
    @SerializedName("mal_id")
    var id: Int = 0
    var url: String = ""
    @SerializedName("image_url")
    var imageUrl: String = ""
    var title: String = ""
    @SerializedName("synopsis")
    var descriptionAnime: String = ""

}