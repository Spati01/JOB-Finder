package com.job.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private OAuthAuthenicationSuccessHandler handler;

	@Autowired
	public CustomAuthSucessHandler sucessHandler;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
  
  //Configuration 

  // urls configuration public or protect 
  httpSecurity.authorizeHttpRequests(authorize->{
  //authorize.requestMatchers("/home","/register", "/services").permitAll();
authorize.requestMatchers("/user/**").authenticated();
authorize.anyRequest().permitAll();
  } );
//form default login 
  httpSecurity.formLogin(formLogin->{
 
  formLogin.loginPage("/login");
  formLogin.loginProcessingUrl("/authenticate");
  formLogin.successForwardUrl("/user/carrer");
  //formLogin.failureForwardUrl("/login?error=true");
  formLogin.usernameParameter("email");
  formLogin.passwordParameter("password");
 
  /*
  formLogin.failureHandler(new AuthenticationFailureHandler() {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
    }
    
  });
formLogin.successHandler(new AuthenticationSuccessHandler() {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
  }
  
});

 */



  });

  httpSecurity.csrf(AbstractHttpConfigurer::disable);
  httpSecurity.logout(logoutForm->{
 logoutForm.logoutUrl("/do-logout");
  logoutForm.logoutSuccessUrl("/login?logout=true");

  });

//oauth2 Configuration 

httpSecurity.oauth2Login(oauth->{
  
  oauth.loginPage("/login");
  oauth.successHandler(handler);

});




  return httpSecurity.build();

}


}