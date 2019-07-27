package vacant;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import vacant.admin.user.User;
import vacant.admin.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	UserService userService;
	
	@Test
	public void contextLoads() {
		User user = userService.findByPk("ss");
		assertNull(user);
	}
	

}
