package band.effective.office.elevator.websocket.client

import band.effective.office.elevator.websocket.client.utils.HashUtil
import band.effective.office.elevator.websocket.client.utils.DateUtils
import band.effective.office.elevator.websocket.client.utils.ElevatorController
import band.effective.office.elevator.websocket.client.utils.parseStringToISO8061Date
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking

fun main() {
    val client = HttpClient(CIO) {
        install(WebSockets) {
            pingInterval = 20_000
        }
    }
    runBlocking {
        client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/elevator-connection") {
            while (true) {
                val dateFromServer = (incoming.receive() as? Frame.Text)?.readText()
                val hashFromServer = (incoming.receive() as? Frame.Text)?.readText()
                val selfGeneratedHash = HashUtil.sha256(dateFromServer)
                when {
                    !DateUtils.isCorrectTime(dateFromServer?.parseStringToISO8061Date()) -> {
                        send("Request failed: incorrect time")
                    }

                    !hashFromServer.equals(selfGeneratedHash) -> {
                        send("Request failed: incorrect hash")
                    }

                    else -> {
                        try {
                            ElevatorController.call()
                            send("Successfully")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }
    client.close()
}