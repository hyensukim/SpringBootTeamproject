package com.springboot.shootformoney.member.enum_;

public enum Grade {
    // 수수료
    CONFERENCE(0.2),
    EUROPA(0.1),
    CHAMPIONS(0.05);

    private final Double fee;

    Grade(double fee){
        this.fee = fee;
    }

    public double getFee() {
        return this.fee;
    }
}
