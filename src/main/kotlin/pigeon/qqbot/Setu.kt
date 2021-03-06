package pigeon.qqbot

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.data.sendTo
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

val legalMode = listOf("text", "tag", "exact_tag", "caption")

fun Bot.setu(token:String) {
    this.subscribeMessages {
        startsWith("#色图", true) {
            val xps = it.trim().split(" ")
            val mode = if (xps.size > 1 && xps[0] in legalMode) xps[0] else "tag"
            var xp = it.replaceFirst(mode, "").trim()
            if (xp == "") {
                xp = "ranking"
            }
            try {
                val proc = Runtime.getRuntime().exec("python3.9 src/main/setusearch.py $token $xp $mode")
                proc.waitFor()
                val urlAndId = BufferedReader(InputStreamReader(proc.inputStream)).readLine().split(" ")
                val url = URL(urlAndId[0])
                val id = urlAndId[1]
                url.openConnection().getInputStream().uploadAsImage().plus("https://www.pixiv.net/artworks/$id").sendTo(subject)
            } catch (e: Exception) {
                reply(e.toString())
                reply("找不到关键词为${xp}的色图")
            }
        }
    }
}

