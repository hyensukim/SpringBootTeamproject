package com.springboot.shootformoney.member.exceptions;

public class MemberNotExistException extends RuntimeException{

    public MemberNotExistException(){
        super("존재하지 않는 회원입니다.");
    }
}
