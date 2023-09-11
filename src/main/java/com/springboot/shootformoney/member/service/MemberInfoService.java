package com.springboot.shootformoney.member.service;

import com.springboot.shootformoney.member.dto.MemberInfo;
import com.springboot.shootformoney.member.entity.MemberEntity;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByM_id(username);

        if(member == null){
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities
                = Arrays.asList(new SimpleGrantedAuthority(member.getRole().toString()));

        return MemberInfo.builder()
                .m_no(member.getM_no())
                .m_id(member.getM_id())
                .m_password(member.getM_password())
                .m_name(member.getM_name())
                .m_email(member.getM_email())
                .m_phone(member.getM_phone())
                .role(member.getRole())
                .authorities(authorities)
                .build();
    }
}
