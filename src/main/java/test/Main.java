package test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.InputStream;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf buf = Unpooled.buffer();
		InputStream input = Main.class.getResourceAsStream("2.txt");
		System.out.println(buf.writeBytes(input, 99));
		System.out.println(buf.readByte());
		System.out.println(buf.writeBytes(input, 99));
		System.out.println(buf.readByte());
		System.out.println(buf.readByte());
		System.out.println(buf.readByte());
	}
}
