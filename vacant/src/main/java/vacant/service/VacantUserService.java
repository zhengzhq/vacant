package vacant.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacant.constant.Global;
import vacant.constant.YesOrNo;
import vacant.domain.VacantUser;
import vacant.util.DateUtil;
import vacant.util.Page;

@Service
public class VacantUserService {

	@Autowired
	private SessionFactory factory;
	@Autowired
	private VacantDictionaryService dictionaryService;

	private Map<String, List<String>> userMap = new HashMap<String, List<String>>();

	public void loadUserMap() {
		String hql = "from VacantUser where isWrittenOff=:isWrittenOff ";
		List<VacantUser> userList = factory.getCurrentSession()
				.createSQLQuery(hql).setString("isWrittenOff", YesOrNo.NO)
				.list();
		String sql = "select url from vacant_resource r, vacant_resource_role rr ";
		sql += "where r.id=rr.resource_id and rr.role_id=:role_id ";
		sql += "and r.is_page=:is_page ";
		for (VacantUser user : userList) {
			List<String> urlList = factory.getCurrentSession()
					.createSQLQuery(sql).setString("role_id", user.getRoleId())
					.list();
			userMap.put(user.getId(), urlList);
		}
	}

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
		String sql = "from VacantUser u left join fetch u.organ o ";
		sql += "left join fetch u.department d ";
		sql += "left join fetch u.role ";
		sql += "where u.isWrittenOff=:isWrittenOff ";
		if (StringUtils.isNotBlank(loginName)) {
			sql += "and u.loginName like :loginName ";
		}
		if (StringUtils.isNotBlank(name)) {
			sql += "and u.name like :name ";
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
		if("".equals(user.getDepartmentId())) {
			user.setDepartmentId(null);
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

	public boolean isUserCanAccessResource(String userId, String requestUrl) {
		if (Global.IS_USE_CACHE.equals(YesOrNo.YES)) {
			List<String> urlList = userMap.get(userId);
			for (String url : urlList) {
				if (url.startsWith(requestUrl)) {
					return true;
				}
			}
			return false;
		} else {
			String sql = "SELECT 1 FROM vacant_user u, vacant_resource_role rr,vacant_resource r ";
			sql += "WHERE u.role_id = rr.role_id AND rr.resource_id = r.id ";
			sql += "AND u.id=:userId AND LOCATE(r.url, :url)=1 and r.is_page=:is_page";

			List<Integer> list = factory.getCurrentSession()
					.createSQLQuery(sql).setString("userId", userId)
					.setString("url", requestUrl)
					.setString("is_page", YesOrNo.YES).setMaxResults(1).list();
			return !list.isEmpty();
		}
	}

}
