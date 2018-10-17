package cf.netty;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 处理服务端 channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // 默默地丢弃收到的数据
    	System.out.print(((ByteBuf) msg).toString(CharsetUtil.UTF_8));
    	//ctx.write("中文123");
    	//ctx.write(msg);
    	//ctx.flush();
    	ctx.channel().writeAndFlush("print zhe info:"+((ByteBuf) msg).toString(CharsetUtil.UTF_8));
        //((ByteBuf) msg).release(); // (3)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}