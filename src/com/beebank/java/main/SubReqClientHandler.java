package com.beebank.java.main;

import com.beebank.java.protobuf.SubscriptReqProto;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class SubReqClientHandler extends ChannelHandlerAdapter implements
		ChannelInboundHandler {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for(int i = 0; i < 10; i++) {
			ctx.write(subReq(i));
		}
		ctx.flush();
	}

	private SubscriptReqProto.SubscriptReq subReq(int i) {
		SubscriptReqProto.SubscriptReq.Builder builder = 
				SubscriptReqProto.SubscriptReq.newBuilder();
		builder.setSubreqID(i);
		builder.setUserName("zhangsan");
		builder.setProductName("netty book for protobuf");
		return builder.build();
	}

	@Override
	public void channelInactive(ChannelHandlerContext arg0) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext arg0, Object msg)
			throws Exception {
		System.out.println("Receive server response:" + msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		ctx.flush();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext arg0) throws Exception {

	}

	@Override
	public void channelUnregistered(ChannelHandlerContext arg0)
			throws Exception {

	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext arg0)
			throws Exception {

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext arg0, Object arg1)
			throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext arg0) throws Exception {

	}

	@Override
	public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {

	}

	
}
