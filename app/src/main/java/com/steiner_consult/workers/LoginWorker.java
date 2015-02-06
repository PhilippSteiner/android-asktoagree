package com.steiner_consult.workers;

import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.models.responses.LoginResponse;
import com.steiner_consult.models.responses.RegisterResponse;
import com.steiner_consult.models.requests.LoginRequest;
import com.steiner_consult.utilities.AppConfig;
import com.steiner_consult.utilities.NetworkURL;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Created by Philipp on 05.02.15.
 */
public class LoginWorker extends BaseWorker {

    private final String TAG = this.getClass().getName();
    private BaseActivity baseActivity;
    private LoginRequest loginRequest;

    public LoginWorker(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
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
            final String url = NetworkURL.REGISTERACCOUNT.getUrl();
            Log.d(TAG, "PostRequest to: " + url);
            ResponseEntity<LoginResponse> responseEntity = getRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(loginRequest), LoginResponse.class);
            LoginResponse loginResponse = responseEntity.getBody();
            Log.d(TAG, "User id: " + loginResponse.getId() + " Status: " + loginResponse.getStatus());
            return responseEntity;
        }

        @Override
        protected void onPostExecute(ResponseEntity<LoginResponse> loginResponse) {
            baseActivity.getProgressDialog().cancel();

        }
    }
}
