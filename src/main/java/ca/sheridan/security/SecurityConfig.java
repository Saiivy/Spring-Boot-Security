package ca.sheridan.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    private LoggingAccessDeniedHandler accessDeniedHandlder;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	 public void configure(HttpSecurity http) throws Exception{
		 http.authorizeRequests()
		 .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
		 .antMatchers("/admin/**").hasRole("ADMIN")
		 .antMatchers("addBookForm/*").hasRole("ADMIN")
		 .antMatchers("/","/**").permitAll()
		 .antMatchers("/h2-console/**").permitAll()
		 .and()
		 .formLogin().loginPage("/login")
		 .defaultSuccessUrl("/")
		 .and()
		 .logout().invalidateHttpSession(true)
		 .logoutSuccessUrl("/")
		 .clearAuthentication(true)
		 .and()
		 .exceptionHandling().accessDeniedHandler(accessDeniedHandlder);
		
		 //For the proper functioning of h2 database
		 http.csrf().disable();
		 http.headers().frameOptions().disable();
		 
	 }
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.jdbcAuthentication()
		 .dataSource(dataSource)
		 .withDefaultSchema()
		 .passwordEncoder(passwordEncoder).withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN");
		 
		 
		 
	 }
	 
	 @Bean
	 public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception{
		 JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		 jdbcUserDetailsManager.setDataSource(dataSource);
		 return jdbcUserDetailsManager;
		 
	 }
	 

}
