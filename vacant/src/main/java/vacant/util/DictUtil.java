package vacant.util;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;

import vacant.web.AppConfig;

public class DictUtil {

	@Autowired
	private AppConfig config;
	
	public void decodeBean(Object obj) throws NoSuchFieldException {
		Class<?> clazz = obj.getClass();
		// 取出对象的成员变量
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			// 获得成员变量的标注
			DictCode annotation = field.getAnnotation(DictCode.class);
			if (annotation == null) {
				continue;
			}
			try {
				// 重要:避免java虚拟机检查对私有成员的访问权限
				field.setAccessible(true);
				String dictCode = (String) field.get(obj);
				if (dictCode == null || dictCode.equals("")) {
					continue;
				}
				String valueFieldName = annotation.valueField();
				if (valueFieldName.equals("")) {
					valueFieldName = field.getName() + "Value";
				}
				String dictType = annotation.type();
				String dictValue = decodeField(dictType, dictCode);
				clazz.getField(valueFieldName).set(obj, dictValue);
			} catch (IllegalAccessException e) {
				// can't happen
			}

		}

	}

	public String decodeField(String dictType, String dictCode) {
			return config.getDicts().get(dictType).get(dictCode);
	}

}
