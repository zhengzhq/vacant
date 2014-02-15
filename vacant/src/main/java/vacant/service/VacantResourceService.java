package vacant.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacant.constant.YesOrNo;
import vacant.domain.VacantResource;
import vacant.util.EasyuiTreeNode;

@Service
public class VacantResourceService {

	@Autowired
	private SessionFactory factory;

	public List<VacantResource> getAllResourceList() {
		return factory.getCurrentSession().createQuery("from VacantResource")
				.list();
	}

	public List<VacantResource> getResourceListByUserId(String userId) {
		String sql = "select r.* from vacant_resource r, ";
		sql += "vacant_resource_role rr, vacant_user u ";
		sql += "where r.id=rr.resource_id and rr.role_id=u.role_id ";
		sql += "and u.id=:user_id order by r.display_order ";
		Session session = factory.getCurrentSession();
		return session.createSQLQuery(sql).addEntity(VacantResource.class)
				.setParameter("user_id", userId).list();
	}

	public Set<String> getResourceUrlSet(List<VacantResource> resourceList) {
		Set<String> set = new HashSet<String>();
		for (VacantResource resource : resourceList) {
			set.add(resource.getUrl());
		}
		return set;
	}

	public String getTreeData(List<VacantResource> resourceList) {
		List<EasyuiTreeNode> rootNodeList = new ArrayList<EasyuiTreeNode>();

		for (VacantResource resource : resourceList) {
			if (resource.getParentId() == null
					&& resource.getIsDisplay().equals(YesOrNo.YES)) {
				String id = resource.getId();
				String text = resource.getName();
				String url = resource.getUrl();
				EasyuiTreeNode rootNode = new EasyuiTreeNode(id, text, url);
				attachChildren(rootNode, resourceList);
				rootNodeList.add(rootNode);
			}
		}
		return JSONArray.fromObject(rootNodeList).toString();
	}

	protected void attachChildren(EasyuiTreeNode node,
			List<VacantResource> resourceList) {
		List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
		String nodeId = node.getId();
		for (VacantResource resource : resourceList) {
			if (nodeId.equals(resource.getParentId())
					&& resource.getIsDisplay().equals(YesOrNo.YES)) {
				EasyuiTreeNode childNode = new EasyuiTreeNode(resource.getId(),
						resource.getName(), resource.getUrl());
				attachChildren(childNode, resourceList);
				children.add(childNode);
			}
		}
		node.setChildren(children.toArray(new EasyuiTreeNode[children.size()]));
	}

	public void save(VacantResource resource) {
		if ("".equals(resource.getId())) {
			resource.setId(null);
		}
		factory.getCurrentSession().saveOrUpdate(resource);
	}

	public void remove(String id) {
		String sql = "delete from vacant_resource where id=:id";
		factory.getCurrentSession().createSQLQuery(sql).setString("id", id)
				.executeUpdate();
		sql = "delete from vacant_resource_role where resource_id=:id";
		factory.getCurrentSession().createSQLQuery(sql).setString("id", id)
				.executeUpdate();
	}

}
