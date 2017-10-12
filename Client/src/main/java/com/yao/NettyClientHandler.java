package com.yao;

import com.yao.module.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yaozb on 15-4-11.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg> {
    private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    /**
     * 心跳检测触发方法
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    PingMsg pingMsg=new PingMsg();
                    ctx.writeAndFlush(pingMsg);
                    logger.info("send ping to server----------");
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 读取服务端发送的消息
     * @param channelHandlerContext
     * @param baseMsg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {
        logger.info("client read server message-----------");
        MsgType msgType=baseMsg.getType();
        switch (msgType){
            case LOGIN:{
                //向服务器发起登录
                LoginMsg loginMsg=new LoginMsg();
                loginMsg.setPassword("admin");
                loginMsg.setUserName("admin123");
                logger.info("client send to server login request----------");
                channelHandlerContext.writeAndFlush(loginMsg);
            }break;
            case PING:{
                logger.info("receive ping from server----------");
            }break;
            case ACK:{
                ReplyClientBody replyClientBody=new ReplyClientBody("client info **** !!!");
                ReplyMsg replyMsg=new ReplyMsg();
                replyMsg.setBody(replyClientBody);
                logger.info("client send to server ack message------");
                channelHandlerContext.writeAndFlush(replyMsg);
            }break;
            case REPLY:{
                ReplyMsg replyMsg=(ReplyMsg)baseMsg;
                ReplyServerBody replyServerBody=(ReplyServerBody)replyMsg.getBody();
                logger.info("receive client msg: "+replyServerBody.getServerInfo());
            }
            default:break;
        }
        ReferenceCountUtil.release(msgType);
    }

}
