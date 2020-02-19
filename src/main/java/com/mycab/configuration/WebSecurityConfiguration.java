package com.mycab.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mycab.dataaccessobject.DriverRepository;
import com.mycab.domainobject.DriverDO;

@Configuration
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter
{

    @Autowired
    DriverRepository driversRepository;


    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService());
    }


    @Bean
    UserDetailsService userDetailsService()
    {
        return new UserDetailsService()
        {
            
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
            {
                final DriverDO user = driversRepository.findByUsername(username);
                if (user == null) {
                    throw new UsernameNotFoundException(username);
                }
                return new MyUser(user);
            }
        };
    }
}
