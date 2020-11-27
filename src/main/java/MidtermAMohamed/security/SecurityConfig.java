package MidtermAMohamed.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import MidtermAMohamed.services.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginAccessDeniedHandler accessDeniedHandler;



	@Autowired
	private UserDetailsServiceImpl userDetailsService;



	//Users with role (User) get access to all the resources and everything under secure as well.
	protected void configure(HttpSecurity http) throws Exception {


		http.csrf().disable();
		http.headers().frameOptions().disable();

		http.authorizeRequests()


		.antMatchers(HttpMethod.POST, "/register").permitAll()


		.antMatchers("/secure/**").hasRole("USER")


		//everything under resources including the folders: js,cs,images...
		.antMatchers("/", "/js/**", "/css/**", "/images/**", "/**").permitAll()

		//


		//Specify db to use
		.antMatchers("/h2-console/**").permitAll()
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
	//End of sec method	



	//To handle encoded user name and password
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}//End of crypt method


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception{
		//////
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());

	}
}
