package com.beebank.java.main;

import java.nio.ByteOrder;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class LengthFieldBaseFrameDecoderSon extends
		LengthFieldBasedFrameDecoder {

	public LengthFieldBaseFrameDecoderSon(ByteOrder byteOrder,
			int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
			int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
		super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, initialBytesToStrip, failFast);
	}

}
