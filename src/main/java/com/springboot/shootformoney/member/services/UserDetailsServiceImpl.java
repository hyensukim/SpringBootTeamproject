package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.dto.MemberInfo;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findBymId(username);

        if(member == null){
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities
                = List.of(new SimpleGrantedAuthority(member.getRole().toString()));

        return MemberInfo.builder()
                .mNo(member.getMNo())
                .mId(member.getMId())
                .mPassword(member.getMPassword())
                .mName(member.getMName())
                .mEmail(member.getMEmail())
                .mPhone(member.getMPhone())
                .role(member.getRole())
                .authorities(authorities)
                .build();
    }
}
