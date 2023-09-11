package com.springboot.shootformoney.comment;

public class CommentCreateDto { //댓글 생성 요청을 처리하기 위한 DTO
    private Integer mNo;
    private Integer pNo;
    private String cContent;

    // getters and setters...

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

    @Override
    public String toString() {
        return "CommentCreateDto{" +
                "mNo=" + mNo +
                ", pNo=" + pNo +
                ", cContent='" + cContent + '\'' +
                '}';
    }
}