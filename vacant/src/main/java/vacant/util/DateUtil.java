package vacant.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String YEAR_MONTH_DATE = "yyyy-MM-dd";

	public static String formateDate(Date date) {
		return new SimpleDateFormat(YEAR_MONTH_DATE).format(date);
	}
	
	public static String currentDate() {
		return formateDate(new Date());
	}
}
