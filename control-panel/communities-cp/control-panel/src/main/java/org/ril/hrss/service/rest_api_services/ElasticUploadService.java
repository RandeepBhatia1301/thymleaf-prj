package org.ril.hrss.service.rest_api_services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.ril.hrss.utility.Constants.ELASTIC_UNDERSCORE;

@Service
public class ElasticUploadService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${spring.elastic.url}")
    private String serverUrl;

    @Value("${spring.elastic.delete.url}")
    private String elasticDeleteUrl;


    public ResponseEntity elasticClient(HashMap map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<HashMap<String, Object>> requestEntity = new HttpEntity<HashMap<String, Object>>(map, headers);
        ResponseEntity<Map> response = null;
        try {
            response = restTemplate.postForEntity(serverUrl, requestEntity, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public ResponseEntity elasticDelete(Integer contentTypeId, Integer hierarchyId) {
        final String uri = elasticDeleteUrl + contentTypeId + ELASTIC_UNDERSCORE + hierarchyId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(headers);
        return restTemplate.postForObject(uri, request, ResponseEntity.class);

    }
}
