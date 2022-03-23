package substratetests

import com.neovisionaries.ws.client.*



open class SocketAdapter: WebSocketListener {

    override fun onStateChanged(websocket: WebSocket?, newState: WebSocketState?) {
//        println("onStateChanged")
    }

    override fun onConnected(websocket: WebSocket?, headers: MutableMap<String, MutableList<String>>?) {
//        println("onConnected")
    }

    override fun onConnectError(websocket: WebSocket?, cause: WebSocketException?) {
//        println("onConnectError")
    }

    override fun onDisconnected(
        websocket: WebSocket?,
        serverCloseFrame: WebSocketFrame?,
        clientCloseFrame: WebSocketFrame?,
        closedByServer: Boolean
    ) {
//        println("onDisconnected")
    }

    override fun onFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
//        println("onFrame")
    }

    override fun onContinuationFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
//        println("onContinuationFrame")
    }

    override fun onTextFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
//        println("onTextFrame")
    }

    override fun onBinaryFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
//        println("onBinaryFrame")
    }

    override fun onCloseFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
//        println("onCloseFrame")
    }

    override fun onPingFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
//        println("onPingFrame")
    }

    override fun onPongFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
//        println("onPongFrame")
    }

    override fun onTextMessage(websocket: WebSocket?, text: String?) {
//        println("onTextMessage")
    }

    override fun onTextMessage(websocket: WebSocket?, data: ByteArray?) {
//        println("onTextMessage")
    }

    override fun onBinaryMessage(websocket: WebSocket?, binary: ByteArray?) {
//        println("onBinaryMessage")
    }

    override fun onSendingFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
//        println("onSendingFrame")
    }

    override fun onFrameSent(websocket: WebSocket?, frame: WebSocketFrame?) {
//        println("onFrameSent")
    }

    override fun onFrameUnsent(websocket: WebSocket?, frame: WebSocketFrame?) {
//        println("onFrameUnsent")
    }

    override fun onThreadCreated(websocket: WebSocket?, threadType: ThreadType?, thread: Thread?) {
//        println("onThreadCreated")
    }

    override fun onThreadStarted(websocket: WebSocket?, threadType: ThreadType?, thread: Thread?) {
//        println("onThreadStarted")
    }

    override fun onThreadStopping(websocket: WebSocket?, threadType: ThreadType?, thread: Thread?) {
//        println("onThreadStopping")
    }

    override fun onError(websocket: WebSocket?, cause: WebSocketException?) {
//        println("onError")
    }

    override fun onFrameError(websocket: WebSocket?, cause: WebSocketException?, frame: WebSocketFrame?) {
//        println("onFrameError")
    }

    override fun onMessageError(
        websocket: WebSocket?,
        cause: WebSocketException?,
        frames: MutableList<WebSocketFrame>?
    ) {
//        println("onMessageError")
    }

    override fun onMessageDecompressionError(
        websocket: WebSocket?,
        cause: WebSocketException?,
        compressed: ByteArray?
    ) {
//        println("onMessageDecompressionError")
    }

    override fun onTextMessageError(websocket: WebSocket?, cause: WebSocketException?, data: ByteArray?) {
//        println("onTextMessageError")
    }

    override fun onSendError(websocket: WebSocket?, cause: WebSocketException?, frame: WebSocketFrame?) {
//        println("onSendError")
    }

    override fun onUnexpectedError(websocket: WebSocket?, cause: WebSocketException?) {
//        println("onUnexpectedError")
    }

    override fun handleCallbackError(websocket: WebSocket?, cause: Throwable?) {
//        println("handleCallbackError")
    }

    override fun onSendingHandshake(
        websocket: WebSocket?,
        requestLine: String?,
        headers: MutableList<Array<String>>?
    ) {
//        println("onSendingHandshake")
    }

}