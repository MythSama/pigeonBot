package pigeon.qqbot

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.content
import kotlin.random.Random



fun Bot.smartReply() {
    var smartReplyPossibility = 100.0
    this.subscribeAlways<GroupMessageEvent> {
        if (message.content.contains("我") && !message.content.startsWith("#"))
            if (Random.nextDouble(1.0, 100.0) <= smartReplyPossibility) {
                reply(message.toString().replace("我", "你"))
            }
    }
    this.subscribeMessages {
        startsWith("#config ", true) {
            val key = it.split(" ")[0]
            val value = it.split(" ")[1]
            if (key == "smartP" && value.toDouble() in 0.00..100.00) {
                smartReplyPossibility = value.toDouble()
                reply("智能回复概率改为$value%")
            }
        }
    }
}