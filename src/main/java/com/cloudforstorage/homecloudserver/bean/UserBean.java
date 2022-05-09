package com.cloudforstorage.homecloudserver.bean;

import com.cloudforstorage.homecloudserver.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class UserBean implements UserDetails {

    private UserEntity user;

    public UserBean(){}
    public UserBean(UserEntity user){this.user = user;}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return Arrays.asList(authority);
    }

    public Long getId(){ return user.getId();}

    public String getRoles(){ return user.getRole(); }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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
        return user.getActive();
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "user=" + user.getUserName() +
                '}';
    }
}