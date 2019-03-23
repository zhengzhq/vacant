package vacant.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacant.constant.Global;
import vacant.constant.YesOrNo;
import vacant.domain.VacantDictionary;
import vacant.domain.VacantDictionaryItem;
import vacant.util.DictCode;

@Service
public class VacantDictionaryService {

	@Autowired
	private SessionFactory factory;

	private Map<String, Map<String, VacantDictionaryItem>> dicts = new HashMap<String, Map<String, VacantDictionaryItem>>();

	public void loadDicts() {
		String hql = "from VacantDictionary d join fetch d.items ";
		List<VacantDictionary> list = factory.getCurrentSession()
				.createQuery(hql).list();
		for (VacantDictionary dict : list) {
			List<VacantDictionaryItem> items = dict.getItems();
			Map<String, VacantDictionaryItem> itemMap = new HashMap<String, VacantDictionaryItem>();
			for (VacantDictionaryItem item : items) {
				itemMap.put(item.getCode(), item);
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
					// 閲嶈:閬垮厤java铏氭嫙鏈烘鏌ュ绉佹湁鎴愬憳鐨勮闂潈闄�
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
		if (Global.IS_USE_CACHE.equals(YesOrNo.YES)) {
			return dicts.get(dictType).get(dictCode).getValue();
		} else {
			String sql = "select di.value from vacant_dictionary d, vacant_dictionary_item di ";
			sql += "where d.id=di.dictionary_id and d.type=:dictType and di.code=:dictCode";
			List<String> list = factory.getCurrentSession().createSQLQuery(sql)
					.setString("dictType", dictType)
					.setString("dictCode", dictCode).setMaxResults(1).list();
			return list.get(0);
		}
	}

	public List<VacantDictionaryItem> getItemListByType(String dictType) {

		if (Global.IS_USE_CACHE.equals(YesOrNo.YES)) {
			List<VacantDictionaryItem> list = new ArrayList<VacantDictionaryItem>();
			Map<String, VacantDictionaryItem> itemMap = dicts.get(dictType);
			Set<String> dictCodeSet = itemMap.keySet();
			for (String dictCode : dictCodeSet) {
				list.add(itemMap.get(dictCode));
			}
			return list;
		} else {
			String sql = "select di.* from vacant_dictionary_item di, vacant_dictionary d ";
			sql += "where di.dictionary_id=d.id and d.type=:dict_type";
			return factory.getCurrentSession().createSQLQuery(sql)
					.addEntity(VacantDictionaryItem.class)
					.setString("dict_type", dictType).list();
		}
	}
}
