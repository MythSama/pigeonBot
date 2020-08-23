package pigeon.qqbot

import kotlinx.coroutines.delay
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.event.events.GroupMemberEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.join
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.sendAsImageTo
import net.mamoe.mirai.message.uploadAsImage
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.toExternalImage
import net.mamoe.mirai.utils.upload
import java.io.File


suspend fun main() {
    val qqId = 3364669470L//Bot的QQ号，需为Long类型，在结尾处添加大写L
    val password = "fsc146665154"//Bot的密码
    val miraiBot = Bot(qqId, password).alsoLogin()//新建Bot并登录
    miraiBot.keywordReply()
    miraiBot.randomRepeat()
    miraiBot.welcome()
    miraiBot.join() // 等待 Bot 离线, 避免主线程退出
}

fun randomImg(path:String) :File = File("src/img/$path").listFiles().random()

fun Bot.keywordReply(){
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
        case("神话语录") {
            randomImg("mythquotes")?.sendAsImageTo(subject)
        }
        case("bfm") {
            randomImg("cats")?.sendAsImageTo(subject)
        }
        (contains("技校") or contains("废物")) {
            reply("虚伪b快爬")
        }
    }
}

fun Bot.randomRepeat(){
    this.subscribeAlways<GroupMessageEvent> {
        if((1..50).random()==1) {
            reply(message)//2%概率复读
        }
    }
}

fun Bot.welcome(){
    this.subscribeAlways<NewFriendRequestEvent> {event->
        if(this.message.contains("鸽舍")){//验证消息含有”鸽舍“
            event.accept()
            delay(3000L)
            bot.getFriend(this.fromId).sendMessage("test")
        }

    }
}