package com.steiner_consult.workers;



import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.utilities.AppConfig;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by Philipp on 11.12.14.
 */
public abstract class BaseWorker {

    protected final String TAG = getClass().getName();
    protected BaseActivity baseActivity;
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor prefEditor;

    protected BaseWorker(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        sharedPreferences = baseActivity.getSharedPreferences(AppConfig.PREFS_NAMESPACE, Context.MODE_PRIVATE);
        prefEditor = sharedPreferences.edit();
    }

    protected HttpEntity<?> getRequestEntity(Object obj) {
        String sessionCookie = getSessionCookie();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAcceptEncoding(ContentCodingType.GZIP);
        httpHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        httpHeaders.setContentType(new MediaType("application", "json"));

        if (sessionCookie != null) {
            httpHeaders.set(AppConfig.AUTH_TOKEN_HEADER, sessionCookie);
        }
        if (obj != null) {
            return new HttpEntity<>(obj, httpHeaders);
        }
        return new HttpEntity<>(httpHeaders);
    }

    public String getSessionCookie() {
        return sharedPreferences.getString(AppConfig.AUTH_TOKEN_HEADER, null);
    }

    protected RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter jacksonMapper = new MappingJackson2HttpMessageConverter();
        jacksonMapper.getObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        restTemplate.getMessageConverters().add(jacksonMapper);
        return restTemplate;
    }

    protected void setSessionCookieFromHeader(List<String> cookieVales) {
        for (String item : cookieVales) {
            if (item.contains(AppConfig.AUTH_TOKEN)) {
                prefEditor.putString(AppConfig.AUTH_TOKEN_HEADER, item).commit();
                Log.d(TAG, "Session: " + item);
                break;
            }
        }
    }
}
