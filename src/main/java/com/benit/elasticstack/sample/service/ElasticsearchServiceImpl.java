package com.benit.elasticstack.sample.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.elasticsearch.client.RestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Service
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService{


    /**
     * ES LIBRARY
     * @return
     */
    public boolean es_connect() {

        // 초기화
        RestClient restClient = RestClient.builder( new HttpHost("https://152.70.235.78", 9200)).build(); // Create the low-level client
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper()); // Create the transport with a Jackson mapper
        ElasticsearchClient client = new ElasticsearchClient(transport); // And create the API client

        // 접속테스트
        log.info( "ES Connect : {}" , client.nodes().toString() );

        return true;
    }


    /**
     * REST API
     * @return
     */
    public boolean es_rest_connect() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        // 초기화
        RestTemplate restTemplate = makeRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://152.70.235.78:9200", String.class);

        log.info("  [response statuscode] {}", responseEntity.getStatusCode());
        log.info("  [response body] {}", responseEntity.getBody());

        return true;
    }







    /**
     * SSL 사설인증서 사용 우회하기
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private RestTemplate makeRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {


        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout(3 * 1000);
        requestFactory.setReadTimeout(3 * 1000);

        return new RestTemplate(requestFactory);
    }

}


