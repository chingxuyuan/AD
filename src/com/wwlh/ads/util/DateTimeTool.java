
package com.wwlh.ads.util;

import java.util.Locale;

import android.text.format.Time;

public class DateTimeTool {

	public static Time now () {

		Time t = new Time();
		t.setToNow();
		return t;
	}

	public static Time timeForHour (int hour) {

		Time t = new Time();
		t.hour = hour;
		return t;
	}

	public static Time parse (String s) {

		if (s == null) {
			return null;
		}

		Time t = new Time();

		try {
			if (t.parse(s)) {
				return t;
			}
		} catch (Exception e) {

		}

		String[] ss = s.split(" |-|/|:");

		if (ss.length != 6) {
			return null;
		}

		int year = Integer.valueOf(ss[0]);
		int month = Integer.valueOf(ss[1]);
		int day = Integer.valueOf(ss[2]);
		int hour = Integer.valueOf(ss[3]);
		int minute = Integer.valueOf(ss[4]);
		int second = Integer.valueOf(ss[5]);

		t.set(second, minute, hour, day, month, year);

		return t;
	}

	public static String timeToString (Time t) {

		if (t == null) {
			return null;
		} else {
			String s = String.format(Locale.CHINA, "%04d/%02d/%02d %02d:%02d:%02d", t.year, t.month, t.monthDay, t.hour, t.minute, t.second);
			return s;
		}
	}

	public static boolean isSameDay (Time a, Time b) {

		if (a == null || b == null) {
			return false;
		}

		return a.year == b.year && a.month == b.month && a.monthDay == b.monthDay;
	}

	public static boolean isToDay (Time t) {

		return isSameDay(t, now());
	}

	public static boolean isInTimeRangeOfDay (Time a, Time b, Time v) {

		if (a == null || b == null || v == null) {
			return false;
		}

		long s = a.hour * 3600 + a.minute * 60 + a.second;
		long e = b.hour * 3600 + b.minute * 60 + b.second;
		long x = v.hour * 3066 + b.minute * 60 + v.second;

		return s <= x && x <= e;
	}

	public static long distanceLastShowTime (Time a, Time b) {

		if (a == null || b == null) {
			return -1;
		}

		long x = b.toMillis(true) - a.toMillis(true);

		if (x < 0) {
			return -1;
		} else {
			return x / 60000;
		}
	}
}
