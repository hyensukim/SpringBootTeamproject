package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.utils.SendMailUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    @Value("${custom.siteName}")
    private String siteName;
    @Value("${custom.siteMainUri}")
    private String siteMainUri;
    @Value("${custom.emailFrom}")
    private String emailFrom;
    @Value("${custom.emailFromName}")
    private String emailFromName;

    private final JavaMailSender javaMailSender;

    @Getter
    private String tempPw;

    //
    public void sendResetPwUrlByEmail(Member member) throws MessagingException {
        String title = "[ " + siteName + " ] " + "비밀번호 변경 안내";
        String mEmail = member.getMEmail();
        String mNickName = member.getMNickName();
        tempPw = makeTempPass();
        String loginUrl = siteMainUri + "/member/login";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setFrom(emailFrom);
        mimeMessageHelper.setTo(mEmail);

        StringBuilder body = new StringBuilder();
        body.append(SendMailUtil.getMailBody(mNickName,tempPw,loginUrl));
        mimeMessageHelper.setText(body.toString(),true);
        javaMailSender.send(mimeMessage);
    }

    // 임시 비밀번호 생성
    private static String makeTempPass(){
        char[] charNums = new char[] { '0','1','2','3','4','5','6','7','8','9'};

        char[] charAlphas = new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
                'S','T','U','V','W','X','Y','Z'};

        char[] charSpecials = new char[] {'!','@','#','$','%','^','&'};

        StringBuilder str = new StringBuilder();

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 3; i++) {
            idx = (int) (charNums.length * Math.random());
            str.append(charNums[idx]);
        }

        idx = 0;
        for(int i=0; i < 3; i++){
            idx = (int) (charAlphas.length * Math.random());
            str.append(charAlphas[idx]);
        }

        idx = 0;
        for(int i=0; i < 2; i++){
            idx = (int) (charSpecials.length * Math.random());
            str.append(charSpecials[idx]);
        }

        return str.toString();
    }
}
