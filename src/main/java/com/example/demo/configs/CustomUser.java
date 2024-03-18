package com.example.demo.configs;




import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.Servicer;
import com.example.demo.entity.User;

import java.util.Arrays;
import java.util.Collection;

public class CustomUser implements UserDetails {
    private final Object userEntity;

    public CustomUser(Object userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userEntity instanceof User) {
            User user = (User) userEntity;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
            return Arrays.asList(authority);
        } else if (userEntity instanceof Servicer) {
            Servicer servicer = (Servicer) userEntity;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(servicer.getRole());
            return Arrays.asList(authority);
        } else {
            throw new IllegalArgumentException("Unsupported user entity type");
        }
    }

    @Override
    public String getPassword() {
        if (userEntity instanceof User) {
            User user = (User) userEntity;
            return user.getPassword();
        } else if (userEntity instanceof Servicer) {
            Servicer servicer = (Servicer) userEntity;
            return servicer.getPassword();
        } else {
            throw new IllegalArgumentException("Unsupported user entity type");
        }
    }

    @Override
    public String getUsername() {
        if (userEntity instanceof User) {
            User user = (User) userEntity;
            return user.getEmail();
        } else if (userEntity instanceof Servicer) {
            Servicer servicer = (Servicer) userEntity;
            return servicer.getEmail();
        } else {
            throw new IllegalArgumentException("Unsupported user entity type");
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

