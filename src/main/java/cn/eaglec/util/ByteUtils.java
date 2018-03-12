package cn.eaglec.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ByteUtils {

	private static String hexStr = "0123456789ABCDEF";
	private final static byte[] hex = "0123456789ABCDEF".getBytes();
	private static String[] binaryArray = { "0000", "0001", "0010", "0011",
			"0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
			"1100", "1101", "1110", "1111" };

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	/**
	 * 字节数组到十六进制字符串转换
	 * 
	 * @param b
	 * @return
	 */
	public static String Bytes2HexString(byte[] b) {
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	/**
	 * 
	 * @param hexString
	 * @return 将十六进制转换为字节数组
	 */
	public static byte[] HexStringToBinary(String hexString) {
		// hexString的长度对2取整，作为bytes的长度
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		byte high = 0;// 字节高四位
		byte low = 0;// 字节低四位

		for (int i = 0; i < len; i++) {
			// 右移四位得到高位
			high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
			low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
			bytes[i] = (byte) (high | low);// 高地位做或运算
		}
		return bytes;
	}

	/**
	 * 十六进制字符串到字节数组转换
	 * 
	 * @param hexstr
	 *            十六进制字符串
	 * @return 字节数组
	 */
	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	/**
	 * 字节数组转换为二进制
	 * 
	 * @param bArray
	 *            字节数组
	 * @return 二进制字符串
	 */
	public static String bytes2BinaryStr(byte[] bArray) {

		String outStr = "";
		int pos = 0;
		for (byte b : bArray) {
			// 高四位
			pos = (b & 0xF0) >> 4;
			outStr += binaryArray[pos];
			// 低四位
			pos = b & 0x0F;
			outStr += binaryArray[pos];
		}
		return outStr;

	}

	/**
	 * 字符串转ascii码
	 * 
	 * @param value
	 * @return
	 */
	public static String stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append(",");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

	
	/**
	 * 10进制转BCD码
	 * @param asc
	 * @return
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
	
	/**
     * 
     * <pre>
     * 将4个byte数字组成的数组合并为一个float数.
     * </pre>
     * 
     * @param arr
     * @return
     */
    public static float byte4ToFloat(byte[] arr) {
        if (arr == null || arr.length != 4) {
            throw new IllegalArgumentException("byte数组必须不为空,并且是4位!");
        }
        int i = byte4ToInt(arr);
        return Float.intBitsToFloat(i);
    }
    
    /**
     * <pre>
     * 将长度为4的8位byte数组转换为32位int.
     * </pre>
     * 
     * @param arr
     * @return
     */
    public static int byte4ToInt(byte[] arr) {
        if (arr == null || arr.length != 4) {
            throw new IllegalArgumentException("byte数组必须不为空,并且是4位!");
        }
        return (int) (((arr[0] & 0xff) << 24) | ((arr[1] & 0xff) << 16) | ((arr[2] & 0xff) << 8) | ((arr[3] & 0xff)));
    }

	public static void main(String[] args) {
		byte[] bt = new byte[] { 10, 2, 12, 14, 1, 0, 0, 1, 0, 31, 45, 1, 8, 0,
				1, 0, -96, -45, 10, 3 };
		System.out.println(Bytes2HexString(bt));
		String data = "0001000000FA00FA00FA012C13880005";
		byte[] dataBytes = HexString2Bytes("0001000000FA00FA00FA012C13880005");
		System.out.println(Arrays.toString(dataBytes));// 0A020C0E01000001001F2D0108000100A0D30A03

		// 获取两个字节的数据
		int byteIndex = 5;
		int bitIndex = 5;
		int length = 2;
		byte[] bytes = new byte[length];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = dataBytes[byteIndex];
			byteIndex++;
		}
		String binaryStr = bytes2BinaryStr(bytes);
		System.out.println(binaryStr);
		System.out.println(Bytes2HexString(bytes));

		System.out.println("substring:"
				+ data.substring(byteIndex * 2, (byteIndex + length) * 2));

		System.out.println(Arrays.toString(bytes));
		System.out
				.println(bytes2BinaryStr(HexString2Bytes("0001000000FA00FA00FA012C13880005")));
		System.out.println(Long.parseLong("FFFFFFFF", 16));
		System.out.println(Long.parseLong("111", 2));

		System.out.println(stringToAscii("C8"));

		System.out.println();
		
		
		String time ="1416982301883";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = new Date(Long.parseLong(time));
			String result = df.format(date);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str ="11011001";
		String value =str.substring(8-(bitIndex+length), 8-bitIndex);
		System.out.println(value);
		
		
	}
	
	/**
     * ASCII码字符串转数字字符串
     * 
     * @param String
     *            ASCII字符串
     * @return 字符串
     */
    public static String asciiStringToString(String content) {
        String result = "";
        int length = content.length() / 2;
        for (int i = 0; i < length; i++) {
            String c = content.substring(i * 2, i * 2 + 2);
            int a = hexStringToAlgorism(c);
            char b = (char) a;
            String d = String.valueOf(b);
            result += d;
        }
        return result;
    }
    
    
    /**
     * 十六进制字符串装十进制
     * 
     * @param hex
     *            十六进制字符串
     * @return 十进制数值
     */
    public static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }

}
