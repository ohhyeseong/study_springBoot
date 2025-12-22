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

    // 필드 (멤버 변수)
    // 로그인한 사용자의 주요 정보를 담아주는 변수들
    // User 엔티티에서 필요한 정보만 뽑아와서 저장한다.
    private final Long id;
    private final String username;
    private final String password;
    private final String nickname;
    private final UserRole role;

    // 생성자
    // 데이터베이스에서 조회한 User 엔티티 객체를 받아와서 SecurityUser 객체를 생성하는 생성자.
    // 이 과정을 통해 DB 데이터(User)가 Spring Security가 사용하는 보안 객체(SecurityUser)로 변환된다.
    public SecurityUser(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }

    // == 밑에 있는 내용들은 UserDetails 인터페이스의 구현 메서드들이다. ==

    // 사용자가 가진 권한(Role) 목록을 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));// 여기서 ROLE_ 은 시큐리티 접두사다.
    }

    @Override public boolean isAccountNonExpired() {return true;} // 계정 만료 여부
    @Override public boolean isAccountNonLocked() {return true;} // 계정 잠금 여부
    @Override public boolean isCredentialsNonExpired() {return true;} // 비밀번호 만료
    @Override public boolean isEnabled() {return true;} // 계정 활성화 여부
}
