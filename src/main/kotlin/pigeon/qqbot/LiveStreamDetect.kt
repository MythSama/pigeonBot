package pigeon.qqbot

import com.beust.klaxon.Klaxon
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import net.mamoe.mirai.Bot
data class Douyu(val error: Int, val data: data)
data class data(val room_status: String)


/*
fun Bot.LiveStreamDetect(roomNumber:Int){
        var liveStatus: String = "0"
        while (true) {
            val http =
                    URL("http://open.douyucdn.cn/api/RoomApi/room/$roomNumber")
                            .openConnection() as HttpURLConnection
            http.requestMethod = "GET"
            val json = Klaxon().parse<Douyu>(http.inputStream)
            if (json?.error == 0) {
                if (json.data.room_status == "1" && json.data.room_status != liveStatus) {
                    liveStatus = json.data.room_status
                    getGroup(1143577518L).sendMessage("$roomNumber 播了")
                } else {
                    liveStatus = json.data.room_status
                    getFriend(2211584273L).sendMessage("myb")
                }
            }
        }
    }*/