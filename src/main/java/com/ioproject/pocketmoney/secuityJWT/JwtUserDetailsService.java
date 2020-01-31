package com.ioproject.pocketmoney.secuityJWT;

import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.secuityJWT.JwtUserDetails;
import com.ioproject.pocketmoney.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    final ServiceUser serviceUser;

    public JwtUserDetailsService(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<EntityUser> user = serviceUser.getByUsername(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        return user.map(JwtUserDetails::new).get();
    }
}
