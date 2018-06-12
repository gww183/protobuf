package com.beebank.java.main;

import com.beebank.java.protobuf.SubscribeRespProto;
import com.beebank.java.protobuf.SubscriptReqProto;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqServerHandler extends ChannelInboundHandlerAdapter implements
		ChannelHandler {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("read");
		try{
			SubscriptReqProto.SubscriptReq req = (SubscriptReqProto.SubscriptReq) msg;
			if("zhangsan".equalsIgnoreCase(req.getUserName())) {
				System.out.println("Server accept client " + req.toString());
				ctx.writeAndFlush(resp(req.getSubreqID()));
			}
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private Object resp(int subreqID) {
		SubscribeRespProto.SubscribeRespProtoTest.Builder builder = 
				SubscribeRespProto.SubscribeRespProtoTest.newBuilder();
		builder.setSubReqId(subreqID);
		builder.setResCode(0);
		builder.setDesc("NETTY book succed");
		return builder.build();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}
	
}
