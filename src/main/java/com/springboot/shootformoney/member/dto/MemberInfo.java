package com.springboot.shootformoney.member.dto;

import com.springboot.shootformoney.member.entity.LoginData;
import com.springboot.shootformoney.member.enum_.Grade;
import com.springboot.shootformoney.member.enum_.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MemberInfo implements UserDetails {

    private Long mNo;
    private String mId;
    private String mPassword;
    private String mName;
    private String mNickName;
    private String mEmail;
    private String mPhone;
    private Grade grade;
    private Integer level;
    private LoginData loginData;
    private Role role;
//    private

    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return mPassword;
    }

    @Override
    public String getUsername() {
        return mId;
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
