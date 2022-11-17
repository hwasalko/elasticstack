package com.benit.elasticstack.sample.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {


    /**
     * es 색인화면
     * @return
     */
    @GetMapping("/input")
    public String inputUI(){
        return "input.html";
    }


    /**
     * es 검색화면
     * @return
     */
    @GetMapping("/search")
    public String searchUI(){
        return "search.html";
    }

}
