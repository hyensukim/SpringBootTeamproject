package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.dto.SignUpForm;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.services.MemberSaveService;
import com.springboot.shootformoney.member.validators.SignUpValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberSaveService memberSaveService;
    private final SignUpValidator signUpValidator;

    @PostMapping("/signup")
    public ResponseEntity<Member> signUp(@RequestBody @Valid SignUpForm signUpForm, Errors errors){
        Member member = null;
        try{

//            signUpValidator.validate(signUpForm,errors);
            if(errors.hasErrors()){
                List<FieldError> errs = errors.getFieldErrors();
                StringBuilder sb = new StringBuilder();
                for(FieldError err : errs){
                    sb.append(err.toString()).append("\n");
                }
                System.out.println(sb);
            }

            member = memberSaveService.save(signUpForm);

        }catch(Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body(member);
    }
}
