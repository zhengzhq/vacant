package vacant;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import vacant.admin.user.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		String usersByUsernameQuery = "select username, password, enabled from vacant_user where username=?";
//		String authoritiesByUsernameQuery = "select a.username, b.role_id from vacant_user a, vacant_user_role b";
//		authoritiesByUsernameQuery += " where a.id = b.user_id and a.`username`=?";
//		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(usersByUsernameQuery)
//				.authoritiesByUsernameQuery(authoritiesByUsernameQuery).passwordEncoder(new BCryptPasswordEncoder());
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(userService);
//		authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//		auth.authenticationProvider(authenticationProvider);
		
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin(); // 允许使用iframe
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry reg = http.authorizeRequests()
				.antMatchers("/index", "/dwz_jui/**", "/").permitAll();
		// 对所有的路径进行配置，要求菜单项的url为该菜单项所指向的页面上相关功能的url的根
		// 例如，如果一个菜单的url为menu_url，则该菜单对应的页面中的操作的url应以menu_url开头
		// 这样就可以利用**来配置页面和页面上的相关操作了
		String sql = "select a.path, group_concat(b.role_id) roles from vacant_menu a, vacant_role_menu b";
		sql += " where a.id = b.`menu_id` group by a.id";
		List<Map<String, Object>> pathAndRolesList = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> pathAndRoles : pathAndRolesList) {
			String path = (String) pathAndRoles.get("path");
			String roles = (String) pathAndRoles.get("roles");
			reg.regexMatchers("^" + path + ".*").hasAnyAuthority(roles.split(",")); // 自定义的User类的Authority不包含ROLE_前缀，所以不能使用hasAnyRole
//			reg.regexMatchers("^" + path + ".*").hasAnyRole(roles.split(","));
		}
		// 其他请求要求登录就可以了
		reg.anyRequest().authenticated();
		// 如登录成功后默认显示的main页面
		reg.and().formLogin().loginPage("/login").defaultSuccessUrl("/main").permitAll().and().logout().permitAll();
	}

	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode("password");
		System.out.println(encode);
		System.out.println("s/abc/def".matches("^/abc.*"));
	}
}
