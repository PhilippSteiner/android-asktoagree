package com.steiner_consult.workers;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.MainActivity;
import com.steiner_consult.models.requests.RegisterRequest;
import com.steiner_consult.models.responses.RegisterResponse;
import com.steiner_consult.utilities.AppConfig;
import com.steiner_consult.utilities.NetworkURL;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Philipp on 11.12.14.
 */
public class UserRegisterWorker extends BaseWorker {

    private final String TAG = this.getClass().getName();

    private RegisterRequest registerRequest;


    public UserRegisterWorker(BaseActivity baseActivity) {
        super(baseActivity);
    }

    public void createUser(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
        this.registerRequest.setClienttoken(AppConfig.CLIENT_TOKEN);
        new UserRegisterAsyncTask().execute();
    }

    private class UserRegisterAsyncTask extends AsyncTask<Void, Void, ResponseEntity<RegisterResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<RegisterResponse> doInBackground(Void... params) {
            final String url = NetworkURL.REGISTER_ACCOUNT.getUrl();
            Log.d(TAG, "PostRequest to: " + url);
            return getRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(registerRequest), RegisterResponse.class);


        }

        @Override
        protected void onPostExecute(ResponseEntity<RegisterResponse> responseEntity) {
            RegisterResponse registerResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, "User id: " + registerResponse.getId() + " Status: " + registerResponse.getStatus() + "Created: " + registerResponse.getCreationDate().toString());
                issueStatusToast(registerResponse.getStatus());
                if (registerResponse.getStatus().equals(AppConfig.LOGIN_SUCCESS)) {
                    //TODO: success
                }

            }
            baseActivity.getProgressDialog().cancel();

        }
    }
}
