package com.benit.elasticstack.sample.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@ToString
public class Board {

    private String title; // 제목
    private String contents; // 내용
    private String reg_ddtm; // 등록일시


    // 생성자
    public Board(String reg_ddtm) {
        this.reg_ddtm = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME); // ISO8601 포맷으로 현재시간을 주입
    }
}
