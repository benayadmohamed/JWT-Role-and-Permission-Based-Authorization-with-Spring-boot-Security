package com.configs.security;

import com.models.User;
import com.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository UserRepository;

    public UserDetailsServiceImpl(com.repositories.UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User User = UserRepository.findByUsername(username);
        if (User == null) {
            throw new UsernameNotFoundException(username);
        }
        return User;
    }
}
