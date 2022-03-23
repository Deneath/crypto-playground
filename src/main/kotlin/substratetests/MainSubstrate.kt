package substratetests

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.random.Random

val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
val nodeUrl = "wss://polkadot-rpc.dwellir.com"
val socket = WebSocketFactory().createSocket(nodeUrl).apply { addListener(SocketListener) }

fun mainSubstrate() {
    scope.launch {
        socket.connectAsynchronously()

        val request = Gson().toJson(GetMetadataRequest)
        socket.sendText(request)
    }
    readLine()
}

abstract class RpcRequest(
    @SerializedName("jsonrpc")
    val jsonRpc: String = "2.0"
)

private fun nextId() = Random.nextInt(1, Int.MAX_VALUE)

open class RuntimeRequest(
    val method: String,
    val params: List<Any>,
    val id: Int = nextId()
) : RpcRequest()

object GetMetadataRequest : RuntimeRequest("state_getMetadata", listOf())
object GetRuntimeVersionRequest : RuntimeRequest("state_getRuntimeVersion", listOf())

object SocketListener : SocketAdapter() {
    override fun onTextMessage(websocket: WebSocket?, text: String?) {

    }
}