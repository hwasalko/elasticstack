package com.benit.elasticstack.sample.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UIController {


    /**
     * es 색인화면
     * @return
     */
    @GetMapping("/input")
    public String inputUI(){
        log.info("ES 색인화면으로 이동합니다.");
        return "/input.html";
    }


    /**
     * es 검색화면
     * @return
     */
    @GetMapping("/search")
    public String searchUI(){
        log.info("ES 검색화면으로 이동합니다.");
        return "/search.html";
    }

}
