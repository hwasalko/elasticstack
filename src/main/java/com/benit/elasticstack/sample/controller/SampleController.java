package com.benit.elasticstack.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@Slf4j
@RestController
public class SampleController {

    @GetMapping("/")
    public String Home(){
        String now = LocalDateTime.now().toString();
        log.info("첫화면 호출!! 현재시각 -> {}", now);

        return "첫화면 호출!! 현재시각 ->" + now;
    }


    /**
     * Hello 출력 컨트롤러
     * @return
     */
    @GetMapping("/hello")
    public String Hello(){
        String now = LocalDateTime.now().toString();
        log.info(" {} /hello 컨트롤러 호출 ", now);

        return "Hello! -> " + now;
    }


    /**
     * Exception 임의 발생 컨트롤러
     * @return
     * @throws Exception
     */
    @GetMapping("/exception")
    public String ExceptionTest() throws Exception {
        String now = LocalDateTime.now().toString();
        log.info(" {} /exception 컨트롤러 호출! Exception을 임의로 발생시킵니다. ", now);

        throw new Exception("Exception 발생 테스트");

    }

}
