package com.benit.elasticstack.sample.controller;


import com.benit.elasticstack.sample.dto.Board;
import com.benit.elasticstack.sample.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        String response = elasticsearchService.es_rest_connect();
        log.info("ES 접속테스트 호출!! Response => \n{}", response);

        return response;

    }




    @PostMapping("/es/index")
    public String EsIndex(Board board) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        log.info(" 파라미터 : {}", board);
        String response = elasticsearchService.es_rest_index(board);

        log.info("ES 인덱싱 호출!! Response => \n{}", response);

        return response;
    }




    @PostMapping("/es/search")
    public String EsSearch(@RequestParam String search_keyword) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {

        // ES 호출
        log.info(" 파라미터 : {}", search_keyword);
        String response = elasticsearchService.es_rest_search(search_keyword);
        log.info("ES 검색 호출!! Response => \n{}", response);

        return response;
    }

}
