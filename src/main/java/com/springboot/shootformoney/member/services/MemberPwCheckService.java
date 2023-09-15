package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.utils.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPwCheckService {
    private final PasswordEncoder passwordEncoder;
    private final MemberUtil memberUtil;

    public boolean check(String mPassword){
        String getPw = memberUtil.getMember().getMPassword();
        boolean matched = passwordEncoder.matches(mPassword,getPw);

        if(!matched){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return matched;
    }
}
