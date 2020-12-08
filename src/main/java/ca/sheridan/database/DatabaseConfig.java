package ca.sheridan.database;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
/**
 * 
 * @author gursimar
 * Configuration class for database that stores objects of books and reviews
 *
 */
@Configuration
public class DatabaseConfig {
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTeamplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
		
	}
}
