package com.springboot.shootformoney.member.exceptions;

public class EuroNotExistException extends RuntimeException{
    public EuroNotExistException(){
        super("해당 회원의 유로가 존재하지 않습니다.");
    }
}
