package com.steiner_consult.workers;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.models.RegisterRequest;
import com.steiner_consult.models.RegisterResponse;
import com.steiner_consult.utilities.NetworkURL;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Philipp on 11.12.14.
 */
public class UserRegisterWorker {

    private final String TAG = this.getClass().getName();
    private BaseActivity baseActivity;
    private RegisterRequest registerRequest;


    public UserRegisterWorker(BaseActivity activity) {
        this.baseActivity = activity;
    }


    public void createUser(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
        new UserRegisterAsyncTask().execute();
    }

    private RegisterRequest buildRequestData() {
        return null;
    }

    private class UserRegisterAsyncTask extends AsyncTask<Void, Void, ResponseEntity<RegisterResponse>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();

        }

        @Override
        protected ResponseEntity<RegisterResponse> doInBackground(Void... params) {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(new MediaType("application", "json"));
            HttpEntity<RegisterRequest> requestEntity = new HttpEntity<RegisterRequest>(registerRequest, httpHeaders);

            RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter jacksonMapper = new MappingJackson2HttpMessageConverter();
            jacksonMapper.getObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            restTemplate.getMessageConverters().add(jacksonMapper);

            final String url = NetworkURL.REGISTERACCOUNT.getUrl();
            //final String url = "https://asktoagree.herokuapp.com/account/register/";
            Log.d(TAG, "PostRequest to: " + url);
            ResponseEntity<RegisterResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, RegisterResponse.class);

            RegisterResponse registerResponse = responseEntity.getBody();

            Log.d(TAG, "User id: " + registerResponse.getId() + " Status: " + registerResponse.getStatus());

            return responseEntity;
        }

        @Override
        protected void onPostExecute(ResponseEntity<RegisterResponse> registerResponse) {
            baseActivity.getProgressDialog().cancel();

        }
    }
}
