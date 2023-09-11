package com.springboot.shootformoney.comment;

public class CommentUpdateDto { //댓글 수정 요청을 처리하기 위한 DTO
    private String cContent;

    // getters and setters...

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent;
    }

    @Override
    public String toString() {
        return "CommentUpdateDto{" +
                "cContent='" + cContent + '\'' +
                '}';
    }
}