package com.springboot.shootformoney.comment;

import com.springboot.shootformoney.comment.dto.CommentRequestDto;
import com.springboot.shootformoney.comment.dto.CommentResponseDto;
import com.springboot.shootformoney.comment.service.CommentService;
import com.springboot.shootformoney.member.utils.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {
    private final CommentService commentService;
    private final MemberUtil memberUtil;

    /* CREATE */
    @PostMapping("/post/{pNo}/comment")
    public ResponseEntity<Long> commentSave(@PathVariable Long pNo,
                                                          @RequestBody CommentRequestDto dto){
        return ResponseEntity.ok(commentService.commentSave(memberUtil.getMember().getMNickName(), pNo,dto));
    }
}
