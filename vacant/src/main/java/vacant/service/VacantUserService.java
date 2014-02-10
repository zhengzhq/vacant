package vacant.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacant.constant.YesOrNo;
import vacant.domain.VacantUser;
import vacant.util.Page;

@Service
public class VacantUserService {

	@Autowired
	private SessionFactory factory;
	@Autowired
	private DictionaryService dictionaryService;

	public VacantUser findUserByLoginName(String loginName) {
		VacantUser user = null;
		String hql = "from VacantUser where loginName=:loginName";
		Session session = factory.getCurrentSession();
		List<VacantUser> list = session.createQuery(hql)
				.setParameter("loginName", loginName).setMaxResults(1).list();
		if (!list.isEmpty()) {
			user = list.get(0);
		}
		return user;
	}

	public Page<VacantUser> getPage(String loginName, String name, int page,
			int rows) throws NoSuchFieldException {
		Page<VacantUser> result = new Page<VacantUser>();
		int offset = (page - 1) * rows;
		String sql = "from VacantUser left join fetch VacantDepartment ";
		sql += "left join fetch VacantRole ";
		sql += "where isWrittenOff=:isWrittenOff ";
		if (StringUtils.isNotBlank(loginName)) {
			sql += "and loginName like :loginName ";
		}
		if (StringUtils.isNotBlank(name)) {
			sql += "and name like :name ";
		}
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(sql).setParameter("isWrittenOff",
				YesOrNo.NO);
		if (StringUtils.isNotBlank(loginName)) {
			query.setParameter("loginName", "%" + loginName + "%");
		}
		if (StringUtils.isNotBlank(name)) {
			query.setParameter("name", "%" + name + "%");
		}
		List<VacantUser> list = query.setMaxResults(rows + 1)
				.setFirstResult(offset).list();
		result.setRows(list);
		result.setTotal(offset + list.size());
		if (list.size() > rows) {
			list.remove(list.size() - 1);
		}
		for (VacantUser user : list) {
			dictionaryService.decodeBean(user);
		}
		return result;
	}

	public void save(VacantUser user) {
		if ("".equals(user.getId())) {
			user.setId(null);
		}
		user.setIsWrittenOff(YesOrNo.NO);
		factory.getCurrentSession().saveOrUpdate(user);
	}

}
