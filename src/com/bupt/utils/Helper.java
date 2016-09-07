package com.bupt.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Helper {

	/**
	 * 将16进制字符串，按照连个字符一组（即一字节处理） 如，输入6611，输出[66,11]
	 * 
	 * @param hex
	 *            输入字符串
	 * @return
	 */
	public static String[] hexToStringArray(String hex) {
		String[] arr = new String[hex.length() / 2];
		for (int i = 0, j = 0; i < hex.length() - 1; i = i + 2, j++) {
			String temp = hex.substring(i, i + 2);
			arr[j] = temp;
		}
		return arr;
	}

	/**
	 * 16进制补齐位数
	 * 
	 * @param input
	 * @param size
	 * @param symbol
	 * @return
	 */
	public static String fill(String input, int size, char symbol) {

		while (input.length() < size) {
			input = symbol + input;
		}
		return input;
	}

	public static byte[] getBytes(char[] chars) {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(chars.length);
		cb.put(chars);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);

		return bb.array();
	}

	public static char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);

		return cb.array();
	}

	public static String[] char2StringArray(char[] ch) {
		String[] rtn = new String[ch.length];
		for (int i = 0; i < rtn.length; i++)
			rtn[i] = fill(Integer.toHexString((int) ch[i]), 2, '0');
		return rtn;
	}

	public static long ipToLong(String strip)
	// 将127.0.0.1形式的ip地址转换成10进制整数，这里没有进行任何错误处理
	{
		long[] ip = new long[4];
		int position1 = strip.indexOf(".");
		int position2 = strip.indexOf(".", position1 + 1);
		int position3 = strip.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strip.substring(0, position1));
		ip[1] = Long.parseLong(strip.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strip.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strip.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];// ip1*256*256*256+ip2*256*256+ip3*256+ip4
	}

	public static String longToIp(long longIp)
	// 将10进制整数形式转换成127.0.0.1形式的ip地址，在命令提示符下输入ping3396362403l
	{
		StringBuffer sb = new StringBuffer("");
		sb.append(String.valueOf(longIp >>> 24));// 直接右移24位
		sb.append(".");
		sb.append(String.valueOf((longIp & 0x00ffffff) >>> 16));// 将高8位置0，然后右移16位
		sb.append(".");
		sb.append(String.valueOf((longIp & 0x0000ffff) >>> 8));
		sb.append(".");
		sb.append(String.valueOf(longIp & 0x000000ff));
//		sb.append(".");
		return sb.toString();
	}

	public static void main(String[] args) {
		// System.out.println(fill("abc", 4, '0'));
//		System.out.println(longToIp(ipToLong("127.1.0.1")));
		System.out.println(longToIp(348704058L));
	}

}
