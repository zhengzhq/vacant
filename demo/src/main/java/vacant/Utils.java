package vacant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import vacant.admin.user.User;

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
		for (int i = 0; i < params.length; i++) {
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
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static String userId() {
		return user().getId();
	}
	
	public static String userName() {
		return user().getName();
	}

	public static String null2blank(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	public static String attachPath(String origName) {
//		String suffix = origName.substring(origName.lastIndexOf("."));
		String suffix = ".jpg";
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String month = formatter.format(currentTime);
		formatter = new SimpleDateFormat("yyyyMMdd");
		String day = formatter.format(currentTime);

		return String.format("%s/%s/%s%s", month, day, uuid(), suffix);
	}
	
	public static String fullPath(String relativePath) {
		return String.format("%s/%s", Cons.ATTACH_ROOT, relativePath);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(attachPath("a.jpg"));
	}
}
