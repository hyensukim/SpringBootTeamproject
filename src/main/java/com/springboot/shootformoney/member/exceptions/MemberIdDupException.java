package com.springboot.shootformoney.member.exceptions;

public class MemberIdDupException extends RuntimeException{
    public MemberIdDupException(){
        super("중복된 아이디입니다. 다시 입력해주세요.");
    }
}
