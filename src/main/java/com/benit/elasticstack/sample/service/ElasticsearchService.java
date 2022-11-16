package com.benit.elasticstack.sample.service;


import com.benit.elasticstack.sample.dto.Board;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public interface ElasticsearchService {


    /**
     * ES java API 접속 테스트
     * @return
     */
    public boolean es_connect();


    /**
     * REST ES접속테스트
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String es_rest_connect() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException;



    /**
     * ES 색인 서비스
     * @param board
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String es_rest_index(Board board) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException;




    /**
     * ES 검색 서비스
     * @param search_keyword
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String es_rest_search(String search_keyword) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException;


}
