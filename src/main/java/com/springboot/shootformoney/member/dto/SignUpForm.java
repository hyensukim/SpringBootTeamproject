package com.springboot.shootformoney.member.dto;


    import com.springboot.shootformoney.member.entity.Euro;
    import com.springboot.shootformoney.member.enum_.Grade;
    import com.springboot.shootformoney.member.enum_.Role;
    import jakarta.validation.constraints.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
    public class SignUpForm {

        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min=4, max=16 , message="최소 4자 최대 16자로 입력해주세요.")
        private String mId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min=8, max=16 , message="최소 8자 최대 16자로 입력해주세요.")
        private String mPassword;

        @NotBlank(message = "비밀번호 확인을 입력해주세요.")
        private String mPasswordCheck; // 비밀번호 확인

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(min=2, max=20, message="최소 2자 최대 20자로 입력해주세요.")
        private String mName;

        @NotBlank(message = "연락처를 입력해주세요.")
        private String mPhone;

        @Email(message = "이메일 양식에 맞지 않습니다.")
        private String mEmail;

        @NotBlank(message = "별명을 입력해주세요.")
        @Size(min=4, max=16, message="최소 4자 최대 16자로 입력해주세요.")
        private String mNickName;

        // Mypage 조회용
        private Grade grade;
        
        private Integer level;
        
        private Role role;

        private Integer euroValue;
    }
