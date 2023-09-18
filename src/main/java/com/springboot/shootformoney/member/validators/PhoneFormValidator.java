package com.springboot.shootformoney.member.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface PhoneFormValidator {
    default boolean checkMobile(String mPhone){

        mPhone = mPhone.replaceAll("\\D","");

        String regex = "^01(0|1|[6-8])\\d{3,4}\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mPhone);

        return matcher.matches();
    }
}
