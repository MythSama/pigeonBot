package pigeon.qqbot

import kotlinx.coroutines.delay
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.join
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.sendAsImageTo
import java.io.File

suspend fun main() {
    val qqId = 3308443151L//Bot的QQ号，需为Long类型，在结尾处添加大写L
    val password = "!"//Bot的密码
    val miraiBot = Bot(qqId, password) {
        fileBasedDeviceInfo()
    }.alsoLogin()//新建Bot并登录
    miraiBot.keywordReply()
    miraiBot.randomRepeat()
    miraiBot.welcome()
    miraiBot.keywordAutoReply()
    miraiBot.quote()
    miraiBot.join() // 等待 Bot 离线, 避免主线程退出
}

//
fun randomImg(path: String) = File("src/img/$path").listFiles()?.random()

fun Bot.keywordReply() {
    this.subscribeMessages {
        case("at me") {
            reply(At(sender as Member) + " 给爷爬 ")
        }
        case("#查询二次元浓度") {
            reply("${(0..100).random()}%")
        }
        contains("veraku", true) {
            reply("veraku是神")
        }
        contains("nmsl") {
            reply("nmysl")
        }
        contains("不行") {
            reply("不许不行")
        }
        case("芳芳") {
            reply("芳芳是神")
        }
        (contains("技校") or contains("废物")) {
            reply("虚伪b快爬")
        }
        (contains("fm") or contains("bfm")) {
            randomImg("cats")?.sendAsImageTo(subject)
        }
    }
}

fun Bot.randomRepeat() {
    this.subscribeAlways<GroupMessageEvent> {
        if ((1..50).random() == 1) {
            reply(message)//2%概率复读
        }
    }
}

fun Bot.welcome() {
    this.subscribeAlways<NewFriendRequestEvent> { event ->
        if (this.fromGroupId == 596870824L) {//好友请求来自组群
            event.accept()
            delay(3000L)
            bot.getFriend(this.fromId).sendMessage("test")
        }
    }
}