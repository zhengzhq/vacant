package vacant.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacant.domain.VacantRole;
import vacant.util.Page;

@Service
public class VacantRoleService {

	@Autowired
	private SessionFactory factory;
	@Autowired
	private VacantDictionaryService dictionaryService;

	public Page<VacantRole> getPage(String loginName, String name, int page,
			int rows) throws NoSuchFieldException {
		Page<VacantRole> result = new Page<VacantRole>();
		int offset = (page - 1) * rows;
		String sql = "from VacantRole ";
		sql += "where 1=1 ";
		if (StringUtils.isNotBlank(name)) {
			sql += "and name like :name ";
		}
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(sql);
		if (StringUtils.isNotBlank(name)) {
			query.setParameter("name", "%" + name + "%");
		}
		List<VacantRole> list = query.setMaxResults(rows + 1)
				.setFirstResult(offset).list();
		result.setRows(list);
		result.setTotal(offset + list.size());
		if (list.size() > rows) {
			list.remove(list.size() - 1);
		}
		dictionaryService.decodeBeans(list);
		return result;
	}

	public void save(VacantRole role, String resourceIds) {
		if ("".equals(role.getId())) {
			role.setId(null);
		}
		factory.getCurrentSession().saveOrUpdate(role);

		String sql = "delete from vacant_resource_role where role_id=:role_id ";
		factory.getCurrentSession().createSQLQuery(sql)
				.setString("role_id", role.getId()).executeUpdate();

		sql = "insert into vacant_resource_role (id,resource_id,role_id) ";
		sql += "values(uuid(), :resource_id, :role_id)";
		String[] resourceIdArray = resourceIds.split(",");
		for (String resourceId : resourceIdArray) {
			factory.getCurrentSession().createSQLQuery(sql)
					.setString("resource_id", resourceId)
					.setString("role_id", role.getId()).executeUpdate();
		}
	}

	public void remove(String id) {
		String sql = "delete from vacant_role where id=:id ";
		factory.getCurrentSession().createSQLQuery(sql).setString("id", id)
				.executeUpdate();

		sql = "delete from vacant_resource_role where role_id=:role_id";
		factory.getCurrentSession().createSQLQuery(sql)
				.setString("role_id", id).executeUpdate();

		sql = "update vacant_user set role_id=null where role_id=:role_id";
		factory.getCurrentSession().createSQLQuery(sql)
				.setString("role_id", id).executeUpdate();
	}

	public List<VacantRole> getAllRoleList() {
		return factory.getCurrentSession().createQuery("from VacantRole")
				.list();
	}

}
