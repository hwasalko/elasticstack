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
     *  - Dev Tools 기준 "GET /" 호출결과와 동일
     *  - ES info를 json으로 리턴
     * @return
     */
    @GetMapping("/es/test")
    public String EsConnect() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String now = LocalDateTime.now().toString();
        String response = elasticsearchService.es_rest_connect();
        log.info("ES 접속테스트 호출!! 현재시각 -> {}", now);
        log.info("Response => \n{}", response);

        return response;
    }

}
