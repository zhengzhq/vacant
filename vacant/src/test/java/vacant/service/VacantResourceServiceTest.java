package vacant.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vacant.domain.VacantResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-hbt.xml" })
public class VacantResourceServiceTest {

	@Autowired
	private VacantResourceService resourceService;

	@Test
	public void testGetResourceListByUserId() {
		List<VacantResource> list = resourceService
				.getResourceListByUserId("fd27a7bd-870d-11e3-a27f-08606e729ccc");
		assertEquals(4, list.size());
	}
}
