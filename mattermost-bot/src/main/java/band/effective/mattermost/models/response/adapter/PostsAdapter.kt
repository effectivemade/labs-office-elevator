package band.effective.mattermost.models.response.adapter

import band.effective.core.moshi
import band.effective.mattermost.models.response.models.Post
import band.effective.mattermost.models.response.models.Posts
import com.squareup.moshi.*

class PostsAdapter: JsonAdapter<Posts>() {

    @ToJson
    override fun toJson(p0: JsonWriter, p1: Posts?) {
        TODO("Not yet implemented")
    }

    @FromJson
    override fun fromJson(reader: JsonReader): Posts? {
        reader.beginObject()
        val posts: MutableList<Post> = mutableListOf()
        val adapter = moshi.adapter(Post::class.java)
         while (reader.hasNext()) {
             reader.skipName()
             adapter.fromJson(reader)?.let { posts.add(it) }
         }
        reader.endObject()
        return Posts(posts)
    }

}

