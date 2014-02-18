package vacant.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacant.constant.YesOrNo;
import vacant.domain.VacantOrgan;
import vacant.util.EasyuiTreeNode;

// 组织机构
@Service
public class VacantOrganService {

	@Autowired
	private SessionFactory factory;

	// 组织机构树
	public List<EasyuiTreeNode> tree() {
		List<EasyuiTreeNode> nodeList = new ArrayList<EasyuiTreeNode>();
		String hql = "from VacantOrgan where isTop=:isTop ";
		List<VacantOrgan> organList = factory.getCurrentSession()
				.createQuery(hql).setString("isTop", YesOrNo.YES).list();
		for (VacantOrgan organ : organList) {
			EasyuiTreeNode node = new EasyuiTreeNode(organ.getId(),
					organ.getName(), getTreeNodes(organ));
			nodeList.add(node);
		}
		return nodeList;
	}

	private EasyuiTreeNode[] getTreeNodes(VacantOrgan parent) {
		String hql = "from VacantOrgan where parentId=:parentId";
		List<VacantOrgan> organList = factory.getCurrentSession()
				.createQuery(hql).setString("parentId", parent.getId()).list();
		EasyuiTreeNode[] childrenNodes = new EasyuiTreeNode[organList.size()];
		int i = 0;
		for (VacantOrgan child : organList) {
			EasyuiTreeNode node = new EasyuiTreeNode(child.getId(),
					child.getName(), getTreeNodes(child));
			childrenNodes[i] = node;
			i++;
		}
		return childrenNodes;
	}
}
