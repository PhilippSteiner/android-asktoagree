package com.steiner_consult.workers;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.LoginActivity;
import com.steiner_consult.asktoagree.MainActivity;
import com.steiner_consult.models.responses.BaseResponse;
import com.steiner_consult.models.responses.LoginResponse;
import com.steiner_consult.utilities.AppConfig;
import com.steiner_consult.utilities.NetworkURL;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Philipp on 10.03.15.
 */
public class LogoutWorker extends BaseWorker {

    private final String TAG = this.getClass().getName();

    public LogoutWorker(BaseActivity baseActivity) {
        super(baseActivity);
    }

    public void logoutUser() {
        new UserLogoutAsyncTask().execute();

    }

    private class UserLogoutAsyncTask extends AsyncTask<Void, Void, ResponseEntity<BaseResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<BaseResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.LOGIN.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.GET, getRequestEntity(null), BaseResponse.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<BaseResponse> responseEntity) {
            BaseResponse baseResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, " Status: " + baseResponse.getStatus());
                issueStatusToast(baseResponse.getStatus());
                if(baseResponse.getStatus().equals(AppConfig.OK)){
                    deleteSessionFromPreferences();
                    baseActivity.startActivity(new Intent(baseActivity.getApplicationContext(), LoginActivity.class));
                }


            }


        }
    }

}
