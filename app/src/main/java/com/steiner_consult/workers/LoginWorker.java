package com.steiner_consult.workers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.MainActivity;
import com.steiner_consult.models.responses.LoginResponse;
import com.steiner_consult.models.responses.RegisterResponse;
import com.steiner_consult.models.requests.LoginRequest;
import com.steiner_consult.utilities.AppConfig;

import com.steiner_consult.utilities.NetworkURL;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * Created by Philipp on 05.02.15.
 */
public class LoginWorker extends BaseWorker {

    private final String TAG = this.getClass().getName();
    private LoginRequest loginRequest;

    public LoginWorker(BaseActivity baseActivity) {
        super(baseActivity);
    }

    public void loginUser(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
        this.loginRequest.setClienttoken(AppConfig.CLIENT_TOKEN);
        new UserLoginAsyncTask().execute();

    }

    private class UserLoginAsyncTask extends AsyncTask<Void, Void, ResponseEntity<LoginResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<LoginResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.LOGIN.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(loginRequest), LoginResponse.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<LoginResponse> responseEntity) {
            LoginResponse loginResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, "User id: " + loginResponse.getId() + " Status: " + loginResponse.getStatus());
                issueStatusToast(loginResponse.getStatus());
                if (loginResponse.getStatus().equals(AppConfig.LOGIN_SUCCESS)) {
                    setSessionCookieFromHeader(responseEntity.getHeaders().get(AppConfig.RESPONSE_SESSION_COOKIE));
                    Intent intent = new Intent(baseActivity.getApplicationContext(), MainActivity.class);
                    baseActivity.startActivity(intent);
                }

            }


        }
    }
}
