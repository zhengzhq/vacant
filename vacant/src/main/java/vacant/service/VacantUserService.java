package vacant.service;

import java.math.BigInteger;
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

	public Page<VacantUser> queryPage(String name, int page, int rows) {
		Page<VacantUser> result = new Page<VacantUser>();
		int offset = (page - 1) * rows;
		String sql = "select * from vacant_user ";
		sql += "where is_written_off=:is_written_off ";
		if (StringUtils.isNotBlank(name)) {
			sql += "and name like :name ";
		}
		String sqlCount = sql.replace("*", "count(*) total");
		sql += "limit :offset, :rows";
		Session session = factory.getCurrentSession();
		Query query = session.createSQLQuery(sql).addEntity(VacantUser.class)
				.setParameter("is_written_off", YesOrNo.NO);
		Query queryCount = session.createSQLQuery(sqlCount).addScalar("total")
				.setParameter("is_written_off", YesOrNo.NO);
		if (StringUtils.isNotBlank(name)) {
			query.setParameter("name", "%" + name + "%");
			queryCount.setParameter("name", "%" + name + "%");
		}
		BigInteger total = (BigInteger) queryCount.uniqueResult();
		result.setTotal(total.intValue());
		List<VacantUser> list = query.setParameter("offset", offset)
				.setParameter("rows", rows).list();
		result.setRows(list);
		return result;
	}

}
