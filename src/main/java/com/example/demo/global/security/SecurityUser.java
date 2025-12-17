package com.example.demo.global.security;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class SecurityUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String nickname;
    private final UserRole role;

    public SecurityUser(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    @Override public boolean isAccountNonExpired() {return true;} // 계정 만료 여부
    @Override public boolean isAccountNonLocked() {return true;} // 계정 잠금 여부
    @Override public boolean isCredentialsNonExpired() {return true;} // 비밀번호 만료
    @Override public boolean isEnabled() {return true;} // 계정 활성화 여부
}
