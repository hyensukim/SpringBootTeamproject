package com.springboot.shootformoney.member.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface PwFormValidator {

    default boolean checkForm_alphabet(String mPassword){

        String regex = "[a-z|A-Z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mPassword);

        return matcher.matches();
    }

    default boolean checkForm_number(String mPassword){

        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mPassword);

        return matcher.matches();
    }

    default boolean checkForm_special(String mPassword){

        String regex = "[!@#$%^&*()_+\\-=]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mPassword);

        return matcher.matches();
    }
}
