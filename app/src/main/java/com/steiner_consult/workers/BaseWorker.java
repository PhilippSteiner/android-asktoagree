package com.steiner_consult.workers;



import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by Philipp on 11.12.14.
 */
public class BaseWorker {

    protected HttpEntity<?> getRequestEntity(Object obj) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAcceptEncoding(ContentCodingType.GZIP);
        httpHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        httpHeaders.setContentType(new MediaType("application", "json"));

        if (obj != null) {
            return new HttpEntity<>(obj, httpHeaders);
        }
        return new HttpEntity<>(httpHeaders);
    }

    protected RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter jacksonMapper = new MappingJackson2HttpMessageConverter();
        jacksonMapper.getObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        restTemplate.getMessageConverters().add(jacksonMapper);
        return restTemplate;
    }
}
