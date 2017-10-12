package com.yao;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaozb on 15-4-11.
 * 保存连接的SocketChannel
 */
public class NettyChannelMap {
    private static final Logger logger = LoggerFactory.getLogger(NettyChannelMap.class);

    // 保存连接的SocketChannel
    private static Map<String,SocketChannel> map=new ConcurrentHashMap<String, SocketChannel>();

    public static void add(String clientId,SocketChannel socketChannel){
        map.put(clientId,socketChannel);
    }

    public static Channel get(String clientId){
       return map.get(clientId);
    }

    public static void remove(SocketChannel socketChannel){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==socketChannel){
                map.remove(entry.getKey());
            }
        }
    }

}
