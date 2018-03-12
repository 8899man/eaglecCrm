package cn.eaglec.util;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	private static String phonePattern = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";

	// 格式化regcode
	public static String formateRegCode(String regCode) {
		if (StringUtils.isBlank(regCode)) {
			return null;
		}
		if (regCode.length() < 26) {
			return null;
		}
		if ("000000000".equals(regCode.substring(regCode.length() - 9,
				regCode.length()))) {
			return regCode.substring(9, 19);
		} else {
			return regCode.substring(9, 25);
		}
	}

	/**
	 * 判断字符串是否是手机格式
	 * 
	 * @param phoneStr
	 * @return
	 */
	public static boolean isPhone(String phoneStr) {
		Pattern p = Pattern.compile(phonePattern);
		Matcher m = p.matcher(phoneStr);
		return m.matches();
	}

	/**
	 * 
	 * 将List 转化成字符串 "," 拼接 例如:a,b,c
	 */
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (StringUtils.isNotEmpty(string)) {
				if (flag) {
					result.append(",");
				} else {
					flag = true;
				}

				result.append(string);
			}
		}
		return result.toString();
	}

	/**
	 * 
	 * 将List 转化成字符串 "," 例如: 'a','b','c'
	 */
	public static String listToStringIn(List<String> stringList) {
		if (stringList == null || stringList.size() < 1) {
			return "''";
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (StringUtils.isNotEmpty(string)) {
				if (flag) {
					result.append(",");
				} else {
					flag = true;
				}

				result.append("'").append(string).append("'");
			}
		}
		return result.toString();
	}

	/**
	 * 
	 * @Description 将String数组转化为字符串，例如：'a','b','c'
	 * @date 2015-10-10 上午11:09:07
	 * @param array
	 *            String数组
	 * @return
	 * @author zenggang
	 */
	public static String arrayToStringIn(String[] array) {
		if (null == array || array.length < 1) {
			return "''";
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : array) {
			if (StringUtils.isNotEmpty(string)) {
				if (flag) {
					result.append(",");
				} else {
					flag = true;
				}

				result.append("'").append(string).append("'");
			}
		}
		return result.toString();
	}

	/**
	 * 判断list 是否为空
	 * 
	 * @param lst
	 * @return
	 */
	public static boolean isNotListEmpty(List<?> lst) {
		if (null != lst) {
			if ((lst.size() > 0) && !lst.isEmpty())
				return true;
		}
		return false;

	}

	/**
	 * 
	 * @Description:判断一个字符串是否包含特殊字符
	 * @date 2015-8-18 下午2:17:54
	 * @param string
	 * @return true 提供的参数string不包含特殊字符
	 * @return false 提供的参数string包含特殊字符
	 * @author zenggang
	 */
	public static boolean isConSpeCharacters(String string) {
		// TODO Auto-generated method stub
		if (string.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "")
				.length() == 0) {
			// 如果不包含特殊字符
			return true;
		}
		return false;
	}

	public static String turnNotNull(String string) {
		if (string == null) {
			return "";
		}
		return string;
	}

	public static String GetMapValue(Map map, String key) {
		if (!map.containsKey(key)) {
			return "";
		}
		if (map.get(key) == null) {
			return "";
		}
		return map.get(key).toString();
	}

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	// 随机生成8位不重复数字
	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	/*
	 * 判断是否为整数
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	// public static void main(String[] args) {
	// System.out.println(generateShortUuid() );
	//
	// }

}
