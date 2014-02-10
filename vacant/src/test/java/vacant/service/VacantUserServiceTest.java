package vacant.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vacant.domain.VacantUser;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-hbt.xml" })
public class VacantUserServiceTest {
	
	@Autowired
	private VacantUserService userService;
	
	@Test
	public void testFindUserByLoginName() {
		VacantUser user = userService.findUserByLoginName("scott");
		assertNotNull(user);
	}

}
