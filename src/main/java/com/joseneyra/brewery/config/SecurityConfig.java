package com.joseneyra.brewery.config;


import com.joseneyra.brewery.security.JpaUserDetailsService;
import com.joseneyra.brewery.security.MyCustomPasswordEncodingFactories;
import com.joseneyra.brewery.security.RestHeaderAuthFilter;
import com.joseneyra.brewery.security.RestUrlParamAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)              // Enables method annotation security @Secure()
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Creates a Header Auth Filter using our custom RestHeaderAuth filter
    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RestUrlParamAuthFilter restUrlParamAuthFilter(AuthenticationManager authenticationManager) {
        RestUrlParamAuthFilter filter = new RestUrlParamAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }


    // Configures the accessibility of endpoints
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Adds our custom filter (Not Recommended, only used in legacy applications)
        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
                        .csrf().disable();  // Turns off csrf (This is why this method is Not Recommended);

        http.addFilterBefore(restUrlParamAuthFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);        // No need to disable the csrf because we already disable it in the first filter setup

        http.authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/h2-console/**")
                                .permitAll()  // Do not use in production

                            .antMatchers("/","/webjars/**", "/login", "/resources/**")
                                .permitAll()

                            .mvcMatchers("/beers/find", "/beers/{beerId}")
                                .hasAnyRole("ADMIN","USER", "CUSTOMER")  // Is better to separate the paths instead of adding all the unsecure resources to one antMatcher

                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**")
                                .hasAnyRole("ADMIN","USER", "CUSTOMER")

                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}")
                                .hasAnyRole("ADMIN","USER", "CUSTOMER")

//                            .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**")
//                                .hasRole("ADMIN")             // Replaced with @PreAuthorizedAnnotation()

                            .mvcMatchers("/brewery/breweries")
                                .hasAnyRole("ADMIN","CUSTOMER")

                            .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries")
                                .hasAnyRole("ADMIN","CUSTOMER")
                    ;  // Allows us to use the mvc capabilities of mapping the params
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();

        // h2 console changes
        http.headers().frameOptions().sameOrigin();
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("pass")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("pass")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }


    // The NoOp is not recommended for new applications
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    // The LDap is not recommended for new applications
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new LdapShaPasswordEncoder();
//    }



    // The Delegating Password Encoder - Allows us to use different password encoding algorithms
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return MyCustomPasswordEncodingFactories.createDelegatingPasswordEncoder();
//    }


    // The BCrypt is recommended for new applications
    @Bean
    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
        return MyCustomPasswordEncodingFactories.createDelegatingPasswordEncoder();     // Use your custom factory
    }


    // No longer needed because we setup the UserDetail Service that gets the credentials from the DB
    // Configures users
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("{bcrypt}$2a$10$1tgoXmeRc1PnWpgvXt20Kui9vS204M4yPBkWJ/FN9YR5JejgFcfsy")         // Need to specify the password encoder {noop}
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{bcrypt}$2a$10$1tgoXmeRc1PnWpgvXt20Kui9vS204M4yPBkWJ/FN9YR5JejgFcfsy")
//                .roles("USER")
//                .and()
//                .withUser("scott")
//                .password("{bcrypt}$2a$10$oH6XZDH5lbipL0bv7Bat0OPoqY0LKzufVMlVkVOgSWUpvvXSjVE5C")
//                .roles("CUSTOMER");
//
}
