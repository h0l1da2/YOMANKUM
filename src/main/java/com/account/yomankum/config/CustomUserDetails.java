package com.account.yomankum.config;

import com.account.yomankum.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static java.time.LocalDateTime.*;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoleList(user);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return now().isBefore(user.getPwdChangeDate().plusMonths(3L));
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStopDate() == null ? true : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getRemoveDate() == null ? true : false;
    }

    private Collection<GrantedAuthority> getRoleList(User user) {
        Collection<GrantedAuthority> roleList = new ArrayList<>();
        roleList.add(() -> user.getRole().getName().name());
        return roleList;
    }
}
