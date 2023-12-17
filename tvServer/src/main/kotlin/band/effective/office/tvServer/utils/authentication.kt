package band.effective.office.tvServer.utils

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.authentication(){
    val apikey = System.getenv("OURKEY")
    install(Authentication){
        bearer("auth-bearer") {
            authenticate {tokenCredential ->
                if (tokenCredential.token == apikey) {
                    UserIdPrincipal("user")
                } else {
                    null
                }
            }
        }
    }
}