package vacant.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vacant.domain.VacantResource;
import vacant.util.EasyuiTreeNode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-hbt.xml" })
public class VacantResourceServiceTest {

	@Autowired
	private VacantResourceService resourceService;

	@Test
	public void testGetResourceListByUserId() {
		List<VacantResource> list = resourceService
				.getResourceListByUserId("fd27a7bd-870d-11e3-a27f-08606e729ccc");
		assertEquals(1, list.size());
		VacantResource resource = list.get(0);
		assertEquals("cf4ac3ec-870c-11e3-a27f-08606e729ccc", resource.getId());
		assertEquals("/login", resource.getUrl());
		assertEquals("0", resource.getIsDisplay());
		assertNull(resource.getDisplayOrder());
		assertNull(resource.getDisplayName());
		assertNull(resource.getParentId());
	}

	@Test
	public void testGetUrlSetByUserId() {
		Set<String> urlSet = resourceService
				.getUrlSetByUserId("fd27a7bd-870d-11e3-a27f-08606e729ccc");
		assertTrue(urlSet.contains("/login"));
	}

	public void testGetTreeDataByUserId() {
		String treeData = resourceService
				.getTreeDataByUserId("fd27a7bd-870d-11e3-a27f-08606e729ccc");
		EasyuiTreeNode tree = new EasyuiTreeNode(
				"57c46197-87b6-11e3-b558-08606e729ccc", "模块1", "#",
				new EasyuiTreeNode[] {
						new EasyuiTreeNode(
								"5cfdfc40-87b6-11e3-b558-08606e729ccc", "页面1",
								"/model1/page1"),
						new EasyuiTreeNode(
								"77410bbd-87b6-11e3-b558-08606e729ccc", "页面2",
								"/model1/page2") });
		String expectedTreeData = JSONArray.fromObject(
				new EasyuiTreeNode[] { tree }).toString();
		assertEquals(expectedTreeData, treeData);
	}
}
