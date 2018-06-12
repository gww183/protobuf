package com.beebank.java.main;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import com.beebank.java.protobuf.SubscribeRespProto;

public class TestSubscriptbeReqPoto {
	
	public static void main(String[] arg) {
		TestSubscriptbeReqPoto reqProto = new TestSubscriptbeReqPoto();
		reqProto.connet(8080);
	}
	
	public void connet(int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootStrap = new Bootstrap();
		bootStrap.group(group);
		bootStrap.option(ChannelOption.TCP_NODELAY, true);
		bootStrap.channel(NioSocketChannel.class);
		bootStrap.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
				socketChannel.pipeline().addLast(new ProtobufDecoder(
						SubscribeRespProto.SubscribeRespProtoTest.getDefaultInstance()
						));
				socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
				socketChannel.pipeline().addLast(new ProtobufEncoder());
				socketChannel.pipeline().addLast(new SubReqClientHandler());
			}
		});
		try {
			ChannelFuture future = bootStrap.connect("127.0.0.1", port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
		
	}
}
