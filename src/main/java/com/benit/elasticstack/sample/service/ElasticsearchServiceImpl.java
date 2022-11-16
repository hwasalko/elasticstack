package com.benit.elasticstack.sample.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.benit.elasticstack.sample.dto.Board;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.elasticsearch.client.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.json.JsonObject;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Service
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService{


    // application.yml 파일에서 사용자 프로퍼티 가져와서 변수에 바인딩
    @Value("${elasticsearch.host}")
    private String es_host;

    @Value("${elasticsearch.protocol}")
    private String es_protocol;

    @Value("${elasticsearch.port}")
    private String es_port;

    @Value("${elasticsearch.user.id}")
    private String es_user_id;

    @Value("${elasticsearch.user.pw}")
    private String es_user_pw;






    /**
     * REST API
     *  - elastic 사에서 제공해주는 Java 라이브러리를 이용하지 않고, 일반적인 REST API 방식으로 호출
     *  - lib 버전과 종속성이 없어지는 장점
     *
     * @return boolean
     */
    public String es_rest_connect() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        // 초기화
        RestTemplate restTemplate = makeRestTemplate();



        // *********** 1. Request *****************

        // Request URI 셋팅
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl( es_protocol + "://" + es_host + ":" + es_port).build(true);

        // Request Header 설정
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setBasicAuth(es_user_id, es_user_pw); // 인가를 위한 ID/PW 셋팅

        // Request Entity 설정
        HttpEntity<String> requestHttpEntity = new HttpEntity<>(requestHeader);

        // Request 요청
        log.info("  [request URI] {}", uriComponents.toUriString());
        log.info("  [request header] {}", requestHttpEntity );
        ResponseEntity<String> responseEntity = restTemplate.exchange( uriComponents.toUriString(), HttpMethod.GET, requestHttpEntity, String.class );



        // *********** 2. Response *****************
        log.info("  [response statuscode] {}", responseEntity.getStatusCode());
        log.info("  [response body] {}", responseEntity.getBody());

        return responseEntity.getBody();
    }







    /**
     * ES 색인 서비스
     * @param board
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String es_rest_index(Board board) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException{

        // 초기화
        RestTemplate restTemplate = makeRestTemplate();



        // *********** 1. Request *****************

        // Request URI 셋팅
        String index_name = "my_nori_ap";
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl( es_protocol + "://" + es_host + ":" + es_port + "/" + index_name + "/_doc").build(true);

        // Request Header 설정
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setBasicAuth(es_user_id, es_user_pw); // 인가를 위한 ID/PW 셋팅
        requestHeader.setContentType(MediaType.APPLICATION_JSON); // 컨텐트타입 설정(json body)


        // Request Entity 설정
        HttpEntity<Board> requestHttpEntity = new HttpEntity<>(board, requestHeader);


        // Request 요청
        log.info("  [request URI] {}", uriComponents.toUriString());
        log.info("  [request header] {}", requestHttpEntity );
        ResponseEntity<String> responseEntity = restTemplate.exchange( uriComponents.toUriString(), HttpMethod.POST, requestHttpEntity, String.class );



        // *********** 2. Response *****************
        log.info("  [response statuscode] {}", responseEntity.getStatusCode());
        log.info("  [response body] {}", responseEntity.getBody());

        return responseEntity.getBody();

    }



    /**
     * ES 검색 서비스
     * @param search_keyword
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    /*
    POST my_nori_ap/_search
    {
      "query": {
        "match": {
          "contents": "테스트"
        }
      },
      "_source": {
        "excludes": "contents"
      },
      "highlight": {
        "fields": {
          "contents": {}
        },
        "pre_tags": [
          "★"
        ],
        "post_tags": [
          "★"
        ]
      }
    }
     */
    public String es_rest_search(String search_keyword) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException{

        // 초기화
        RestTemplate restTemplate = makeRestTemplate();



        // *********** 1. Request *****************

        // Request URI 셋팅
        String index_name = "my_nori_ap";
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl( es_protocol + "://" + es_host + ":" + es_port + "/" + index_name + "/_search").build(true);

        // Request Header 설정
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setBasicAuth(es_user_id, es_user_pw); // 인가를 위한 ID/PW 셋팅
        requestHeader.setContentType(MediaType.APPLICATION_JSON); // 컨텐트타입 설정(json body)



        // Request Param 설정
        JSONObject root = new JSONObject();

        JSONObject query = new JSONObject();
        JSONObject match = new JSONObject();

        match.put("contents", search_keyword); // 검색어 주입
        query.put("match", match);
        root.put("query", query);


        JSONObject _source = new JSONObject();
        _source.put("excludes", "contents"); // 원문은 size가 크기때문에 리턴결과에서 제외
        root.put("_source", _source);



        JSONObject highlight = new JSONObject();
        JSONObject fields = new JSONObject();
        JSONObject contents = new JSONObject();
        JSONArray pre_tags = new JSONArray();
        JSONArray post_tags = new JSONArray();

        fields.put("contents", contents);
        highlight.put("fields", fields);

        pre_tags.add("★");
        post_tags.add("★");
        highlight.put("pre_tags", pre_tags);
        highlight.put("post_tags", post_tags);
        root.put("highlight", highlight);





        // Request Entity 설정
        HttpEntity<String> requestHttpEntity = new HttpEntity<>(root.toJSONString(), requestHeader);


        // Request 요청
        log.info("  [request URI] {}", uriComponents.toUriString());
        log.info("  [request header] {}", requestHttpEntity );
        ResponseEntity<String> responseEntity = restTemplate.exchange( uriComponents.toUriString(), HttpMethod.POST, requestHttpEntity, String.class );



        // *********** 2. Response *****************
        log.info("  [response statuscode] {}", responseEntity.getStatusCode());
        log.info("  [response body] {}", responseEntity.getBody());

        return responseEntity.getBody();

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



}


