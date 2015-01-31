package com.steiner_consult.workers;



import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Philipp on 11.12.14.
 */
public class BaseWorker {

    public RestTemplate buildRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter jacksonMapper = new MappingJackson2HttpMessageConverter();


        return restTemplate;
    }
}
