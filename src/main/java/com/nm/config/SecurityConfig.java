package com.nm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nm.security.CustomAuthenticationFailureHandler;
import com.nm.service.LoginService;

@Configuration
public class SecurityConfig {
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CustomAuthenticationFailureHandler failureHandler;

	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Order(1)
	public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/admin/**")
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/admin/login", "/admin/register").permitAll().anyRequest().hasRole("ADMIN"))
				.formLogin(form -> form.loginPage("/admin/login") // trang login admin
						.loginProcessingUrl("/admin/login") // xử lý
						.defaultSuccessUrl("/admin", true) // đăng nhập thành công -> về trang chủ admin
						.failureUrl("/admin/login?error=true").permitAll())
				.logout(logout -> logout.logoutUrl("/admin/logout").logoutSuccessUrl("/admin/login?logout")
						.invalidateHttpSession(true) // Hủy session khi logout
						.deleteCookies("JSESSIONID") // Xóa cookie phiên làm việc
						.permitAll())

				.userDetailsService(loginService);

		return http.build();
	}
	
	 @Bean
	    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf().disable()
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers(
	                    "/login", "/register", 
	                    "/css/**", "/js/**", "/images/**",
	                    "/", "/manga-detail", "/read-chapter.html", 
	                    "/manga-list", "/search" , "/manga-category"
	                ).permitAll()
	                .anyRequest().authenticated()
	            )
	            .formLogin(form -> form
	                .loginPage("/login")
	                .loginProcessingUrl("/login")
	                .defaultSuccessUrl("/", true)
	                .failureHandler(failureHandler) // xử lý lỗi cụ thể
	                .permitAll()
	            )
	            .logout(logout -> logout
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/")
	                .invalidateHttpSession(true)
	                .deleteCookies("JSESSIONID")
	                .permitAll()
	            )
	            .userDetailsService(loginService);

	        return http.build();
	    }

}
