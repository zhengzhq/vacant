package vacant;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@SpringBootApplication
public class DemoApplication {
	
	// 配置jta
	@Bean(name = "dataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix = "spring.jta.datasource")
	public DataSource dataSource() {
		return new AtomikosDataSourceBean();
	}

	@Primary
	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource)throws Exception {
		JdbcTemplate bean = new JdbcTemplate();
		bean.setDataSource(dataSource);
		return bean;
	}
	@Bean(name = "dataSource2", destroyMethod = "close")
	@ConfigurationProperties(prefix = "spring.jta.datasource2")
	public DataSource dataSource2() {
		return new AtomikosDataSourceBean();
	}

	@Bean(name = "jdbcTemplate2")
	public JdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource2)throws Exception {
		JdbcTemplate bean = new JdbcTemplate();
		bean.setDataSource(dataSource2);
		return bean;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
