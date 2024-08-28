package jp.eightbit.exam.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception{
		httpSec.formLogin(login -> login
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.failureUrl("/login?error")
				.permitAll()
		).logout(logout -> logout
				.logoutSuccessUrl("/")
		).authorizeHttpRequests(auth -> auth
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers("/user/register/**").hasAuthority("ADMIN")
				.requestMatchers("/user/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/history/delete/**").hasAuthority("HIGH")
				.requestMatchers("/task/delete/**").hasAuthority("HIGH")
				.requestMatchers("/template/delete/**").hasAuthority("HIGH")
				.requestMatchers("/task/toggle/standby/**").hasAuthority("HIGH")
				.requestMatchers("/task/toggle/check/**").hasAuthority("HIGH")
				.anyRequest().authenticated()
		);
		//registerの認証追加する
		return httpSec.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	RoleHierarchy rolehierarchy() {
		String hierar = "ROOT > ADMIN \n ADMIN > HIGH \n HIGH > LOW";
		RoleHierarchyImpl rh = RoleHierarchyImpl.fromHierarchy(hierar);
		return rh;
	}
}
