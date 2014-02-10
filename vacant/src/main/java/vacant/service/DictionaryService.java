package vacant.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import vacant.constant.YesOrNo;
import vacant.domain.VacantDictionary;
import vacant.domain.VacantDictionaryItem;
import vacant.util.DictCode;

@Lazy(true)
@Service
public class DictionaryService {

	@Autowired
	private SessionFactory factory;

	private Map<String, Map<String, String>> dicts = new HashMap<String, Map<String, String>>();

	public DictionaryService() {
//		String hql = "from VacantDictionary join fetch VacantDictionaryItem ";
//		List<VacantDictionary> list = factory.getCurrentSession()
//				.createQuery(hql).list();
//		for (VacantDictionary dict : list) {
//			List<VacantDictionaryItem> items = dict.getItems();
//			Map<String, String> itemMap = new HashMap<String, String>();
//			for (VacantDictionaryItem item : items) {
//				itemMap.put(item.getCode(), item.getValue());
//			}
//			dicts.put(dict.getType(), itemMap);
//		}
	}

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
				String dictValue = decode(dictType, dictCode);
				clazz.getField(valueFieldName).set(obj, dictValue);
			} catch (IllegalAccessException e) {
				// can't happen
			}

		}

	}

	public String decode(String dictType, String dictCode) {
		return dicts.get(dictType).get(dictCode);
	}

}
