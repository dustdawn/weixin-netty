package com.dustdawn.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * 处理消息的handler
 * TextWebSocketFrame：在netty中，是用于websocket专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的消息
        String content = msg.text();
        System.out.println("接收到的信息：" + content);

        /*for(Channel channels : clients){
            //将数据写到缓冲区，刷到客户端
            //将TextWebSocketFrame作为载体，将数据放入TextWebSocketFrame，输入客户端
            channels.writeAndFlush(
                    new TextWebSocketFrame("[服务器在：]"
                            + LocalDateTime.now() + "接收到消息，消息为：" + content));
        }*/
        //下面方法和上面的for循环一致
        clients.writeAndFlush(
                new TextWebSocketFrame("[服务器在：]"
                        + LocalDateTime.now() + "接收到消息，消息为：" + content));
    }

    /**
     * 当客户端连接服务器之后（打开连接）
     * 获取客户端的channel，并且放到ChannelGroup中区进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //将channel添加到管理的容器里
        clients.add(ctx.channel());

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        //clients.remove(ctx.channel());
        System.out.println("客户端断开，channel对应的长id为："+ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应的短id为："+ctx.channel().id().asShortText());
    }
}
