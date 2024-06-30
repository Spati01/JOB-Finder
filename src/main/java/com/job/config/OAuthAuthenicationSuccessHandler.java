package com.job.config;


import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.job.Entity.User;
import com.job.Repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler{


    Logger logger = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);
    @Autowired
    private UserRepo userRepo;




    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
       
                logger.info("OAuthAuthenticationSuccessHamdler");


// Intentfy the provider 
var  oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;

String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();

logger.info(authorizedClientRegistrationId);



var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

oauthUser.getAttributes().forEach((key, value)->{
   logger.info(key + " : " + value);
});

//User class store data

User user1 = new User();
                                  


if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
//GOOGLE
user1.setEmail(oauthUser.getAttribute("email").toString());

user1.setName(oauthUser.getAttribute("name").toString());


}else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
//GITHUB
String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
 : oauthUser.getAttribute("login").toString() + "@gmail.com";

 String picture = oauthUser.getAttribute("avatar_url").toString();
String name = oauthUser.getAttribute("login").toString();
String providerUserId = oauthUser.getName();

user1.setEmail(email);




}else if(authorizedClientRegistrationId.equalsIgnoreCase("linkedin")){

//LINKEDIN



}else{
    logger.info("OAuthAuthentictionSuccessHandler : Unknown provider");

}












        /* 
                //response.sendRedirect("/home");
          DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
      
      
        //   logger.info(user.getName());
        //     user.getAttributes().forEach((key, value) ->{
        //      logger.info("{} => {}",key,value);

        //     });
        //     logger.info(user.getAuthorities().toString());


        //Data  database save 
     String email = user.getAttribute("email").toString();
     String name = user.getAttribute("name").toString();
     String picture = user.getAttribute("picture").toString();

     //create user and save in database

     User user2 = new User();
     user2.setEmail(email);
     user2.setName(name);
     user2.setProfilePic(picture);
     user2.setPassword("password");
    user2.setUserId(UUID.randomUUID().toString());
    user2.setProvider(Providers.GOOGLE);
    user2.setEnabled(true);
    user2.setEmailVerified(true);
    user2.setProviderUserId(user.getName());
    user2.setRollList(List.of(AppConstants.ROLE_USER));
    user2.setAbout("This account is created using google..");
 
    User userData  =  userRepo.findByEmail(email).orElse(null);

    if(userData == null){
        userRepo.save(user2);
        logger.info("User save : " + email);

    }
          
    */


    User userData  =  userRepo.findByEmail(user1.getEmail());

    if(userData == null){
        userRepo.save(user1);
       // logger.info("User save : " + email);

    }
    new DefaultRedirectStrategy().sendRedirect(request, response, "/user/carrer");
       
    }

 

}
