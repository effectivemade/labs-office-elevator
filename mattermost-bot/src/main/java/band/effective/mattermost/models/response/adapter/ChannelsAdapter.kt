package band.effective.mattermost.models.response.adapter

import band.effective.mattermost.models.response.models.Channel
import band.effective.core.moshi
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class ChannelsAdapter: JsonAdapter<List<Channel>>() {
    @FromJson
    override fun fromJson(p0: JsonReader): List<Channel>? {
        val channelAdapter = moshi.adapter(Channel::class.java)
        val list: MutableList<Channel> = mutableListOf()
        p0.beginArray()
        while (p0.hasNext()) {
            channelAdapter.fromJson(p0)?.let { list.add(it)
            }
        }
        p0.endArray()
        return list
    }

    @ToJson
    override fun toJson(p0: JsonWriter, p1: List<Channel>?) {
        TODO("Not yet implemented")
    }
}