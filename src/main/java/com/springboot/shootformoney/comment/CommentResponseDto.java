package com.springboot.shootformoney.comment;

import com.springboot.shootformoney.comment.Comment;

import java.time.LocalDateTime;

public class CommentResponseDto { //댓글 조회 요청에 대한 응답을 전달하기 위한 DTO
    private Integer cNo;
    private Integer mNo;
    private Integer pNo;
    private String cContent;
    private LocalDateTime cCreatedAt;
    private LocalDateTime cUpdatedAt;

    public CommentResponseDto(Comment newComment) {
    }

    // getters and setters...

    public Integer getcNo() {
        return cNo;
    }

    public void setcNo(Integer cNo) {
        this.cNo = cNo;
    }

    public Integer getmNo() {
        return mNo;
    }

    public void setmNo(Integer mNo) {
        this.mNo = mNo;
    }

    public Integer getpNo() {
        return pNo;
    }

    public void setpNo(Integer pNo) {
        this.pNo = pNo;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent;
    }

    public LocalDateTime getcCreatedAt() {
        return cCreatedAt;
    }

    public void setcCreatedAt(LocalDateTime cCreatedAt) {
        this.cCreatedAt = cCreatedAt;
    }

    public LocalDateTime getcUpdatedAt() {
        return cUpdatedAt;
    }

    public void setcUpdatedAt(LocalDateTime cUpdatedAt) {
        this.cUpdatedAt = cUpdatedAt;
    }

    @Override
    public String toString() {
        return "CommentResponseDto{" +
                "cNo=" + cNo +
                ", mNo=" + mNo +
                ", pNo=" + pNo +
                ", cContent='" + cContent + '\'' +
                ", cCreatedAt=" + cCreatedAt +
                ", cUpdatedAt=" + cUpdatedAt +
                '}';
    }
}