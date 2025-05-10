package handler

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.*

class HttpServerHandler : SimpleChannelInboundHandler<FullHttpRequest>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: FullHttpRequest) {
        val uri = msg.uri()
        val method = msg.method()
        val responseContent = when (method) {
            HttpMethod.GET -> "GET request to $uri"
            HttpMethod.POST -> "POST request to $uri"
            HttpMethod.PUT -> "PUT request to $uri"
            HttpMethod.DELETE -> "DELETE request to $uri"
            else -> "Unsupported method $method"
        }
        val response = DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(responseContent.toByteArray()))
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes())
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE)
    }
}