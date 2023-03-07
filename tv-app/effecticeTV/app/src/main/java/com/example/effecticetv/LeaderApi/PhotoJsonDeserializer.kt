package LeaderApi

import LeaderApi.JsonElements.Photo
import LeaderApi.JsonElements.Thumb
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class PhotoJsonDeserializer: JsonDeserializer<Photo?> {
    companion object{
        var i = 0
    }
    fun getPhoto( json: JsonElement): Photo=Photo(//360 520
        full = json.asJsonObject.get("full").asString,
        thumb = Thumb(
            if (json.asJsonObject.toString().contains("\"180\""))
                if (json.asJsonObject.toString().contains("thumb")) json.asJsonObject.get("thumb").asJsonObject.get("180").asString
                else json.asJsonObject.asJsonObject.get("180").asString
            else "",
            if (json.asJsonObject.toString().contains("\"360\""))
                if (json.asJsonObject.toString().contains("thumb")) json.asJsonObject.get("thumb").asJsonObject.get("360").asString
                else json.asJsonObject.asJsonObject.get("360").asString
            else "",
            if (json.asJsonObject.toString().contains("\"520\""))
                if (json.asJsonObject.toString().contains("thumb")) json.asJsonObject.get("thumb").asJsonObject.get("520").asString
                else json.asJsonObject.asJsonObject.get("520").asString
            else ""
        )
    )

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Photo? {
        ++i
         return if (json!!.isJsonNull) null
        else if(json!!.isJsonArray)
            if (json!!.asJsonArray.size() == 0) null
            else getPhoto(json.asJsonArray.get(0))
        else if (!json.toString().contains("\"_")) getPhoto(json) else null/*getPhoto(json.asJsonObject.get("_679945"))*/
    }


}