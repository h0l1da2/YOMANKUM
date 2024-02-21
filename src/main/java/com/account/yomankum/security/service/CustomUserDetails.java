package com.account.yomankum.security.service;

import com.account.yomankum.user.domain.SnsUser;
import com.account.yomankum.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static java.time.Instant.now;

public class CustomUserDetails implements UserDetails, OAuth2User {

    private User user;
    private SnsUser snsUser;
    private Map<String, Object> attributes;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public CustomUserDetails(SnsUser snsUser, Map<String, Object> attributes) {
        this.snsUser = snsUser;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

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
        if (snsUser == null) {
            return user.getEmail();
        }
        return snsUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        if (snsUser == null) {
            return now().isBefore(user.getPwdChangeDatetime().plus(3L, ChronoUnit.MONTHS));
        }
        return now().isBefore(snsUser.getPwdChangeDatetime().plus(3L, ChronoUnit.MONTHS));
    }

    @Override
    public boolean isAccountNonLocked() {
        if (snsUser == null) {
            return user.getStopDatetime() == null;
        }
        return snsUser.getStopDatetime() == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (snsUser == null) {
            return user.getRemoveDatetime() == null;
        }
        return snsUser.getRemoveDatetime() == null;
    }

    private Collection<GrantedAuthority> getRoleList(User user) {
        Collection<GrantedAuthority> roleList = new ArrayList<>();
        if (snsUser == null) {
            roleList.add(() -> user.getRole().getRoleName().name());
        } else {
            roleList.add(() -> snsUser.getRole().getRoleName().name());
        }
        return roleList;
    }

    @Override
    public String getName() {
        if (snsUser == null) {
            return user.getNickname();
        }
        return snsUser.getNickname();
    }
}
