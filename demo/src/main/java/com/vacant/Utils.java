package com.vacant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static String dateTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(currentTime);
	}
}
