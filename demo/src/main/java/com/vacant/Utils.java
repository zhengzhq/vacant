package com.vacant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vacant.user.User;

public class Utils {

	public static String dateTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(currentTime);
	}

	public static String date() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(currentTime);
	}
	
	public static void log(Logger logger, String sql, Object[] params) {
		String msg = sql;
		msg += "(";
		for(int i=0; i< params.length; i++) {
			msg += ",";
			msg += params[i];
		}
		msg += ")";
		logger.debug(msg);
	}
	
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	public static String parentCode(String code) {
		if (code.length() == 12) {
			return code.substring(0, 9);
		} else if (code.length() == 9) {
			return code.substring(0, 6);
		} else if (code.length() == 6) {
			return code.substring(0, 4);
		} else if (code.length() == 4) {
			return code.substring(0, 2);
		} else {
			throw new RuntimeException("行政区划代码长度错误");
		}
	}
	public static String subCode(String code) {
		if (code.length() == 12) {
			return code.substring(9);
		} else if (code.length() == 9) {
			return code.substring(6);
		} else if (code.length() == 6) {
			return code.substring(4);
		} else if (code.length() == 4) {
			return code.substring(2);
		} else {
			throw new RuntimeException("行政区划代码长度错误");
		}
	}
	
	public static User user() {
		return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static String userId() {
		return user().getId();
	}
}
