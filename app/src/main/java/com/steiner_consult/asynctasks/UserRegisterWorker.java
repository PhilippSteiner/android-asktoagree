package com.steiner_consult.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.models.RegisterResponse;
import com.steiner_consult.models.User;


import org.apache.http.HttpRequest;
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

    private static final String TAG = "UserRegisterWorker";


    public void createUser() {
        new UserRegisterAsyncTask().execute();
    }


    private class UserRegisterAsyncTask extends AsyncTask<Void, Void, ResponseEntity<RegisterResponse>> {

        User user = new User();
        String url = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            user.setEmail("philipp.steiner@snw.at");
            user.setFirstname("Philipp");
            user.setLastname("Steiner");
            user.setPassword("password");

        }

        @Override
        protected ResponseEntity<RegisterResponse> doInBackground(Void... params) {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(new MediaType("application", "json"));
            HttpEntity<User> requestEntity = new HttpEntity<User>(user, httpHeaders);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ResponseEntity<RegisterResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, RegisterResponse.class);

            RegisterResponse registerResponse = responseEntity.getBody();

            Log.d(TAG, "User id: " + registerResponse.getId());

            return responseEntity;
        }

        @Override
        protected void onPostExecute(ResponseEntity<RegisterResponse> registerResponse) {


        }
    }
}
