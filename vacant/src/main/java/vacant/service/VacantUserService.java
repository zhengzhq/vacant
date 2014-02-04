package vacant.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacant.domain.VacantUser;

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

}
