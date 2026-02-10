package org.example.visitme.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.visitme.model.entities.LoginEntity;
import org.example.visitme.model.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LoginEntity> optional = loginRepository.findByUsername(username);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("Username \"" + username + "\"does not exist in the system");
        }
        LoginEntity entity = optional.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER_AUTHORITY"));
        return new User(entity.getUsername(), entity.getPassword(), authorities);
    }
    

}
