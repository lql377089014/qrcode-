package com.sany.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;


public class PUtils {

	public static String newLine = "\r\n";

	/**
	 * action byte[]转String 如 '0xFF,0x01,0xFE'==> "FF01FE"
	 *
	 * @param b
	 *
	 * @return
	 */
	

	/**
	 * action byte转String 如 '0xFF'==> "FF"
	 *
	 * @param b
	 *
	 * @return
	 */
//	public static String toHex(byte b) {
//		byte[] buf = new byte[1];
//		buf[0] = b;
//		return toHex(buf);
//	}

	/**
	 * action String 转成byte[] exp: "FF01FE"==>'0xFF,0x01,0xFE'
	 *
	 * @param s
	 *
	 * @return
	 */
	public static byte[] twoCharToBytes(String s) {

		byte[] msg = new byte[s.length() / 2];
		try {
			for (int i = 0; i < msg.length; i++) {
				int index = i * 2;
				int n = Integer.parseInt(s.substring(index, index + 2), 16);
				msg[i] = (byte) n;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return msg;
	}

	/**
	 * aciont 填充字符串
	 *
	 * @param left        true 左填充 false 右填充
	 * @param src         源String
	 * @param suppChar    填充字符
	 * @param totallength 填充后的长度
	 *
	 * @return 填充后的String
	 */
	public static String StringSupplement(boolean left, String src, String suppChar, int totallength) {
		if (src.length() >= totallength)
			return src;
		for (int i = src.length(); i < totallength; i++) {
			if (left == true) {
				src = suppChar + src;
			} else {
				src = src + suppChar;
			}
		}

		return src;
	}

	public static String fillWarehouseCode(String src, int totallength) throws HintException {
		if (src.length() > totallength)
			throw new HintException("编码长度超过规定");
		return StringSupplement(true, src, "0", totallength);

	}

	public static int toUnsignedFromBytes(byte b) {
		return b & 0xFF;
	}

	/**
	 * 15-->"0F"
	 *
	 * @param i
	 * @param keepbit
	 *
	 * @return
	 */
	public static String toHexString(int i, int keepbit) {
		return String.format("%0" + String.valueOf(keepbit) + "x", i).toUpperCase();
	}

	/**
	 * 15-->"0F"
	 *
	 * @param i
	 * @param keepbit
	 *
	 * @return
	 */
	public static String toHexString(long i, int keepbit) {
		return String.format("%0" + String.valueOf(keepbit) + "x", i).toUpperCase();
	}

	/**
	 * 15-->"0F"
	 *
	 * @param i
	 * @param keepbit
	 *
	 * @return
	 */
	public static String toHexString(short i, int keepbit) {
		return String.format("%0" + String.valueOf(keepbit) + "x", i).toUpperCase();
	}

	/**
	 * 15-->"0F"
	 *
	 * @param i
	 * @param keepbit
	 *
	 * @return
	 */
	public static String toHexString(byte i, int keepbit) {
		return String.format("%0" + String.valueOf(keepbit) + "x", i).toUpperCase();
	}

	/**
	 * bigMode, 511-->"01FF"-->"FF01"
	 *
	 * @param b
	 *
	 * @return
	 */
	public static byte[] toReverse(byte[] b) {
		byte data[] = new byte[b.length];

		for (int i = 0; i < b.length; i++) {
			data[b.length - 1 - i] = b[i];
		}

		return data;

	}

//	public static String toReverse(String s) {
//		byte[] b = twoCharToBytes(s);
//		byte[] data = toReverse(b);
//		return toHex(data);
//
//	}

	public static String[] split(String value, String splitchar) {
		return value.split(splitchar);
	}

	/**
	 * action 分割数组
	 *
	 * @param b  源数组
	 * @param sp 分割标志
	 *
	 * @return
	 */
	public static List<byte[]> split(byte[] b, byte sp) {
		int pos = 0;
		List<byte[]> res = new ArrayList<byte[]>();
		for (int i = 0; i < b.length; i++) {

			if (b[i] == sp) {
				if (i != pos) {
					byte[] data = new byte[i - pos];
					System.arraycopy(b, pos, data, 0, data.length);
					res.add(data);
					pos = i + 1;
				} else {
					pos = pos + 1;
				}
			}
		}

		if (res.size() == 0) {
			res.add(b);
		}
		return res;
	}

	public static boolean isEmpty(String s) {
		if (s == null || s.length() == 0)
			return true;
		return false;
	}
	
	public static boolean isEmptyTrim(String s) {
		if (s == null || s.trim().length()==0)
			return true;
		return false;
	}

	public static boolean isEmpty(Map<?, ?> s) {
		if (s == null || s.isEmpty())
			return true;
		return false;
	}

	public static boolean isEmpty(byte[] s) {
		if (s == null || s.length == 0)
			return true;
		return false;
	}

	public static boolean isEmpty(Collection<?> s) {
		if (s == null || s.isEmpty())
			return true;
		return false;
	}

	public static boolean isEmpty(Object s) {
		if (s == null)
			return true;
		return false;
	}

	public static boolean isHexString(String src) {
		Pattern pattern = Pattern.compile("[0-9,A-F,a-f]*");
		Matcher isNum = pattern.matcher(src);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isNumber(String src) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(src);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isLetter(String src) {
		Pattern pattern = Pattern.compile("[A-Z,a-z]*");
		Matcher isNum = pattern.matcher(src);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static String formatInt(int i, int keepbit) {
		return String.format("%0" + String.valueOf(keepbit) + "d", i).toUpperCase();
	}

	/**
	 * 转换byte array 到指定长度，不足时后面补0
	 *
	 * @param b
	 * @param fixLength
	 *
	 * @return
	 */
	public static byte[] convertToFixLenByteArrayToEnd(byte[] b, int fixLength, byte charByte) {
		byte[] b1 = new byte[fixLength];
		byte[] b2 = new byte[fixLength];
		for (int i = 0; i < fixLength; i++) {
			b2[i] = charByte;
		}
		if (b != null) {
			if (b.length < fixLength) {
				System.arraycopy(b, 0, b1, 0, b.length);
				System.arraycopy(b2, 0, b1, b.length, fixLength - b.length);
				return b1;
			} else if (b.length > fixLength) {
				System.arraycopy(b, b.length - fixLength, b1, 0, b1.length);
				return b1;
			} else {
				return b;
			}
		} else {
			return b1;
		}
	}

	public static String TimeToString(long i) {
		String format = "yyyyMMddHHmmss";
		String time = "";
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date d = new Date();
		d.setTime(i);
		time = sf.format(d);
		return time;
	}

	public static String TimeToString(Date i) {
		String format = "yyyy-MM-dd HH:mm:ss";
		String time = "";
		SimpleDateFormat sf = new SimpleDateFormat(format);
		time = sf.format(i);
		return time;
	}
	
	public static String TimeToString2(long i) {
		String format = "yyyyMMddHHmm";
		String time = "";
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date d = new Date();
		d.setTime(i);
		time = sf.format(d);
		return time;
	}

	public static String TimeToString(long i, String format) {
		String time = "";
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date d = new Date(i);
		time = sf.format(d);
		return time;
	}

	/*
	 * 将时间转换为时间戳
	 */
	public static long dateToStamp(Date s) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = simpleDateFormat.format(s);
		Date datetime = simpleDateFormat.parse(time);// 将你的日期转换为时间戳
		long ts = datetime.getTime();
		return ts;
	}

	public static Date StringToTimeDate(String s, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			Date d = sf.parse(s);
			return d;
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}
	}

//	public static long dateCstToStamp(String s) {
//		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//		try {
//			Date date = (Date) sdf.parse(s);
//			return dateToStamp(date);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return System.currentTimeMillis();
//		}
//
//	}

	public static byte[] transferCmcc(byte[] data) {
		ArrayList<Byte> transfer = new ArrayList<Byte>();
		for (byte line : data) {
			if (line == 0x7E) {
				transfer.add((byte) 0x5E);
				transfer.add((byte) 0x7D);
			} else if (line == 0x5E) {
				transfer.add((byte) 0x5E);
				transfer.add((byte) 0x5D);
			} else {
				transfer.add(line);
			}
		}

		byte[] res = new byte[transfer.size()];
		for (int i = 0; i < transfer.size(); i++) {
			res[i] = transfer.get(i);
		}
		return res;
	}

	public static byte[] unTransferCmcc(byte[] data) {
		ArrayList<Byte> unTransfer = new ArrayList<Byte>();
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 0x5E) {
				i++;
				if (data[i] == 0x7D) {
					unTransfer.add((byte) 0x7E);
				} else if (data[i] == 0x5D) {
					unTransfer.add((byte) 0x5E);
				} else {
					unTransfer.add(data[i]);
				}
			} else {
				unTransfer.add(data[i]);
			}
		}
		byte[] res = new byte[unTransfer.size()];
		for (int i = 0; i < unTransfer.size(); i++) {
			res[i] = unTransfer.get(i);
		}
		return res;
	}

	/**
	 * 按长度分隔字符串
	 *
	 * @param s
	 * @param perSize 分隔长度
	 *
	 * @return
	 */
	public static String[] split(String s, int perSize) {
		int size = (int) Math.ceil(s.length() / (double) perSize);
		String[] lines = new String[size];
		int postion = 0;
		int i = 0;
		while (postion < s.length()) {
			lines[i] = s.substring(postion, postion + perSize);
			postion = postion + perSize;
			i++;
		}
		return lines;
	}

	public static String bulidRedisParamListKey(int deviceId) {
		return String.format("%0" + "10d", deviceId);
	}

	public static String bulidRedisBatchingKey(int deviceId) {
		return "Batch" + bulidRedisParamListKey(deviceId);
	}

	public static void sleep(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String excepitonToString(Exception e) {
		// e.printStackTrace();
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return "\r\n" + sw.toString();
	}

	public static String excepitonGetMessage(Exception e) {

		if (isEmpty(e.getMessage()))
			return "error";
		return e.getMessage();

	}

	public static String toBulidString(String... lines) {
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
		}
		return sb.toString();
	}

//	public static byte[] toByteArray(String s) {
//		return DatatypeConverter.parseHexBinary(s);
//	}

	public static byte[] hexStringToByteArray(String s) {
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}

	public static int bytesToInt(byte[] b) {
		int byteNum = Integer.SIZE / Byte.SIZE;
		if (b.length < byteNum) {
			// 如果长度不足4,前面自动补0
			b = convertToFixLenByteArray(b, byteNum);
		}
		final ByteBuffer bb = ByteBuffer.wrap(b);
		return bb.getInt();
	}

	public static byte[] shortToBytes(Short i) {
		final ByteBuffer bb = ByteBuffer.allocate(Short.SIZE / Byte.SIZE);
		bb.putShort(i);
		return bb.array();
	}

	public static byte[] shortToBytes(Short i, ByteOrder order) {
		final ByteBuffer bb = ByteBuffer.allocate(Short.SIZE / Byte.SIZE);
		bb.order(order);
		bb.putShort(i);
		return bb.array();
	}

	public static byte[] intToBytes(int i) {
		final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
		bb.putInt(i);
		return bb.array();
	}

	public static byte[] longToBytes(long value) {
		final ByteBuffer bb = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
		bb.putLong(value);
		return bb.array();
	}

	public static byte[] longToBytes(long value, int keepbit) {
		if (keepbit <= 0 || keepbit > Long.SIZE / Byte.SIZE)
			keepbit = Long.SIZE / Byte.SIZE;
		final ByteBuffer bb = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
		bb.putLong(value);
		byte[] buf = new byte[keepbit];
		System.arraycopy(bb.array(), (Long.SIZE / Byte.SIZE - keepbit), buf, 0, keepbit);
		return buf;
	}

	public static long bytesToLong(byte[] bytes) {
		int byteNum = Long.SIZE / Byte.SIZE;
		if (bytes.length < Long.SIZE / Byte.SIZE) {
			// 如果长度不足8,前面自动补0
			bytes = convertToFixLenByteArray(bytes, byteNum);
		}
		return ByteBuffer.wrap(bytes).getLong();
	}

	public static short bytesToShort(byte[] bytes) {
		int byteNum = Short.SIZE / Byte.SIZE;
		if (bytes.length < Long.SIZE / Byte.SIZE) {
			// 如果长度不足8,前面自动补0
			bytes = convertToFixLenByteArray(bytes, byteNum);
		}
		return ByteBuffer.wrap(bytes).getShort();
	}

	public static String bytesToString(byte[] bytes) {
		return ByteBuffer.wrap(bytes).toString();
	}

	public static String bytes2String(byte[] bytes) {
		return new String(bytes, StandardCharsets.UTF_8);
	}

	public static String bytes2String(byte[] bytes, Charset charset) {
		return new String(bytes, charset);
	}

	public static String bytesToAscii(byte[] b) {
		StringBuffer sb = null;
		if (b != null && b.length != 0) {
			sb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				sb.append((char) (b[i]));
			}
			return sb.toString().trim();
		} else {
			return "";
		}
	}

	/**
	 * 转换byte array 到指定长度，不足时前面补0
	 *
	 * @param b
	 * @param fixLength
	 *
	 * @return
	 */
	public static byte[] convertToFixLenByteArray(byte[] b, int fixLength) {
		byte[] b1 = new byte[fixLength];
		if (b != null) {
			if (b.length < fixLength) {
				System.arraycopy(b, 0, b1, fixLength - b.length, b.length);
				return b1;
			} else if (b.length > fixLength) {
				System.arraycopy(b, b.length - fixLength, b1, 0, b1.length);
				return b1;
			} else {
				return b;
			}
		} else {
			return b1;
		}
	}

	/**
	 * action 判断byte字节上 bit 位是 0还是1
	 *
	 * @param b
	 * @param bit bit from 0 end 7;
	 *
	 * @return
	 */
	public static int checkByBit(byte b, int bit) {
		if (bit < 0 || bit > 8)
			return 0;
		int i = (b >> bit) & 0x01;
		return i;
	}

	/**
	 * action 判断short字节上 bit 位是 0还是1
	 *
	 * @param b
	 * @param bit bit from 0 end 15;
	 *
	 * @return
	 */
	public static int checkByBit(short b, int bit) {
		if (bit < 0 || bit > 16)
			return 0;
		int i = (b >> bit) & 0x01;
		return i;
	}

	/**
	 * action 判断int字节上 bit 位是 0还是1
	 *
	 * @param b
	 * @param bit bit from 0 end 31;
	 *
	 * @return
	 */
	public static int checkByBit(int b, int bit) {
		if (bit < 0 || bit > 16)
			return 0;
		int i = (b >> bit) & 0x01;
		return i;
	}

	/**
	 * 连接两个byte数组
	 *
	 * @param b1
	 * @param b2
	 *
	 * @return 返回新的数据
	 */
	public static byte[] connect(byte[] b1, byte[] b2) {
		if (b1 == null && b2 == null)
			return null;
		if (b1 == null)
			return b2;
		if (b2 == null)
			return b1;

		byte[] b = new byte[b1.length + b2.length];
		System.arraycopy(b1, 0, b, 0, b1.length);
		System.arraycopy(b2, 0, b, b1.length, b2.length);
		return b;
	}

	/**
	 * copy 生成新的数姐
	 *
	 * @param b1
	 * @param b2
	 * @param b3
	 *
	 * @return 返回新的数组
	 */
	public static byte[] connect(byte[] b1, byte[] b2, byte[] b3) {
		byte[] b = connect(b1, b2);
		byte[] res = connect(b, b3);
		return res;
	}

	/**
	 * copy 生成新的数姐
	 *
	 * @param src    源数组
	 * @param srcPos 起始位置
	 * @param length 拷贝长度
	 *
	 * @return 返回新的数组
	 */
	public static byte[] copy(byte[] src, int srcPos, int length) {
		try {
			byte[] s = new byte[length];
			System.arraycopy(src, srcPos, s, 0, length);
			return s;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * action byte型有号符号数转成 int example: byte b=0xFF 转成 int n=255;
	 *
	 * @param b
	 *
	 * @return
	 */
	public static int byteToInt(byte b) {
		return b & 0xFF;
	}

	/**
	 * action short型有号符号数转成 int example: short b=0xFFFF 转成 int n=65535;
	 *
	 * @param i
	 *
	 * @return
	 */
	public static int shortToInt(short i) {
		return i & 0xFFFF;
	}

	/**
	 * action 检查字符串是否符合正则表达式
	 *
	 * @param src
	 * @param format 正则表达式
	 *
	 * @return 符合正则表达式true 否则 false
	 */
	public static boolean checkFormat(String src, String format) {

		Pattern p = Pattern.compile(format);
		Matcher m = p.matcher(src);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * action String转成unicode编码
	 *
	 * @param s
	 *
	 * @return
	 */
//	public static String toUnicode(String s) {
//		try {
//			byte[] t = s.getBytes("unicode");
//			byte[] r = new byte[t.length - 2];
//			System.arraycopy(t, 2, r, 0, r.length);
//			return toHex(r);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			return null;
//		}
//	}

	/**
	 * action unicode编码转成String
	 *
	 * @param hex
	 *
	 * @return
	 * @throws Exception
	 */
	public static String unicodeToString(byte[] hex) throws Exception {

		return new String(hex, "unicode");

	}

	/**
	 * action 查找 mark的第一个下标
	 *
	 * @param src
	 * @param mark
	 *
	 * @return 查到找返回index 否则 -1
	 */
	public static int indexFromArray(byte[] src, byte mark) {
		for (int i = 0; i < src.length; i++) {
			if (src[i] == mark)
				return i;
		}
		return -1;
	}

	/**
	 * action 根据编码
	 *
	 * @param src
	 * @param encode
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String bulidModbusString(byte[] src, String encode) throws UnsupportedEncodingException {
		int index = PUtils.indexFromArray(src, (byte) 0);
		String hit = new String(PUtils.copy(src, 0, index), encode);
		return hit;
	}

	/**
	 * action 对String进行编码
	 *
	 * @param v
	 * @param charsetName 编码格式 如"UTF-8" "GB2312"
	 *
	 * @return
	 */
	public static byte[] encodeBytes(String v, String charsetName) {

		return v.getBytes(Charset.forName(charsetName));
	}

	public static String MD5(String key) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = key.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 自定义加码算法
	 *
	 * @param password 源密码
	 *
	 * @return 加密后的字符串
	 */
//	public static String skyworthEncrypt(String password) {
//		String p = password.trim();
//		byte[] b = p.getBytes();
//		for (int i = 0; i < b.length; i++) {
//			if (i % 2 == 0)
//				b[i] = (byte) (b[i] + (byte) (i));
//			else
//				b[i] = (byte) (b[i] - (byte) (i));
//		}
//		return toHex(b);
//	}

	public static String skyworthDeciphering(String encryptPassword) {
		byte[] b = twoCharToBytes(encryptPassword.trim());
		for (int i = 0; i < b.length; i++) {
			if (i % 2 == 0)
				b[i] = (byte) (b[i] - (byte) (i));
			else
				b[i] = (byte) (b[i] + (byte) (i));
		}
		return new String(b);
	}

	/**
	 * list<Number>-->1,2,3,4,5,6
	 *
	 * @param list
	 *
	 * @return
	 */
	public static String numbersFormatString(List<? extends Number> list) {
		if (isEmpty(list))
			return null;
		String result = "";
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (i != size - 1) {
				result = result + i;
			} else {
				result = result + i + ",";
			}
		}
		return result;
	}

	/**
	 * 1,2,3,4,5,6 --> list<Number>
	 *
	 * @param s
	 * @param cls
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> List<T> stringFormatNumber(String s, Class<T> cls) {
		String[] vs = s.split(",");
		if (isEmpty(vs))
			return null;
		List<T> result = new ArrayList<T>();
		for (int i = 0; i < vs.length; i++) {
			if (cls == Byte.class) {
				result.add((T) Byte.valueOf(vs[i]));
			}
			if (cls == Integer.class) {
				result.add((T) Integer.valueOf(vs[i]));
			}
			if (cls == Short.class) {
				result.add((T) Short.valueOf(vs[i]));
			}
			if (cls == Long.class) {
				result.add((T) Long.valueOf(vs[i]));
			}
			if (cls == Float.class) {
				result.add((T) Float.valueOf(vs[i]));
			}
			if (cls == Double.class) {
				result.add((T) Double.valueOf(vs[i]));
			}

		}

		return result;
	}

	/**
	 * 取当前时间的 凌晨时间 如 2018-12-20 00:00:00
	 *
	 * @return
	 */
	public static long getWholePointDate() {
		long t = System.currentTimeMillis();
		long tempStart = 8 * 60 * 60 * 1000;
		// long v=(t/temp)*temp-tempStart;
		long v = ((t - 2 * tempStart) / tempStart / 3 * 3) * tempStart + 2 * tempStart;
		return v;
	}

	/**
	 * 取当某个时间的 凌晨时间 如 2018-12-20 00:00:00
	 *
	 * @param t 某个时间
	 *
	 * @return
	 */
	public static long getWholePointDate(long t) {
		// long temp=24*60*60*1000;
		long tempStart = 8 * 60 * 60 * 1000;
		// long v=(t/temp)*temp-tempStart;
		long v = ((t - 2 * tempStart) / tempStart / 3 * 3) * tempStart + 2 * tempStart;
		return v;
	}

	public static long getWholePoint() {
		long t = System.currentTimeMillis();
		long count = t / (60 * 60 * 1000);
		long wp = 60 * 60 * 1000 * count;
		return wp;
	}

	public static long getWholePoint(long t) {

		long count = t / (60 * 60 * 1000);
		long wp = 60 * 60 * 1000 * count;
		return wp;
	}

	public static long getWholeTime45() {
		long t = System.currentTimeMillis() - 45 * 60 * 1000;
		long count = t / (60 * 60 * 1000);
		long wp = 60 * 60 * 1000 * count + 45 * 60 * 1000;
		;
		return wp;
	}

	public static long getWholeTime45(long t) {
		t = t - 45 * 60 * 1000;
		long count = t / (60 * 60 * 1000);
		long wp = 60 * 60 * 1000 * count + 45 * 60 * 1000;
		;
		return wp;
	}

	public static long getWholeTime30() {
		long t = System.currentTimeMillis() - 30 * 60 * 1000;
		long count = t / (60 * 60 * 1000);
		long wp = 60 * 60 * 1000 * count + 30 * 60 * 1000;
		;
		return wp;
	}

	public static long getWholeTime30(long t) {
		t = t - 30 * 60 * 1000;
		long count = t / (60 * 60 * 1000);
		long wp = 60 * 60 * 1000 * count + 30 * 60 * 1000;
		;
		return wp;
	}

	public static long hour1 = 1 * 60 * 60 * 1000;
	public static long mintue45 = 1 * 45 * 60 * 1000;
	public static long mintue30 = 1 * 30 * 60 * 1000;
	public static long mintue20 = 1 * 20 * 60 * 1000;
	public static long mintue10 = 1 * 10 * 60 * 1000;

	public static long getWholePointFromUser(long t) {

		long d = getWholePointDate(t);
		long dis = t - d;

		// 00:00---7:30 取 半点数 如18:30 19:.30.. 0:30

		if((dis >= 0 && dis < (7 * hour1 + mintue30)) )
		{
			
			//00:00--00:30——>取00:30
			if(dis>=0&&dis < (0 + mintue30) )
			{
				return getWholeTime30(t)+ hour1;

			}
			
			return getWholeTime30(t);

		}

		
		// 18:30---24:00 取 半点数 如18:30 19:.30.. 
		if ( (dis >= (18 * hour1 + mintue30) && dis < 24 * hour1))
		{
			//23:30--24:00 ——>取22:30
			if(dis>=(23*hour1+mintue30)&&dis < 24 * hour1)
			{
				return getWholeTime30(t)- hour1;

			}
			
			return getWholeTime30(t);

			
		}
		
		
	
		// 7:45---11:45 or 13:45---17:45 取 7:45 8:45 13:45
		if ((dis >= (7 * hour1 + mintue45) && dis < (11 * hour1 + mintue45))
				|| (dis >= (13 * hour1 + mintue45) && dis < (17 * hour1 + mintue45)))
			return getWholeTime45(t);

		// 6:30---6:45 取 5.30
//		if (dis >= (6 * hour1 + mintue30) && dis < (6 * hour1 + mintue45))
//			return getWholeTime30(t) - hour1;

		// 7:30---7:45 取 7:45
		if (dis >= (7 * hour1 + mintue30) && dis < (7 * hour1 + mintue45))
			return getWholeTime45(t) + hour1;

		// 11:45---12:45 取 10:45
		if (dis >= (11 * hour1 + mintue45) && dis < (12 * hour1 + mintue45))
			return getWholeTime45(t) - hour1;

		// 12:45---13:00 取 10:45
		if (dis >= (12 * hour1 + mintue45) && dis < (13 * hour1))
			return getWholeTime45(t) - 2 * hour1;

		// 13:00---13:45 取 13:45
		if (dis >= (13 * hour1) && dis < (13 * hour1 + mintue45))
			return getWholeTime45(t) + hour1;

		// 17:45---18:20 取 16:45
		if (dis >= (17 * hour1 + mintue45) && dis < (18 * hour1 + mintue20))
			return getWholeTime45(t) - hour1;

		// 18:20---18:30 取 18:30
		if (dis >= (18 * hour1 + mintue20) && dis < (18 * hour1 + mintue30))
			return getWholeTime30(t) + hour1;

		return getWholeTime45(t);
	}

	public static long getWholePointFromUser() {
		long t = System.currentTimeMillis();
		long d = getWholePointDate(t);
		long dis = t - d;

		// 18:30---6:30 取 半点数 如18:30 19:.30.. 0:30
		if ((dis >= 0 && dis < (6 * hour1 + mintue30)) || (dis >= (18 * hour1 + mintue30) && dis < 24 * hour1))
			return getWholeTime30();

		// 7:45---11:45 or 13:45---17:45 取 7:45 8:45 13:45
		if ((dis >= (7 * hour1 + mintue45) && dis < (11 * hour1 + mintue45))
				|| (dis >= (13 * hour1 + mintue45) && dis < (17 * hour1 + mintue45)))
			return getWholeTime45();

		// 6:30---6:45 取 5.30
		if (dis >= (6 * hour1 + mintue30) && dis < (6 * hour1 + mintue45))
			return getWholeTime30() - hour1;

		// 6:45---7:45 取 7:45
		if (dis >= (6 * hour1 + mintue45) && dis < (7 * hour1 + mintue45))
			return getWholeTime45() + hour1;

		// 11:45---12:45 取 10:45
		if (dis >= (6 * hour1 + mintue45) && dis < (12 * hour1 + mintue45))
			return getWholeTime45() - hour1;

		// 12:45---13:00 取 10:45
		if (dis >= (12 * hour1 + mintue45) && dis < (13 * hour1))
			return getWholeTime45() - 2 * hour1;

		// 13:00---13:45 取 13:45
		if (dis >= (13 * hour1) && dis < (13 * hour1 + mintue45))
			return getWholeTime45() + hour1;

		// 17:45---18:20 取 16:45
		if (dis >= (17 * hour1 + mintue45) && dis < (18 * hour1 + mintue20))
			return getWholeTime45() - hour1;

		// 18:20---18:30 取 18:30
		if (dis >= (18 * hour1 + mintue20) && dis < (18 * hour1 + mintue30))
			return getWholeTime30() + hour1;

		return getWholeTime45();
	}

	/**
	 * 取当前时间的 凌晨时间
	 *
	 * @param t      某一时刻的时间
	 * @param preDay 0表示当天。-1表示前天
	 *
	 * @return
	 */
	public static long getWholePointDate(long t, int preDay) {
		long day = 24 * 60 * 60 * 1000;
		long tempStart = 8 * 60 * 60 * 1000;
		// long v=(t/temp)*temp-tempStart;
		long v = ((t - 2 * tempStart) / tempStart / 3 * 3) * tempStart + 2 * tempStart + day * preDay;
		return v;
	}


	
	/**
	 * Module查找机芯
	 *
	 * @param line
	 *
	 * @return //9R48-55E366W (电源、恒流一体板) 9R48 机芯 Module
	 */
	public static String findModule(String line) {
		// 9R48-55E366W (电源、恒流一体板)
		// String format="([0-9,A-Z,a-z]{1,100})([S%,s%]{0,400})";
		try {
			String format = "([\\w]{1,100})[-]([S%,s%]{0,400})";
			Pattern p = Pattern.compile(format);
			Matcher m = p.matcher(line.trim());
			m.find();
			return m.group(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}

	}

	/**
	 * model查找机型
	 *
	 * @param line
	 *
	 * @return //9R48-55E366W (电源、恒流一体板) 55E366W机型 model
	 */
	public static String findModel(String line) {
		// 9R48-55E366W (电源、恒流一体板)
		// String format="([0-9,A-Z,a-z]{1,100})([S%,s%]{0,400})";

		try {
			String format = "[-]([\\w]{1,100})([S%,s%]{0,400})";
			Pattern p = Pattern.compile(format);
			Matcher m = p.matcher(line.trim());
			m.find();
			return m.group(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}

	}

	public static String findScreen(String line) {
		// 9R48-55E366W 整机配屏组件(7618-T5500C-Y590)
		try {
			String format = "([\\(|（]([\\s]*[\\w,-]{1,250}[\\s]*)[\\)|）])";
			Pattern p = Pattern.compile(format);
			Matcher m = p.matcher(line.trim());
			m.find();
			return m.group(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	/**
	 * 生成计划号
	 *
	 * @param orderNo
	 * @param planDate
	 * @param line
	 *
	 * @return
	 */
	public static String bulidPlanNo(String orderNo, long planDate, String line) {
		return line + TimeToString(planDate, "MMdd") + orderNo;
	}



	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(String ids, Class<T> t) throws Exception {
		List<T> list = new ArrayList<T>();
		try {
			String[] vs = ids.split(",");

			for (String line : vs) {
				if (!PUtils.isEmpty(line)) {
					try {
						if (t.getName().equals("java.lang.Integer"))
							list.add((T) Integer.valueOf(line));

						if (t.getName().equals("java.lang.Long"))
							list.add((T) Long.valueOf(line));

						if (t.getName().equals("java.lang.Short"))
							list.add((T) Short.valueOf(line));

						if (t.getName().equals("java.lang.String"))
							list.add((T) String.valueOf(line));

					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	



	public static String removeEndChar(String s) {
		if (!isEmpty(s))
			s = s.substring(0, s.length() - 1);
		return s;
	}

	public static boolean compareString(String s1, String s2) {
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null || s2 == null)
			return false;
		return s1.equals(s2);
	}

	public static Map<String, Object> initFilter() {

		Map<String, Object> filter = new HashMap<String, Object>();
		return filter;
	}

	public static Map<String, Object> initFilter(Map<String, Object> filter) {
		if (filter == null || filter.isEmpty()) {
			filter = new HashMap<String, Object>();
			filter.put("start", 0);
			filter.put("limit", 2000);
			return filter;
		}
		Object start = filter.get("start");
		if (start == null) {
			filter.put("start", 0);
			filter.put("limit", 2000);
		}
		return filter;
	}

	public static Map<String, Object> initFilter(Integer start, Integer limit) {
		Map<String, Object> filter = initFilter(null);
		if (start != null)
			filter.put("start", start);
		else
			filter.put("start", 0);
		if (limit != null)
			filter.put("limit", limit);
		else
			filter.put("limit", 200);
		return filter;
	}

	public static void initFilterBetweenTime(long time, Map<String, Object> filter) {
		long st = getWholePointDate(time);
		long ft = st + 24 * 60 * 60 * 1000;
		filter.put("st", st);
		filter.put("ft", ft);

	}

	/**
	 * 1深拷贝
	 *
	 * @param <T>
	 * @param obj
	 *
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deepCopy(T obj) throws IOException, ClassNotFoundException {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			// 将流序列化成对象
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			T result = (T) ois.readObject();
			return result;
		} finally {
			try {
				oos.close();
				ois.close();
			} catch (Exception e) {
			}
		}
	}

	public static String fomratBomVersion(int version) {
		return formatInt(version, 3);
	}

	public static String getDayFromTime(long time) {
		return TimeToString(time, "dd");
	}

	public static String getYearFromTime(long time) {
		return TimeToString(time, "yyyy");
	}

	public static String getMonthDayFromTime(long time) {
		return TimeToString(time, "MMdd");
	}

	public static String getMonthFromTime(long time) {
		return TimeToString(time, "MM");
	}

	public static Integer getDaysBetweenTime(long st, long et) {
		int days = 0;
		while (st < et) {
			days++;
			st = st + 24 * 60 * 60 * 1000L;
		}
		return days;
	}

	public static String bulidUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		return uuid;
	}
   
	public static String getUUID()
	{
		String  uuid=UUID.randomUUID().toString();
		return  uuid;
	}
	
	
	public static String removeDecimals(String src) {
		if (isEmpty(src))
			return src;
		// if(!src.contains(".")) return src;
		String[] result = src.split("\\.");
		return result[0];
	}

	public static String connectString(String conectChar, Object... s) {

		String result = s[0] == null ? "null" : s[0].toString();
		for (int i = 1; i < s.length; i++) {

			result = result + conectChar + (s[i] == null ? "null" : s[i].toString());
		}
		return result;
	}

	/**
	 * anction 字符串是否包含中文
	 *
	 * @param str
	 *
	 * @return 包含中文true 否则 false
	 */
	public static boolean isContainChinese(String str) {

		try {
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(str);
			return m.find();

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检测是否为WMS label标签
	 *
	 * @param line
	 *
	 * @return
	 */
	public static boolean checkIsLabel(String line) {

		try {
			String format = "^[0-9]{12,40}$";
			Pattern p = Pattern.compile(format);
			Matcher m = p.matcher(line.trim());
			return m.find();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}

	}

	/**
	 * 检测是否为UUID标签格式
	 *
	 * @param line
	 *
	 * @return
	 */
	public static boolean checkIsUUID(String line) {
		try {
			String format = "^[0-9,A-F,a-f]{32,32}$";
			Pattern p = Pattern.compile(format);
			Matcher m = p.matcher(line.trim());
			return m.find();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}

	}

	/**
	 * 返回当月的第一天的0.0.0.0
	 *
	 * @return
	 */
	public static long getCurrentFirstDay() {
		long fTime = 0;
		Calendar cale = null;
		// 获取当月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		fTime = getWholePointDate(cale.getTimeInMillis()); // 返回毫秒数
		return fTime;
	}

	/**
	 * 返回当月的最后一天的23.59.59.999
	 *
	 * @return
	 */
	public static long getCurrentEndDay() {
		long eTime = 0;
		Calendar cale = null;
		// 获取当月的第一天
		cale = Calendar.getInstance();
		// 设置为当月最后一天
		cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
		// 将小时至23
		cale.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		cale.set(Calendar.MINUTE, 59);
		// 将秒至59
		cale.set(Calendar.SECOND, 59);
		// 将毫秒至999
		cale.set(Calendar.MILLISECOND, 999);
		eTime = cale.getTimeInMillis(); // 返回毫秒数
		return eTime;
	}

	/**
	 * 获取当年的第一天的0.0.0.0
	 *
	 * @return
	 */
	public static long getCurrentFirstYear() {
		long fYear = 0;
		Calendar cale = null;
		// 获取当年的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.YEAR, 0);
		cale.set(Calendar.DAY_OF_YEAR, 1);
		fYear = getWholePointDate(cale.getTimeInMillis()); // 返回毫秒数
		return fYear;
	}

	/**
	 * 获取当年的最后一天的23.59.59.999
	 *
	 * @return
	 */
	public static long getCurrentEndYear() {
		long eYear = 0;
		Calendar cale = null;
		cale = Calendar.getInstance();
		// 设置为当年最后一天
		cale.set(Calendar.DAY_OF_YEAR, cale.getActualMaximum(Calendar.DAY_OF_YEAR));
		// 将小时至23
		cale.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		cale.set(Calendar.MINUTE, 59);
		// 将秒至59
		cale.set(Calendar.SECOND, 59);
		// 将毫秒至999
		cale.set(Calendar.MILLISECOND, 999);
		eYear = cale.getTimeInMillis(); // 返回毫秒数
		return eYear;
	}

	/**
	 * 获取上个月的第一天的整点0.0.0.0
	 *
	 * @return
	 */
	public static long getTopMonthFirstDay() {
		long tDate = 0;
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, -1);
		calendar1.set(Calendar.DAY_OF_MONTH, 1);
		tDate = getWholePointDate(calendar1.getTimeInMillis());
		return tDate;
	}

	/**
	 * 获取上个月的最后一天的整点23.59.59.999
	 *
	 * @return
	 */
	public static long getTopMonthEndDay() {
		long eDate = 0;
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.DAY_OF_MONTH, 0);
		// 将小时至23
		calendar1.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		calendar1.set(Calendar.MINUTE, 59);
		// 将秒至59
		calendar1.set(Calendar.SECOND, 59);
		// 将毫秒至999
		calendar1.set(Calendar.MILLISECOND, 999);
		eDate = calendar1.getTimeInMillis(); // 返回毫秒数
		return eDate;
	}

	/**
	 * 获取上一年的第一天的整点0.0.0.0
	 *
	 * @return
	 */
	public static long getTopYearFirstDay() {
		long tDate = 0;
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.YEAR, -1);
		calendar1.set(Calendar.DAY_OF_YEAR, 1);
		tDate = getWholePointDate(calendar1.getTimeInMillis());
		return tDate;
	}

	/**
	 * 获取上一年的最后一天的23.59.59.999
	 *
	 * @return
	 */
	public static long getTopYearEndDay() {
		long eDate = 0;
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.DAY_OF_YEAR, 0);
		// 将小时至23
		calendar1.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		calendar1.set(Calendar.MINUTE, 59);
		// 将秒至59
		calendar1.set(Calendar.SECOND, 59);
		// 将毫秒至999
		calendar1.set(Calendar.MILLISECOND, 999);
		eDate = calendar1.getTimeInMillis(); // 返回毫秒数
		return eDate;
	}

	/**
	 * 返回指定时间的当月的第一天的0.0.0.0
	 *
	 * @return
	 */
	public static long getCurrentFirstDay(Long time) {
		long fTime = 0;
		Date date = new Date(time);
		Calendar cale = null;
		// 获取当月的第一天
		cale = Calendar.getInstance();
		cale.setTime(date);
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		fTime = getWholePointDate(cale.getTimeInMillis()); // 返回毫秒数
		return fTime;
	}

	/**
	 * 返回指定时间的当月的最后一天的23.59.59.999
	 *
	 * @return
	 */
	public static long getCurrentEndDay(Long time) {
		long eTime = 0;
		Date date = new Date(time);
		Calendar cale = null;
		// 获取当月的第一天
		cale = Calendar.getInstance();
		cale.setTime(date);
		// 设置为当月最后一天
		cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
		// 将小时至23
		cale.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		cale.set(Calendar.MINUTE, 59);
		// 将秒至59
		cale.set(Calendar.SECOND, 59);
		// 将毫秒至999
		cale.set(Calendar.MILLISECOND, 999);
		eTime = cale.getTimeInMillis(); // 返回毫秒数
		return eTime;
	}

	/**
	 * 获取指定时间的当年的第一天的0.0.0.0
	 *
	 * @return
	 */
	public static long getCurrentFirstYear(Long time) {
		long fYear = 0;
		Calendar cale = null;
		// 获取当年的第一天
		cale = Calendar.getInstance();
		Date date = new Date(time);
		cale.setTime(date);
		cale.add(Calendar.YEAR, 0);
		cale.set(Calendar.DAY_OF_YEAR, 1);
		fYear = getWholePointDate(cale.getTimeInMillis()); // 返回毫秒数
		return fYear;
	}

	/**
	 * 获取指定时间的当年的最后一天的23.59.59.999
	 *
	 * @return
	 */
	public static long getCurrentEndYear(Long time) {
		long eYear = 0;
		Calendar cale = null;
		cale = Calendar.getInstance();
		Date date = new Date(time);
		cale.setTime(date);
		// 设置为当年最后一天
		cale.set(Calendar.DAY_OF_YEAR, cale.getActualMaximum(Calendar.DAY_OF_YEAR));
		// 将小时至23
		cale.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		cale.set(Calendar.MINUTE, 59);
		// 将秒至59
		cale.set(Calendar.SECOND, 59);
		// 将毫秒至999
		cale.set(Calendar.MILLISECOND, 999);
		eYear = cale.getTimeInMillis(); // 返回毫秒数
		return eYear;
	}

	/**
	 * 返回指定时间的当天的最后时间的23.59.59.999
	 *
	 * @return
	 */
	public static long getCurrentDayEndTime(Long time) {
		long eTime = 0;
		Date date = new Date(time);
		Calendar cale = null;
		// 获取当月的第一天
		cale = Calendar.getInstance();
		cale.setTime(date);
		// 将小时至23
		cale.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		cale.set(Calendar.MINUTE, 59);
		// 将秒至59
		cale.set(Calendar.SECOND, 59);
		// 将毫秒至999
		cale.set(Calendar.MILLISECOND, 999);
		eTime = cale.getTimeInMillis(); // 返回毫秒数
		return eTime;
	}

	/**
	 * 返回指定时间的当天的开始时间的23.59.59.999
	 *
	 * @return
	 */
	public static long getCurrentDayFirstTime(Long time) {
		long eTime = 0;
		Date date = new Date(time);
		Calendar cale = null;
		// 获取当月的第一天
		cale = Calendar.getInstance();
		cale.setTime(date);
		// 将小时至23
		cale.set(Calendar.HOUR_OF_DAY, 00);
		// 将分钟至59
		cale.set(Calendar.MINUTE, 00);
		// 将秒至59
		cale.set(Calendar.SECOND, 00);
		// 将毫秒至999
		cale.set(Calendar.MILLISECOND, 000);
		eTime = cale.getTimeInMillis(); // 返回毫秒数
		return eTime;
	}

	/**
	 * 获取指定时间的上个月的第一天的整点0.0.0.0
	 *
	 * @return
	 */
	public static long getTopMonthFirstDay(Long time) {
		long tDate = 0;
		Calendar cale = Calendar.getInstance();
		Date date = new Date(time);
		cale.setTime(date);
		cale.add(Calendar.MONTH, -1);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		tDate = getWholePointDate(cale.getTimeInMillis());
		return tDate;
	}

	/**
	 * 获取指定时间的上个月的最后一天的整点23.59.59.999
	 *
	 * @return
	 */
	public static long getTopMonthEndDay(Long time) {
		long eDate = 0;
		Calendar cale = Calendar.getInstance();
		Date date = new Date(time);
		cale.setTime(date);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		// 将小时至23
		cale.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		cale.set(Calendar.MINUTE, 59);
		// 将秒至59
		cale.set(Calendar.SECOND, 59);
		// 将毫秒至999
		cale.set(Calendar.MILLISECOND, 999);
		eDate = cale.getTimeInMillis(); // 返回毫秒数
		return eDate;
	}

	/**
	 * 获取指定时间的上一年的第一天的整点0.0.0.0
	 *
	 * @return
	 */
	public static long getTopYearFirstDay(Long time) {
		long tDate = 0;
		Calendar cale = Calendar.getInstance();
		Date date = new Date(time);
		cale.setTime(date);
		cale.add(Calendar.YEAR, -1);
		cale.set(Calendar.DAY_OF_YEAR, 1);
		tDate = getWholePointDate(cale.getTimeInMillis());
		return tDate;
	}

	/**
	 * 获取指定时间的上一年的最后一天的23.59.59.999
	 *
	 * @return
	 */
	public static long getTopYearEndDay(Long time) {
		long eDate = 0;
		Calendar cale = Calendar.getInstance();
		Date date = new Date(time);
		cale.setTime(date);
		cale.set(Calendar.DAY_OF_YEAR, 0);
		// 将小时至23
		cale.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		cale.set(Calendar.MINUTE, 59);
		// 将秒至59
		cale.set(Calendar.SECOND, 59);
		// 将毫秒至999
		cale.set(Calendar.MILLISECOND, 999);
		eDate = cale.getTimeInMillis(); // 返回毫秒数
		return eDate;
	}

	/**
	 * 获取指定时间的当年的第一天的0.0.0.0
	 *
	 * @return
	 */
	public static long getCurrentYearFirstDay(String year) throws ParseException {
		Date yearP = new SimpleDateFormat("yyyy").parse(year);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(yearP);
		calendar1.add(Calendar.YEAR, 0);
		calendar1.set(Calendar.DAY_OF_YEAR, 1);
		return calendar1.getTimeInMillis();
	}

	/**
	 * 获取指定时间的当年的最后一天的23.59.59.999
	 *
	 * @return
	 */
	public static long getCurrentYearEndDay(String year) throws ParseException {
		Date yearP = new SimpleDateFormat("yyyy").parse(year);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(yearP);
		// 设置为当年最后一天
		calendar1.set(Calendar.DAY_OF_YEAR, calendar1.getActualMaximum(Calendar.DAY_OF_YEAR));
		// 将小时至23
		calendar1.set(Calendar.HOUR_OF_DAY, 23);
		// 将分钟至59
		calendar1.set(Calendar.MINUTE, 59);
		// 将秒至59
		calendar1.set(Calendar.SECOND, 59);
		// 将毫秒至999
		calendar1.set(Calendar.MILLISECOND, 999);
		return calendar1.getTimeInMillis();
	}

	/**
	 * 获取任意时间是type的第几天
	 *
	 * @param date
	 * @param type
	 *
	 * @return
	 */
	public static Integer getDayInType(Long date, String type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(date));
		type = type == null ? "monthDay" : type;
		if (type == "monthDay") {
			int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
			return monthDay;
		}
		if (type == "yearDay") {
			int yearDay = calendar.get(Calendar.DAY_OF_YEAR);
			return yearDay;
		} else {
			int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
			if (weekDay == 1) {
				weekDay = 7;
			} else {
				weekDay = weekDay - 1;
			}
			return weekDay;
		}
	}

	/**
	 * 获取传入时间所在月份的天数
	 *
	 * @param date
	 *
	 * @return
	 */
	public static int getMonthDaysByAnyTime(Long date) {
		Calendar a = Calendar.getInstance();
		a.setTime(new Date(date));
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static long getRecentWholeColock(int colock){
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		// 获取当前系统时间
		LocalDateTime toDay = LocalDateTime.now();
        /*
          判断当天时间是否大于早上8点
          true: 获取昨天早上8点 - 当天早上8点
          false: 获取当天早上8点 - 明天早上8点时间
         */
		if(toDay.getHour() < colock){
			toDay = toDay.plusDays(-1);
		}
		// 获取当天早上8点时间  时间精确到8:00:00
		LocalDateTime startDateTime = LocalDateTime.of(toDay.toLocalDate(), LocalTime.MIN.withHour(colock));
		return startDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

	public static boolean checkIsInt(String line) {
		try {
			String format = "([0-9]{1,1000})[.]{1,1}([0]{1,1000}$)";
			// String format = "([0,9]{0,1000})[\\.]([0,3,9]{0,1000})";
			Pattern p = Pattern.compile(format);
			Matcher m = p.matcher(line.trim());
			return m.find();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String findStringBeforePoint(String line) {
		try {
			if (PUtils.isEmpty(line))
				return line;
			String format = "([0-9,a-z,A-Z]{1,1000})[.]{0,1}([0-9,a-z,A-Z]{0,1000})";
			// String format = "([0,9]{0,1000})[\\.]([0,3,9]{0,1000})";
			Pattern p = Pattern.compile(format);
			Matcher m = p.matcher(line.trim());
			m.find();
			return m.group(1);
		} catch (Exception e) {
			e.printStackTrace();
			return line;
		}
	}

	public static String formatDateTime(String d) {
		return d.replace("-", "").replace("/", "").replace(".", "");
	}

	public static String createRadomAufnr() {
		String d = TimeToString(System.currentTimeMillis(), "MMdd");
		int n = (int) (Math.random() * 100000000);
		// 5030 00119047
		String d2 = String.format("%06d", n);
		return d + d2;
	}

	public static String[] removeRepeat(String[] arr) {
		// 创建一个集合
		List<String> list = new ArrayList<String>();
		// 遍历数组往集合里存元素
		for (int i = 0; i < arr.length; i++) {
			// 如果集合里面没有相同的元素才往里存
			if (!list.contains(arr[i])) {
				list.add(arr[i]);
			}
		}

		// toArray()方法会返回一个包含集合所有元素的Object类型数组
		String[] newArr = list.toArray(new String[list.size()]);
		return newArr;
	}

	public static Integer getNumericFromString(String str) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(str);
		String num = matcher.replaceAll("").trim();
		if (!PUtils.isEmpty(num)) {
			return Integer.valueOf(num);
		}
		return 0;

	}

	public static Long getLongFromString(String str) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(str);
		String num = matcher.replaceAll("").trim();
		if (!PUtils.isEmpty(num)) {
			return Long.valueOf(num);
		}
		return 0L;

	}

	public static String getStrFromString(String str) {
		Pattern pattern = Pattern.compile("[^A-Za-z]");
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll("").trim();
	}

	public static String getStrFromString1(String str) {
		if (PUtils.isEmpty(str)) {
			return "";
		}
		Pattern pattern = Pattern.compile("[^A-Za-z]");
		Matcher matcher = pattern.matcher(str);
		String result = matcher.replaceAll("").trim();
		if (PUtils.isEmpty(result)) {
			return "1";
		}
		return matcher.replaceAll("").trim();
	}

	public static Integer getMidNumbericFromString(String str) {
		if (PUtils.isEmpty(str))
			return 0;
		String[] cartons = str.split("-");
		Integer midCar = 0;
		for (String car : cartons) {
			if (cartons.length == 1) {
				midCar = PUtils.getNumericFromString(car) * 2;
			} else {
				midCar = midCar + PUtils.getNumericFromString(car);
			}
		}
		return midCar / 2;
	}

	public static Integer getMidNumbericFromString1(String str) {
		if (PUtils.isEmpty(str))
			return 0;
		String[] cartons = str.split("-|,");
		Integer midCar = 0;
		for (String car : cartons) {
			if (cartons.length == 1) {
				midCar = PUtils.getNumericFromString(car) * 2;
			} else {
				midCar = midCar + PUtils.getNumericFromString(car);
			}
		}

		if (cartons.length == 1) {
			return midCar / 2;
		} else {
			return midCar / cartons.length;
		}

	}

	public static Integer getFirstNumbericFromString(String str) {
		if (PUtils.isEmpty(str))
			return 0;
		String[] cartons = str.split("-|,");
		Integer firstCar = 0;
		for (String car : cartons) {
			firstCar = PUtils.getNumericFromString(car);
			break;
		}
		return firstCar;
	}

	public static String getMidStrFromString(String str) {
		if (PUtils.isEmpty(str)) {
			return "";
		}
		String[] cartons = str.split("-");
		String midCar = "";
		for (String car : cartons) {
			midCar = getStrFromString(car);
		}
		return midCar;
	}

//	/**
//	 * 将卡板号如BB5，BB6转为对应的ascil码值
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public static Integer getNumFromString(String str) {
//		if (PUtils.isEmpty(str)) {
//			return 0;
//		}
//
//		String chars = getMidStrFromString(str);
//		Integer num = getMidNumbericFromString(str);
//		chars = chars + num;
//
//		Integer d = 0;
//		for (int i = 0; i < chars.length(); i++) {
//			d = d + (int) chars.charAt(i);
//		}
//
//		return d;
//	}

	public static Integer getCartonsNumFromString(String str) {
		if (PUtils.isEmpty(str)) {
			return 0;
		}
		String[] cartons = str.split("-");
		if (cartons.length == 1) {
			return 1;
		} else {
			return (getNumericFromString(cartons[1]) - getNumericFromString(cartons[0])) + 1;
		}
	}

	public static boolean matchAfterNum(String str) {
		Pattern pattern = Pattern.compile("\\d+[a-zA-Z]+");
		return pattern.matcher(str).matches();
	}

	public static boolean matchBeforeNum(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z]+\\d+");
		return pattern.matcher(str).matches();
	}

	public static boolean isContainsLetter(String src) {
		boolean flag = false;
		for (int i = 0; i < src.length(); i++) {
			String convert = Convert.convert(String.class, src.charAt(i));
			if (isLetter(convert)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 拼接卡板号，箱号等 s1,s2,s3-->s1-s3
	 *
	 * @param nos
	 *
	 * @return
	 */
	public static String mergePitsNoOrCaton(List<String> nos) {
		String result = "";
		if (nos.size() > 0) {
			Integer index = -1;
			String pre = PUtils.getStrFromString(nos.get(0));
			if (!PUtils.isEmpty(pre))
				index = nos.get(0).indexOf(pre);
			List<Integer> pitsNoList = new ArrayList<Integer>();
			for (String item : nos) {
				pitsNoList.add(PUtils.getNumericFromString(item));
			}

			Collections.sort(pitsNoList);
			int state = 0;
			if (index == 0) {
				for (int i = 0; i < pitsNoList.size(); i++) {
					if (i == pitsNoList.size() - 1) {
						state = 2;
						if (result.endsWith(",")) {
							result += pre + pitsNoList.get(i);
						} else {
							result = result.substring(0, result.lastIndexOf("-") + 1);
							result += pre + pitsNoList.get(i);
						}
					}
					if (state == 0) {
						if (pitsNoList.get(i + 1).equals(pitsNoList.get(i) + 1)) {
							result += pre + pitsNoList.get(i);
							result += "-";
							state = 1;
						} else {
							result += pre + pitsNoList.get(i);
							result += ",";
						}
					} else if (state == 1) {
						if (!pitsNoList.get(i + 1).equals(pitsNoList.get(i) + 1)) {
							result = result.substring(0, result.lastIndexOf("-") + 1);
							result += pre + pitsNoList.get(i);
							result += ",";
							state = 0;
						} else {
							result = result.substring(0, result.lastIndexOf("-") + 1);
							result += pre + pitsNoList.get(i);
						}
					}
				}

			}
			// 最有一位为字母1R,2R
			if (index > 0) {
				for (int i = 0; i < pitsNoList.size(); i++) {
					if (i == pitsNoList.size() - 1) {
						state = 2;
						if (result.endsWith(",")) {
							result += pitsNoList.get(i) + pre;
						} else {
							result = result.substring(0, result.lastIndexOf("-") + 1);
							result += pitsNoList.get(i) + pre;
						}
					}
					if (state == 0) {
						if (pitsNoList.get(i + 1).equals(pitsNoList.get(i) + 1)) {
							result += pitsNoList.get(i) + pre;
							result += "-";
							state = 1;
						} else {
							result += pitsNoList.get(i) + pre;
							result += ",";
						}
					} else if (state == 1) {
						if (!pitsNoList.get(i + 1).equals(pitsNoList.get(i) + 1)) {
							result = result.substring(0, result.lastIndexOf("-") + 1);
							result += pitsNoList.get(i) + pre;
							result += ",";
							state = 0;
						} else {
							result = result.substring(0, result.lastIndexOf("-") + 1);
							result += pitsNoList.get(i) + pre;
						}
					}
				}

			}
			// 全为数字
			if (index < 0) {
				for (int i = 0; i < pitsNoList.size(); i++) {
					if (i == pitsNoList.size() - 1) {
						state = 2;
						if (result.endsWith(",")) {
							result += pitsNoList.get(i);
						} else {
							result = result.substring(0, result.lastIndexOf("-") + 1);
							result += pitsNoList.get(i);
						}
					}
					if (state == 0) {
						if (pitsNoList.get(i + 1).equals(pitsNoList.get(i) + 1)) {
							result += pitsNoList.get(i);
							result += "-";
							state = 1;
						} else {
							result += pitsNoList.get(i);
							result += ",";
						}
					} else if (state == 1) {
						if (!pitsNoList.get(i + 1).equals(pitsNoList.get(i) + 1)) {
							result = result.substring(0, result.lastIndexOf("-") + 1);
							result += pitsNoList.get(i);
							result += ",";
							state = 0;
						} else {
							result = result.substring(0, result.lastIndexOf("-") + 1);
							result += pitsNoList.get(i);
						}
					}
				}

			}
		}
		return result;
	}

	public static String mergePitsNoOrCartonPro(List<String> nos) {
		List<String> result = new ArrayList<>();
		// 重组
		for (String s : nos) {
			String convert = Convert.convert(String.class, s.charAt(0));

			// 包含字母
			if (isContainsLetter(s)) {
				// 首字符是字母
				if (isLetter(convert)) {
					// 带“-”
					if (s.contains("-")) {
						String[] split = StrUtil.split(s, "-");
						String letter = getStrFromString(split[0]);
						Integer start = getNumericFromString(split[0]);
						Integer end = getNumericFromString(split[1]);
						for (int i = start; i < end + 1; i++) {
							String v = letter + i;
							result.add(v);
						}
					}
					// 不带“-”
					if (!s.contains("-")) {
						result.add(s);
					}
				}
				// 首字符不是字母
				if (!isLetter(convert)) {
					// 带“-”
					if (s.contains("-")) {
						String[] split = StrUtil.split(s, "-");
						String letter = getStrFromString(split[0]);
						Integer start = getNumericFromString(split[0]);
						Integer end = getNumericFromString(split[1]);
						for (int i = start; i < end + 1; i++) {
							String v = i + letter;
							result.add(v);
						}
					}
					// 不带“-”
					if (!s.contains("-")) {
						result.add(s);
					}
				}
			}

			// 不包含字母
			if (!isContainsLetter(s)) {
				// 带“-”
				if (s.contains("-")) {
					String[] split = StrUtil.split(s, "-");
					Integer start = getNumericFromString(split[0]);
					Integer end = getNumericFromString(split[1]);
					for (int i = start; i < end + 1; i++) {
						String v = Convert.convert(String.class, i);
						result.add(v);
					}
				}
				// 不带“-”
				if (!s.contains("-")) {
					result.add(s);
				}
			}
		}
		// 去重
		List<String> distinct = CollUtil.distinct(result);
		return mergePitsNoOrCaton(distinct);
	}

	public static String mergePitsNoOrCartonPro3(List<String> nos) {
		List<String> result = new ArrayList<>();
		// 重组
		for (String s : nos) {
			String convert = Convert.convert(String.class, s.charAt(0));

			// 包含字母
			if (isContainsLetter(s)) {
				// 首字符是字母
				if (isLetter(convert)) {
					// 带“-”
					String[] s1 = StrUtil.split(s, ",");
					for (String si : s1) {
						if (si.contains("-")) {
							String[] split = StrUtil.split(si, "-");
							String letter = getStrFromString(split[0]);
							Integer start = getNumericFromString(split[0]);
							Integer end = getNumericFromString(split[1]);
							for (int i = start; i < end + 1; i++) {
								String v = letter + i;
								result.add(v);
							}
						}
						// 不带“-”
						if (!si.contains("-")) {
							result.add(si);
						}
					}
				}
				// 首字符不是字母
				if (!isLetter(convert)) {
					// 带“-”
					String[] s1 = StrUtil.split(s, ",");
					for (String si : s1) {
						if (si.contains("-")) {
							String[] split = StrUtil.split(si, "-");
							String letter = getStrFromString(split[0]);
							Integer start = getNumericFromString(split[0]);
							Integer end = getNumericFromString(split[1]);
							for (int i = start; i < end + 1; i++) {
								String v = i + letter;
								result.add(v);
							}
						}
						// 不带“-”
						if (!si.contains("-")) {
							result.add(si);
						}
					}

				}
			}

			// 不包含字母
			if (!isContainsLetter(s)) {
				// 带“-”
				if (s.contains("-")) {
					String[] split = StrUtil.split(s, "-");
					Integer start = getNumericFromString(split[0]);
					Integer end = getNumericFromString(split[1]);
					for (int i = start; i < end + 1; i++) {
						String v = Convert.convert(String.class, i);
						result.add(v);
					}
				}
				// 不带“-”
				if (!s.contains("-")) {
					result.add(s);
				}
			}
		}
		// 去重
		List<String> distinct = CollUtil.distinct(result);
		return mergePitsNoOrCaton(distinct);
	}

	// 特有方法：获取不同值的箱数/卡板数
	public static double getSubCabinetPitQtyOrBoxNum(List<String> nos) {
		List<String> result = new ArrayList<>();
		// 重组
		for (String s : nos) {
			if (s.contains("-")) {

				if (!isEmpty(getFirstLetterStr(s))) {
					// 带字母
					String[] split = StrUtil.split(s, "-");
					String oStr = getStrFromString(split[0]);
					Integer oNum = getNumericFromString(split[0]);
//					String tStr = getStrFromString(split[1]);
					Integer tNum = getNumericFromString(split[1]);
					for (int i = oNum; i < tNum + 1; i++) {
						String v = oStr + i;
						result.add(v);
					}
				} else {
					// 不带字母
					String[] split = StrUtil.split(s, "-");
					Integer oNum = getNumericFromString(split[0]);
					Integer tNum = getNumericFromString(split[1]);
					for (int i = oNum; i < tNum + 1; i++) {
						String v = Convert.convert(String.class, i);
						result.add(v);
					}
				}
				continue;
			}
			result.add(s);
		}
		// 去重
		ArrayList<String> distinct = CollUtil.distinct(result);
		return Convert.convert(Double.class, distinct.size());
	}

	// 卡板号拼接 -- 1 2 -> 12 1-3 5 -> 1235
	public static String getSplitString(List<String> list) {
		String flag = "";
		List<String> result = new ArrayList<>();
		// 重组
		for (String s : list) {
			if (s.contains("-")) {

				if (!isEmpty(getFirstLetterStr(s))) {
					// 带字母
					String[] split = StrUtil.split(s, "-");
					String oStr = getStrFromString(split[0]);
					Integer oNum = getNumericFromString(split[0]);
//					String tStr = getStrFromString(split[1]);
					Integer tNum = getNumericFromString(split[1]);
					for (int i = oNum; i < tNum + 1; i++) {
						String v = oStr + i;
						result.add(v);
					}
				} else {
					// 不带字母
					String[] split = StrUtil.split(s, "-");
					Integer oNum = getNumericFromString(split[0]);
					Integer tNum = getNumericFromString(split[1]);
					for (int i = oNum; i < tNum + 1; i++) {
						String v = Convert.convert(String.class, i);
						result.add(v);
					}
				}
				continue;
			}
			result.add(s);
		}
		for (String s : result) {
			flag += s;
		}
		return flag;
	}

	/**
	 * s1-s3,s7拆成列表 s1,s2,s3,s7
	 * 
	 * @param nos
	 * @return
	 */
	public static List<String> spliterPitsNoOrCaton(String nos) {
		ArrayList<String> result = new ArrayList<>();

		if (PUtils.isEmpty(nos)) {
			return result;
		}

		String[] lines = nos.split(",");

		for (String line : lines) {
			if (!PUtils.isEmpty(line)) {
				if (line.contains("-")) {
					String[] vs = line.split("-");
					String st = vs[0];
					String ft = vs[1];

					String preChar = getStrFromString(st).trim();
					int postionSt = getNumericFromString(st);
					int postionft = getNumericFromString(ft);

					for (int i = postionSt; i <= postionft; i++) {
						result.add(preChar + i);
					}

				} else {
					result.add(line.trim());
				}

			}
		}

		return result;
	}

	/**
	 * s1-s3,s7拆成列表 s1,s2,s3,s7 字母在前，字母在后
	 * 
	 * @param nos
	 * @return
	 */
	public static List<String> spliterPitsNoOrCatonPro(String nos) {
		ArrayList<String> result = new ArrayList<>();

		if (PUtils.isEmpty(nos)) {
			return result;
		}

		String[] lines = nos.split(",");

		for (String line : lines) {
			if (!PUtils.isEmpty(line)) {
				if (line.contains("-")) {
					String[] vs = line.split("-");
					String st = vs[0];
					String ft = vs[1];

					String preChar = getStrFromString(st).trim();
					int postionSt = getNumericFromString(st);
					int postionft = getNumericFromString(ft);

					if (matchBeforeNum(st)) {
						for (int i = postionSt; i <= postionft; i++) {
							if(!result.contains(preChar + i))
								result.add(preChar + i);
						}
					} else {
						for (int i = postionSt; i <= postionft; i++) {
							if(!result.contains(i + preChar))
								result.add(i + preChar);
						}
					}

				} else {
					if(!result.contains(line.trim()))
						result.add(line.trim());
				}

			}
		}

		return result;
	}

	public static String mergePitsNoOrCartonPro2(String nos) {
		List<String> result = new ArrayList<>();
		String[] lines = nos.split(",");
		HashSet<String> preNokeys = new HashSet<>();// 记录不同箱的前缀，分类
		Map<String, List<String>> preNoMaps = new HashMap<>();// E4,1E-2E，3E类似字符串的key值都是convert E，则最终结果是1E-4E convert +
																// "1":代表首字符是字符 convert + "2":代表首字符不是字符
		// 重组
		for (String s : lines) {
			String convert = Convert.convert(String.class, s.charAt(0));

			// 包含字母
			if (isContainsLetter(s)) {
				// 首字符是字母
				if (isLetter(convert)) {
					// if(!preNokeys.contains(convert))
					// preNokeys.add(convert);
					List<String> temp = preNoMaps.get(convert + "1");
					if (PUtils.isEmpty(temp)) {
						temp = new ArrayList<>();
					}
					// 带“-”
					if (s.contains("-")) {
						String[] split = StrUtil.split(s, "-");
						String letter = getStrFromString(split[0]);
						Integer start = getNumericFromString(split[0]);
						Integer end = getNumericFromString(split[1]);

						for (int i = start; i < end + 1; i++) {
							String v = letter + i;
							result.add(v);
							if (!preNoMaps.containsValue(v)) {
								temp.add(v);
								preNoMaps.put(convert + "1", temp);
							}
						}
					}
					// 不带“-”
					if (!s.contains("-")) {
						result.add(s);
						if (!preNoMaps.containsValue(s)) {
							temp.add(s);
							preNoMaps.put(convert + "1", temp);
						}
					}
				}
				// 首字符不是字母
				if (!isLetter(convert)) {
					convert = Convert.convert(String.class, s.charAt(s.length() - 1));
					List<String> temp = preNoMaps.get(convert + "2");
					if (PUtils.isEmpty(temp)) {
						temp = new ArrayList<>();
					}
					// 带“-”
					if (s.contains("-")) {
						String[] split = StrUtil.split(s, "-");
						String letter = getStrFromString(split[0]);
						Integer start = getNumericFromString(split[0]);
						Integer end = getNumericFromString(split[1]);
						for (int i = start; i < end + 1; i++) {
							String v = i + letter;
							result.add(v);
							if (!preNoMaps.containsValue(v)) {
								temp.add(v);
								preNoMaps.put(convert + "2", temp);
							}
						}
					}
					// 不带“-”
					if (!s.contains("-")) {
						result.add(s);
						if (!preNoMaps.containsValue(s)) {
							temp.add(s);
							preNoMaps.put(convert + "2", temp);
						}
					}
				}
			}

			// 不包含字母
			if (!isContainsLetter(s)) {
				List<String> temp = preNoMaps.get("9");
				if (PUtils.isEmpty(temp)) {
					temp = new ArrayList<>();
				}
				// 带“-”
				if (s.contains("-")) {
					String[] split = StrUtil.split(s, "-");
					Integer start = getNumericFromString(split[0]);
					Integer end = getNumericFromString(split[1]);
					for (int i = start; i < end + 1; i++) {
						String v = Convert.convert(String.class, i);
						result.add(v);
						if (!preNoMaps.containsValue(v)) {
							temp.add(v);
							preNoMaps.put("9", temp);
						}
					}
				}
				// 不带“-”
				if (!s.contains("-")) {
					result.add(s);
					if (!preNoMaps.containsValue(s)) {
						temp.add(s);
						preNoMaps.put("9", temp);
					}
				}
			}
		}
		// 去重
		List<String> distinct = CollUtil.distinct(result);
		StringBuilder rBuilder = new StringBuilder();
		for (Map.Entry<String, List<String>> entry : preNoMaps.entrySet()) {
			rBuilder.append(mergePitsNoOrCaton(entry.getValue()));
			rBuilder.append(",");
		}
		rBuilder.deleteCharAt(rBuilder.length() - 1);
		return rBuilder.toString();
	}

	/**
	 * 将时间戳转化成YYYYMMdd格式
	 */
	public static long getDateFormat(long l) {
		String s = new SimpleDateFormat("YYYYMMdd").format(new Date(l));
		Long d = Long.parseLong(s);
		return d;
	}

	/**
	 * 获取字符串的第一个首字母
	 * 
	 * @param s
	 *
	 * @return
	 */
	public static String getFirstLetterStr(String s) {
		for (int i = 0; i < s.length(); i++) {
			char a = s.charAt(i);
			if ((a <= 'z' && a >= 'a') || (a <= 'Z' && a >= 'A')) {
				return String.valueOf(a);
			}
		}
		return null;
	}

	// 判断字符串是否是连续的数字 -- 6541233 false
	public static boolean isOrderNumeric(String numOrStr) {
		boolean flag = true;
		for (int i = 0; i < numOrStr.length(); i++) {
			if (i > 0) {// 判断如123456
				int num = Integer.parseInt(numOrStr.charAt(i) + "");
				int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") + 1;
				if (num != num_) {
					flag = false;
					break;
				}
			}
		}
		if (!flag) {
			for (int i = 0; i < numOrStr.length(); i++) {
				if (i > 0) {// 判断如654321
					int num = Integer.parseInt(numOrStr.charAt(i) + "");
					int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") - 1;
					if (num != num_) {
						flag = false;
						break;
					}
				}
			}
		}
		return flag;
	}

	// 获取连续的数字
	public static String getContinuousNumber(List<Integer> NoNum) {
		int state = 0;
		String result = "";
		for (int i = 0; i < NoNum.size(); i++) {
			if (i == NoNum.size() - 1) {
				state = 2;
			}
			if (state == 0) {
				if (NoNum.get(i + 1) == NoNum.get(i) + 1) {
					result += Integer.toString(NoNum.get(i));
					result += "-";
					state = 1;
				} else {
					result += Integer.toString(NoNum.get(i));
					result += ",";
				}
			} else if (state == 1) {
				if (NoNum.get(i + 1) != NoNum.get(i) + 1) {
					result += Integer.toString(NoNum.get(i));
					result += ",";
					state = 0;
				} else {
					result += NoNum.get(i) + "-";
				}
			} else {
				result += Integer.toString(NoNum.get(i));
			}
		}

		String[] str = result.split(",");
		/*
		 * for ( int stritem = 0 ; stritem < str.length ; stritem++ ) { String [] sp =
		 * str[stritem].split("-"); List<String> tt = Arrays.asList(sp); if (
		 * tt.contains( charct+"")) { result = str[stritem].replace("-",","); } }
		 */
		return result;
	}

	public static String poiTrim(String str) {
		return str.replaceAll("[\\s\\u00A0]+", "").trim();
	}

	/**
	 * 判断某一时间是否在一个区间内
	 *
	 * @param sourceTime 时间区间,半闭合,如[10:00-20:00)
	 * @param curTime    需要判断的时间 如10:00
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean isInTime(String sourceTime, String curTime) {
		if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}
		if (curTime == null || !curTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
		}
		String[] args = sourceTime.split("-");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			long now = sdf.parse(curTime).getTime();
			long start = sdf.parse(args[0]).getTime();
			long end = sdf.parse(args[1]).getTime();
			if (args[1].equals("00:00")) {
				args[1] = "24:00";
			}
			if (end < start) {
				if (now >= end && now < start) {
					return false;
				} else {
					return true;
				}
			} else {
				if (now >= start && now < end) {
					return true;
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}

	}

	/**
	 * 保留三位小数 返回字符串
	 * 
	 * @param v
	 * @return
	 */
	/**
	 * double转String 如果double无小数点 转String会带 .0
	 * 
	 * @param v
	 * @return
	 */
	public static String doubleToStringHold(Double v) {
		if (v == null) {
			return "";
		}
		if (v % 1 == 0) {
			return String.valueOf(v.intValue());
		} else {
			return String.valueOf(v);
		}
	}

	/**
	 * 比较两个list 主要用于基础数据类型
	 * 
	 * @param <T>
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static <T> boolean listEqual(List<T> list1, List<T> list2) {
		if (list1 == list2)
			return true;

		if (list1.size() != list2.size())
			return false;

		int n = list1.size();
		int i = 0;
		while (n-- != 0) {
			if (!list1.get(i).equals(list2.get(i)))
				return false;
			i++;
		}
		return true;

	}

	public static int hexToDecimal(String hex) {
		int outcome = 0;
		for (int i = 0; i < hex.length(); i++) {
			char hexChar = hex.charAt(i);
			outcome = outcome * 16 + charToDecimal(hexChar);
		}
		return outcome;
	}

	public static int charToDecimal(char c) {
		if (c >= 'A' && c <= 'F')
			return 10 + c - 'A';
		else
			return c - '0';
	}
	
	
 
	/**
	 * 进一法
	 * @param src 被除数
	 * @param div 除数
	 * @return
	 */
	public static int oneWayForward(int src, int div)
	{
		 if(src%div==0)
			 return src/div;
		 else
         return	src/div+1;
	  
	}

	public static void main(String[] args) {
		String tts="2020-11-19 23:31:56";
		long t=StringToTimeDate(tts, "yyyy-MM-dd HH:mm:ss").getTime();
		// t=1605715916000L;//2020-11-19 00:11:56
		 //t =1605717716000L;//2020-11-19 00:41:56
		// t=1605800516000L;//2020-11-19 23:41:56
		long tt1=getWholePointFromUser(t);
		System.out.println(TimeToString(tt1));
//		List<String> list = new ArrayList<>();
//		// 1357920000000
//		List<String> lines = Arrays.asList("s1", "s2", "s3", "s4", "s9", "s20");
//		String line = "2020.04.15";
//
//		System.out.println(mergePitsNoOrCaton(lines));
//		System.out.println(StringToTimeDate(line, "yyyy.MM.dd").getTime());
//		System.out.println(formatDateTime(line));
//		long tt = getWholePointDate();
//		System.out.println(tt);
//		System.out.println(TimeToString(tt));
//
//		System.out.println(getStrFromString("aaa b55"));
//
//
//
//		String[] lines1 ={ "4300-CF102D-T5","0801-UM4AAA-10","0801-UM4AAA-10"};
//		Set<String> set=new LinkedHashSet<>();
//		for(String s:lines1){
//			set.add(s);
//		}
//		List<String> list1=new ArrayList<>(set);
//		System.out.println(String.join(",",list1));
//
//		System.out.println(tt);
//		String s = "cbaouhbfi";
//		int index = s.indexOf(":");
//		System.out.println(index);
//
//		System.out.println(getMidNumbericFromString1("YY6"));
//
//		System.out.println(doubleToStringHold(1.0));
//
//		System.out.println(Integer.MAX_VALUE / 2);
//
//		System.out.println(isContainChinese("屏货柜/ Panel container"));
//
//		System.out.println(toHexString(15, 2));
//
//		System.out.println(hexToDecimal("02"));
//		System.out.println((Integer.valueOf("0") & 1) == 1 ? "0" : "1");
//		System.out.println(getDaysBetweenTime(1598889600000l, 1600099200000l));
//
//		System.out.println(BeanUtils.getCTSDataLong("Sun Jul 05 00:00:00 CST 2020"));
//
//		System.out.println(formatInt(10, 7));
//
//		System.out.println(getCurrentDayFirstTime(1603335223000l));
//
//		List<String> instockedPitsNoList=PUtils.spliterPitsNoOrCatonPro("AA2,AA1");
//		String instockedPitsNoStr=PUtils.mergePitsNoOrCartonPro3(instockedPitsNoList);
//		System.out.println(instockedPitsNoStr);
//
//		double netw=0.0000000009d;
//		System.out.println(netw);
//
//		List<String> pitsList=spliterPitsNoOrCatonPro("401-40816");
		//System.out.println(getRoundNumber(0.00174,6));
	}
}
