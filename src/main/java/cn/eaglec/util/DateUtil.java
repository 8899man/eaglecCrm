package cn.eaglec.util;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 时间工具类
 * 
 * @author l2211
 * 
 */
public class DateUtil {

	private static SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat dayDf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat monthDf = new SimpleDateFormat(
			"yyyy-MM");

	/**
	 * 获取日期 yyyy-MM-dd格式。
	 * 
	 * @param day
	 *            日期
	 * @return
	 */
	public static String getDayStringByDate(Date day) {
		String dateStr = dayDf.format(day);
		return dateStr;
	}

	public static String getYearStringByString(String day) {
		if (StringUtils.isEmpty(day) || day.length() <= "yyyy".length()) {
			return day;
		}
		return day.substring(0, "yyyy".length());
	}

	public static String getMonthStringByString(String day) {
		if (StringUtils.isEmpty(day) || day.length() <= "yyyy-MM".length()) {
			return day;
		}
		return day.substring(0, "yyyy-MM".length());
	}

	/**
	 * 获取日期 yyyy-MM-dd格式。
	 * 
	 * @param day
	 *            日期
	 * @return
	 */
	public static String getDayStringByString(String day) {
		if (StringUtils.isEmpty(day) || day.length() <= "yyyy-MM-dd".length()) {
			return day;
		}
		return day.substring(0, "yyyy-MM-dd".length());
	}

	public static Date stringToDate2(String dateStr) {
		Date date = null;

		try

		{
			dateStr = URLDecoder.decode(dateStr, "UTF-8");

			if (isDate(dateStr)) {
				SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				date = df1.parse(dateStr);
			} else {
				if (isValidDate(dateStr, "yyyy-MM-dd HH:mm:ss")) {
					date = df.parse(dateStr);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return date;
	}

	public static Date stringToDate(String dateStr) {

		Date date = null;
		if (StringUtils.isNotBlank(dateStr)) {
			try {
				dateStr = URLDecoder.decode(dateStr, "UTF-8");
				date = df.parse(dateStr);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		return date;

	}

	public static String dateToString(Date date) {

		String dateStr = df.format(date);

		return dateStr;
	}

	public static String dateToString(Date date, String formatter) {
		if (null == date) {
			return "";
		}

		SimpleDateFormat df = new SimpleDateFormat(formatter);
		return df.format(date);
	}

	public static boolean isDate(String date) {
		/**
		 * 判断日期格式和范围
		 */
		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

		Pattern pat = Pattern.compile(rexp);

		Matcher mat = pat.matcher(date);

		boolean dateType = mat.matches();

		return dateType;
	}

	public static boolean isValidDate(String str, String format) {
		boolean convertSuccess = true;
		SimpleDateFormat format2 = new SimpleDateFormat(format);
		try {
			format2.setLenient(false);
			format2.parse(str);
		} catch (Exception e) {

			convertSuccess = false;
		}
		return convertSuccess;
	}

	public static String longToString(long currentTime) {
		String strTime = null;
		try {
			Date date = new Date(currentTime); // long类型转成Date类型
			strTime = dateToString(date); // date类型转成String
		} catch (Exception e) {

			e.printStackTrace();
		}
		return strTime;
	}

	public static Date longToDate(long currentTime) {
		Date date = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		return date;
	}

	/**
	 * 获取time 秒之后的时间，time为负数表示 获取time 秒之前的时间
	 * 
	 * @param time
	 * @return
	 */
	public static Date getTimeAfterSecond(Date date, long time) {
		if (time > Integer.MAX_VALUE || time < Integer.MIN_VALUE) {
			throw new IllegalArgumentException("时间超出范围");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, (int) time);
		return calendar.getTime();
	}

	/**
	 * 判断是否是当天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isCurrentDay(String date) {
		String currentDayStr = DateUtil.getDayStringByDate(new Date());
		if (StringUtils.isEmpty(date)) {
			return false;
		}
		Date beginData = DateUtil.stringToDate2(date);
		Date currDate = DateUtil.stringToDate2(currentDayStr);
		if (beginData == null || currDate == null) {
			return false;
		}
		return beginData.getTime() >= currDate.getTime();
	}

	/**
	 * 获取当月的最后一天时间
	 * @param date
	 * @return
	 */
	public static Date getMonthLastTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		// 得到一个月最后一天日期(31/30/29/28)
		int MaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 按你的要求设置时间
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay, 23, 59, 59);

		return c.getTime();
	}
	
	public static String getMonth(Date date) {
		if(date == null) {
			return monthDf.format(new Date());
		}
		return monthDf.format(date);
	}
}
