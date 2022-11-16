package com.benit.elasticstack.sample.service;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public interface ElasticsearchService {


    public boolean es_connect();

    public String es_rest_connect() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException;

}
