package MidtermAMohamed.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginAccessDeniedHandler accessDeniedHandler;
	
	
	
//Users with role (User) get access to all the resources and everything under secure as well.
protected void configure(HttpSecurity http) throws Exception {
http.authorizeRequests()
.antMatchers("/secure/**").hasRole("USER")

//everything under resources including the folders: js,cs,images...
.antMatchers("/", "/js/**", "/css/**", "/images/**", "/**").permitAll()
.anyRequest().authenticated()
.and().formLogin()

//Custom login form (/login)
.loginPage("/login").permitAll()

// When the user logs out
.and().logout()

//invalidate the session
.invalidateHttpSession(true)

//Clear credentials/Authentication info
.clearAuthentication(true)

.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
.logoutSuccessUrl("/login?logout").permitAll()


.and().exceptionHandling()
.accessDeniedHandler(accessDeniedHandler);
}
	

protected void configure(AuthenticationManagerBuilder auth) throws Exception {
auth.inMemoryAuthentication()
.passwordEncoder(NoOpPasswordEncoder.getInstance())
.withUser("frank@frank.com").password("1234").roles("USER")
.and()
.withUser("guest@guest.com").password("password").roles("GUEST");
}































	
	
	
}
