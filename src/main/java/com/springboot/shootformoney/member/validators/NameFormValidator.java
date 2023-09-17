package com.springboot.shootformoney.member.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface NameFormValidator {

    default boolean checkName(String mName){
        String regex = "^[가-힣]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mName);

        return matcher.matches();
    }
}
