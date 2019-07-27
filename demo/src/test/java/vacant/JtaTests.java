package vacant;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import vacant.admin.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JtaTests {

	@Autowired
	UserService userService;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	@Qualifier("jdbcTemplate2")
	JdbcTemplate jdbcTemplate2;

	@Test
	public void contextLoads() {
		assertNotNull(jdbcTemplate);
		assertNotNull(jdbcTemplate2);
		assertNotEquals(jdbcTemplate, jdbcTemplate2);
	}

}
