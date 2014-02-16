package vacant.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
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
	@Autowired
	private DictionaryService dictionaryService;

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
			if ((resource.getParentId() == null || resource.getParentId()
					.equals("")) && resource.getIsDisplay().equals(YesOrNo.YES)) {
				String id = resource.getId();
				String text = resource.getName();
				String url = resource.getUrl();
				EasyuiTreeNode rootNode = new EasyuiTreeNode(id, text, url);
				attachChildrenNode(rootNode, resourceList);
				rootNodeList.add(rootNode);
			}
		}
		return JSONArray.fromObject(rootNodeList).toString();
	}

	protected void attachChildrenNode(EasyuiTreeNode node,
			List<VacantResource> resourceList) {
		List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
		String nodeId = node.getId();
		for (VacantResource resource : resourceList) {
			if (nodeId.equals(resource.getParentId())
					&& resource.getIsDisplay().equals(YesOrNo.YES)) {
				EasyuiTreeNode childNode = new EasyuiTreeNode(resource.getId(),
						resource.getName(), resource.getUrl());
				attachChildrenNode(childNode, resourceList);
				children.add(childNode);
			}
		}
		node.setChildren(children.toArray(new EasyuiTreeNode[children.size()]));
	}

	public String getTreeDataByRole() {
		// TODO
		return null;
	}

	public List<VacantResource> getTopResourceListWithChildren() {
		String hql = "from VacantResource where isTop=:isTop order by displayOrder ";
		List<VacantResource> topResourceList = factory.getCurrentSession()
				.createQuery(hql).setString("isTop", YesOrNo.YES).list();
		dictionaryService.decodeBeans(topResourceList);
		for (VacantResource resource : topResourceList) {
			attachChildrenResource(resource);
		}
		return topResourceList;
	}

	private void attachChildrenResource(VacantResource parent) {
		String hql = "from VacantResource where parentId=:parentId order by displayOrder ";
		List<VacantResource> children = factory.getCurrentSession()
				.createQuery(hql).setString("parentId", parent.getId()).list();
		if (!children.isEmpty()) {
			dictionaryService.decodeBeans(children);
			parent.setChildren(children);
			for (VacantResource child : children) {
				attachChildrenResource(child);
			}
		}
	}

	public void save(VacantResource resource) {
		if ("".equals(resource.getId())) {
			resource.setId(null);
		}
		if (StringUtils.isBlank(resource.getParentId())) {
			resource.setIsTop(YesOrNo.YES);
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

	public void grantToAllRols(String id) {
		String sql = "delete from vacant_resource_role where resource_id=:resource_id ";

		factory.getCurrentSession().createSQLQuery(sql)
				.setString("resource_id", id).executeUpdate();

		sql = "insert into vacant_resource_role (id, resource_id, role_id) ";
		sql += "select uuid(), :resource_id, id FROM vacant_role ";
		sql += "WHERE is_written_off = :is_written_off";
		factory.getCurrentSession().createSQLQuery(sql)
				.setString("resource_id", id)
				.setString("is_written_off", YesOrNo.NO).executeUpdate();
	}

}
