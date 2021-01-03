package pigeon.qqbot

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.quote
import net.mamoe.mirai.message.quoteReply
import net.mamoe.mirai.message.sendAsImageTo
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.URL

val legalMode = listOf("text", "tag", "exact_tag", "caption")

fun Bot.setu(username:String, password:String) {
    //Signal.handle(Signal("INT")) { serv.destroy() }
    this.subscribeMessages {
        startsWith("#色图", true) {
            val xps = it.trim().split(" ")
            val mode = if (xps.size > 1 && xps[0] in legalMode) xps[0] else "tag"
            var xp = it.replaceFirst(mode, "").trim()
            if (xp == "") {
                xp = "色图"
            }
            try {
                val proc = Runtime.getRuntime().exec("python3 src/main/setusearch.py $username $password $xp $mode")
                val urlAndId = BufferedReader(InputStreamReader(proc.inputStream)).readLine().split(" ")
                val url = urlAndId[0] as URL
                val id = urlAndId[1]
                //val md5 = saveImg(url, "setu")
                url.openConnection().getInputStream().sendAsImageTo(subject).quoteReply(id)
            } catch (e: Exception) {
                reply("找不到关键词为${xp}的色图")
            }
        }
    }
}

