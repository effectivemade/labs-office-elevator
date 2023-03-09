package band.effective.office.tv.leader

import band.effective.office.tv.leader.models.Photo
import band.effective.office.tv.leader.models.Thumb
import com.google.gson.*
import java.lang.reflect.Type

class PhotoJsonDeserializer: JsonDeserializer<Photo?> {
    fun getPhoto( json: JsonObject): Photo = Photo(
        full = json.get("full").asString,
        thumb =
        if (json.has("thumb"))
            Thumb(
                json.get("thumb").asJsonObject.get("180")?.asString ?: "",
                json.get("thumb").asJsonObject.get("360")?.asString ?: "",
                json.get("thumb").asJsonObject.get("520")?.asString ?: "")
        else
            Thumb(
                json.asJsonObject.get("180")?.asString ?: "",
                json.asJsonObject.get("360")?.asString ?: "",
                json.get("520")?.asString ?: ""
            )
    )

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Photo? =
        if (json!!.isJsonNull) null
        else if(json!!.isJsonArray)
            if (json!!.asJsonArray.size() == 0) null
            else getPhoto(json.asJsonArray.get(0).asJsonObject)
        else if (!json.toString().contains("\"_")) getPhoto(json.asJsonObject)
        else getPhoto(json.asJsonObject.entrySet().toList()[0].value.asJsonObject)
}