package com.authentication.service;
import com.authentication.model.User;
import com.authentication.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImplementation implements UserDetailsService{
    private final userRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User optionalUser = repository.findByEmail(email);
        if(optionalUser==null) throw new UsernameNotFoundException("Username not found", null);
        return new org.springframework.security.core.userdetails.User(optionalUser.getEmail(), optionalUser.getPassword()
                , new ArrayList<>());
    }
}



