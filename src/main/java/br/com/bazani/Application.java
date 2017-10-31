package br.com.bazani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                    http
                    	.authorizeRequests()
                        .anyRequest().fullyAuthenticated()
                        .and()
                        .httpBasic();
            }

            @Override      
            public void configure(AuthenticationManagerBuilder auth) throws Exception {
            	auth
                    .ldapAuthentication()
                        .userSearchFilter("(sAMAccountName={0})")
                        .userSearchBase("ou=Users")
                    .contextSource()
                        .url("ldap://ldap-server-address:3268/dc=bazani,dc=com")
                        .managerDn("cn=Username,ou=Users,dc=bazani,dc=com")
                        .managerPassword("password");
            }
    }
}
