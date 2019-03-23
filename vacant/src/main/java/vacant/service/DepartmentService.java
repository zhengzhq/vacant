package vacant.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacant.domain.VacantDepartment;

@Service
public class VacantDepartmentService {

	@Autowired
	private SessionFactory factory;

	public List<VacantDepartment> byOrganId(String organId) {
		return factory.getCurrentSession()
				.createQuery("from VacantDepartment where organId=:organId")
				.setString("organId", organId).list();
	}
}
