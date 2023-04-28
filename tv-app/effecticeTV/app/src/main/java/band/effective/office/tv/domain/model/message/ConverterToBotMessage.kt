package band.effective.office.tv.domain.model.message

import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.network.mattermost.model.PostJson
import band.effective.office.tv.network.mattermost.model.ReactionJson

fun PostJson.toBotEvent(): BotEvent.PostMessage =
    BotEvent.PostMessage(
        message = data.post.message,
        messageId = data.post.id,
        userName = data.senderName,
        userId = data.post.userId,
        rootId = data.post.rootId,
        channelId = data.post.channelId
    )

fun ReactionJson.toBotEvent(): BotEvent.Reaction =
    BotEvent.Reaction(
        messageId = data.reaction.postId,
        userId = data.reaction.userId,
        rootId = data.reaction.postId,
        channelId = data.reaction.channelId,
        emojiName = data.reaction.emojiName
    )