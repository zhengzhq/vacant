package vacant.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacant.domain.VacantDictionary;
import vacant.domain.VacantDictionaryItem;
import vacant.util.DictCode;

@Service
public class DictionaryService {

	@Autowired
	private SessionFactory factory;

	private Map<String, Map<String, String>> dicts = new HashMap<String, Map<String, String>>();

	public void loadDicts() {
		String hql = "from VacantDictionary d join fetch d.items ";
		List<VacantDictionary> list = factory.getCurrentSession()
				.createQuery(hql).list();
		for (VacantDictionary dict : list) {
			List<VacantDictionaryItem> items = dict.getItems();
			Map<String, String> itemMap = new HashMap<String, String>();
			for (VacantDictionaryItem item : items) {
				itemMap.put(item.getCode(), item.getValue());
			}
			dicts.put(dict.getType(), itemMap);
		}
	}

	public void decodeBeans(Collection<?> collection) {
		for (Object object : collection) {
			decodeBean(object);
		}
	}

	public void decodeBean(Object obj) {
		Class<?> clazz = obj.getClass();
		while (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!Modifier.isPrivate(field.getModifiers())) {
					continue;
				}
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
					String dictValue = decode(dictType, dictCode);
					Field valueField = clazz.getDeclaredField(valueFieldName);
					valueField.setAccessible(true);
					valueField.set(obj, dictValue);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			clazz = clazz.getSuperclass();
		}
	}

	public String decode(String dictType, String dictCode) {
		return dicts.get(dictType).get(dictCode);
	}

}
