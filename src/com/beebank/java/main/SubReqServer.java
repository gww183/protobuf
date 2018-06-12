package com.beebank.java.main;

import com.beebank.java.protobuf.SubscriptReqProto;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SubReqServer {
	
	public static void main(String[] arg) {
		int port = 8080;
		SubReqServer subreqServer = new SubReqServer();
		subreqServer.bind(port);
	}
	
	public void bind(int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap serverBootStrap = new ServerBootstrap();
			serverBootStrap.group(bossGroup, workGroup);
			serverBootStrap.channel(NioServerSocketChannel.class);
			serverBootStrap.option(ChannelOption.SO_BACKLOG, 100);
			serverBootStrap.handler(new LoggingHandler(LogLevel.INFO));
			serverBootStrap.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sorckerChannel) throws Exception {
					sorckerChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
					sorckerChannel.pipeline().addLast(
							new ProtobufDecoder(
									SubscriptReqProto.SubscriptReq.getDefaultInstance()
									)
							);
					sorckerChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
					sorckerChannel.pipeline().addLast(new ProtobufEncoder());
					sorckerChannel.pipeline().addLast(new SubReqServerHandler());
				}
				
			});
			ChannelFuture channelFuture = serverBootStrap.bind(port).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
