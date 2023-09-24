package com.springboot.shootformoney.member.utils;

public class SendMailUtil {

    public static String getMailBody(String mNickName, String tempPw, String loginUrl){
        return "<p> 안녕하세요. <b>" + mNickName + "</b> 님, </p> \n"
                + "<p> 귀하께서 요청하신 비밀번호 변경 페이지 수신을 위해 </p> \n"
                + "<br>"
                + "<p> 발송된 메일 입니다.</p> \n"
                + "<p> 임시 비밀번호 : <h2>\"" + tempPw +"\"</h2></p>"
                + "<br>"
                + "<h3><a style='text-decoration: none; background-color: #C6BFBFFF; color: red;'"
                + "href='"+loginUrl+"' target='_blank'>로그인 페이지로 이동</a></h3>"
                + "<p style='text-decoration-line: underline; color:red;'>로그인 후에 비밀번호를 변경해주세요</p>";
    }

}
