package com.benit.elasticstack.sample.controller;


import com.benit.elasticstack.sample.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Slf4j
@RestController
public class ElasticsearchController {


    @Autowired
    ElasticsearchService elasticsearchService;



    /**
     * ES 접속테스트
     * @return
     */
    @GetMapping("/es/test")
    public String EsConnect() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String now = LocalDateTime.now().toString();
        log.info("ES 접속테스트 호출!! 결과 : {}     /     현재시각 -> {}", elasticsearchService.es_rest_connect(), now);

        return "ES 접속테스트 호출!! 현재시각 ->" + now;
    }

}
