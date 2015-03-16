package com.steiner_consult.workers;



import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.fragments.BaseFragment;
import com.steiner_consult.utilities.AppConfig;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    protected Context context;

    protected BaseWorker(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        this.context = baseActivity.getApplicationContext();
        sharedPreferences = context.getSharedPreferences(AppConfig.PREFS_NAMESPACE, Context.MODE_PRIVATE);
        prefEditor = sharedPreferences.edit();
    }

    protected HttpEntity<?> getRequestEntity(Object obj) {
        String sessionCookie = getSessionCookie();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAcceptEncoding(ContentCodingType.GZIP);
        httpHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        httpHeaders.setContentType(new MediaType("application", "json"));

        if (sessionCookie != null) {
            Log.d(TAG, "Session: " + sessionCookie);
            httpHeaders.set(AppConfig.AUTH_TOKEN_HEADER, sessionCookie);
        }
        if (obj != null) {
            return new HttpEntity<>(obj, httpHeaders);
        }
        return new HttpEntity<>(httpHeaders);
    }

    private String getSessionCookie() {
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
                String token = item.substring(item.indexOf("=") + 1, item.indexOf(";"));
                prefEditor.putString(AppConfig.AUTH_TOKEN_HEADER, token).commit();
                Log.d(TAG, "Session: " + token);
                break;
            }
        }
    }

    protected void deleteSessionFromPreferences() {
        prefEditor.putString(AppConfig.AUTH_TOKEN_HEADER, null).apply();
    }

    protected void issueToastAndCancelDialog(String status) {
        cancelProgressDialog();
        issueToast(status);
    }

    protected void issueToastAndCancelDialog(String status, BaseFragment baseFragment) {
        cancelLoadingBar(baseFragment);
        issueToast(status);
    }

    private void issueToast(String status) {
        switch (status) {
            case AppConfig.WRONG_PASSWORD:
                Toast.makeText(baseActivity, R.string.toast_wrong_password, Toast.LENGTH_LONG).show();
                break;
            case AppConfig.USER_NOT_EXIST:
                Toast.makeText(baseActivity, R.string.toast_user_not_exist, Toast.LENGTH_LONG).show();
                break;
            case AppConfig.ACCOUNT_CREATED:
                Toast.makeText(baseActivity, R.string.toast_account_created, Toast.LENGTH_LONG).show();
                break;
            case AppConfig.LOGIN_SUCCESS:
                Toast.makeText(baseActivity, R.string.toast_login_success, Toast.LENGTH_LONG).show();
                break;
            case AppConfig.OK:
                Toast.makeText(baseActivity, R.string.toast_ok, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public boolean CancelAndShowToast(ResponseEntity response) {
        if (response == null) {
            Toast.makeText(baseActivity, R.string.toast_loading_error, Toast.LENGTH_SHORT).show();
            if(baseActivity != null)
                baseActivity.getProgressDialog().cancel();
            return true;
        }
        return false;
    }

    public boolean CancelAndShowToast(ResponseEntity response, BaseFragment baseFragment) {
        if (response == null) {
            Toast.makeText(baseActivity, R.string.toast_loading_error, Toast.LENGTH_SHORT).show();
            if(baseActivity != null)
                cancelLoadingBar(baseFragment);
            return true;
        }
        return false;
    }

    private void cancelLoadingBar(BaseFragment baseFragment) {
        baseFragment.getLoadingBar().setVisibility(View.GONE);
    }

    private void cancelProgressDialog() {
        baseActivity.getProgressDialog().cancel();
    }
}
