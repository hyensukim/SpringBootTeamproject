package com.springboot.shootformoney.member.enum_;

public enum Grade {
    CONFERENCE(0.2), // 0.1
    EUROPA(0.1), // 0.07
    CHAMPIONS(0.05); // 0.05

    private final Double fee;

    Grade(double fee){
        this.fee = fee;
    }

    public double getFee(){
        return this.fee;
    }
}
