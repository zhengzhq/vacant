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
import vacant.util.DateUtil;
import vacant.util.Page;

@Service
public class VacantRoleService {

	@Autowired
	private SessionFactory factory;
	@Autowired
	private VacantDictionaryService dictionaryService;

	public Page<VacantUser> getPage(String loginName, String name, int page,
			int rows) throws NoSuchFieldException {
		Page<VacantUser> result = new Page<VacantUser>();
		int offset = (page - 1) * rows;
		String sql = "from VacantRole ";
		sql += "where isWrittenOff=:isWrittenOff ";
		if (StringUtils.isNotBlank(name)) {
			sql += "and name like :name ";
		}
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(sql).setParameter("isWrittenOff",
				YesOrNo.NO);
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
		dictionaryService.decodeBeans(list);
		return result;
	}

	public void save(VacantUser user) {
		if ("".equals(user.getId())) {
			user.setId(null);
		}
		user.setIsWrittenOff(YesOrNo.NO);
		factory.getCurrentSession().saveOrUpdate(user);
	}

	public void remove(String id, String writtenOffReason) {
		String hql = "update VacantUser set writtenOffReason=:reason, ";
		hql += "writtenOffDate=:date,";
		hql += "isWrittenOff=:isWrittenOff ";
		hql += "where id=:id";
		factory.getCurrentSession().createQuery(hql)
				.setString("reason", writtenOffReason)
				.setString("date", DateUtil.currentDate())
				.setString("isWrittenOff", YesOrNo.YES).setString("id", id)
				.executeUpdate();
	}

}