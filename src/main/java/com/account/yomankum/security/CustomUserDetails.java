package com.account.yomankum.security;

import com.account.yomankum.domain.SnsUser;
import com.account.yomankum.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static java.time.LocalDateTime.*;

@AllArgsConstructor
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
            return now().isBefore(user.getPwdChangeDate().plusMonths(3L));
        }
        return now().isBefore(snsUser.getPwdChangeDate().plusMonths(3L));
    }

    @Override
    public boolean isAccountNonLocked() {
        if (snsUser == null) {
            return user.getStopDate() == null ? true : false;
        }
        return snsUser.getStopDate() == null ? true : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (snsUser == null) {
            return user.getRemoveDate() == null ? true : false;
        }
        return snsUser.getRemoveDate() == null ? true : false;
    }

    private Collection<GrantedAuthority> getRoleList(User user) {
        Collection<GrantedAuthority> roleList = new ArrayList<>();
        if (snsUser == null) {
            roleList.add(() -> user.getRole().getName().name());
        } else {
            roleList.add(() -> snsUser.getRole().getName().name());
        }
        return roleList;
    }

    @Override
    public String getName() {
        if (snsUser == null) {
            return user.getEmail();
        }
        return snsUser.getEmail();
    }
}
