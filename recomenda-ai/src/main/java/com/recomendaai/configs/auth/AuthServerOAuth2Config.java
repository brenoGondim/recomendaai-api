//package com.recomendaai.configs.auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.stereotype.Component;
//
////@Configuration
//@EnableAuthorizationServer
//@Component
//public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {
//
//
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    public AuthServerOAuth2Config(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Override
//    public void configure(
//            AuthorizationServerSecurityConfigurer oauthServer)
//            throws Exception {
//        oauthServer
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()");
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("angular")
//                .secret("teste")
//                .scopes("read","write")
//                .authorizedGrantTypes("password")
//                .accessTokenValiditySeconds(1800);
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(getTokenStore())
//                //.accessTokenConverter(accessTokenConverter())
//                .authenticationManager(authenticationManager);
//
//    }
//
////    @Bean
////    public JwtAccessTokenConverter accessTokenConverter() {
////        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
////        accessTokenConverter.setSigningKey("recomendaai");
////        return accessTokenConverter;
////    }
////
//    @Bean
//    private TokenStore getTokenStore() {
//        return new InMemoryTokenStore();
//    }
//}
