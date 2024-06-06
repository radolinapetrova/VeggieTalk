    package com.veggietalk.api_gateway.config;


    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
    import org.springframework.security.core.session.SessionRegistry;
    import org.springframework.security.core.session.SessionRegistryImpl;
    import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
    import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
    import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
    import org.springframework.security.web.session.HttpSessionEventPublisher;
    import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

    import java.util.Collection;
    import java.util.HashSet;
    import java.util.Map;
    import java.util.Set;
    import java.util.stream.Collectors;


    @Configuration
    @EnableWebSecurity
    class SecurityConfig {

        @Bean
        public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers(new AntPathRequestMatcher("/api/account/*"))
                    .hasRole("user")
                    .requestMatchers(new AntPathRequestMatcher("/api/posts/*"))
                    .permitAll()
                    .anyRequest()
                    .authenticated());
            http.oauth2ResourceServer((oauth2) -> oauth2
                    .jwt(Customizer.withDefaults()));
//            http.oauth2Login(Customizer.withDefaults())
//                    .logout(logout -> logout.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/"));
            return http.build();
        }

    }