package com.dustdawn.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 初始化器
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //WebSocket是基于Http协议，所以要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        //对鞋大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        //Http聚合器，对http消息聚合，聚合成FullHttpRequest或FullHttpResponse
        //几乎在对netty中的编程，都会使用到此hanler
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        // ===============以上用于支持Http协议====================== //

        /**
         * websocket服务器处理的协议，用于指定客户端连接访问的路由： /ws   协议号
         * 本handler会帮你处理系诶繁重的复杂的工作
         * 会帮你处理握手动作： handshack（close，ping，pong） ping+pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的第数据类型对相应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义的handler
        pipeline.addLast(new ChatHandler());

    }
}
