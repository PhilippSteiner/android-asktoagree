package com.steiner_consult.workers;

import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.models.Prayer;
import com.steiner_consult.models.responses.BaseResponse;
import com.steiner_consult.utilities.AppConfig;
import com.steiner_consult.utilities.NetworkURL;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Philipp on 31.01.15.
 */
public class CreatePrayerWorker extends BaseWorker {

    private final String TAG = this.getClass().getName();
    private Prayer prayer;

    public CreatePrayerWorker(BaseActivity baseActivity) {
        super(baseActivity);
    }

    public void createPrayer(Prayer prayer) {
        this.prayer = prayer;
        new CreatePrayerAsyncTask().execute();
    }

    private class CreatePrayerAsyncTask extends AsyncTask<Void, Void, ResponseEntity<BaseResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<BaseResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.PRAYER_CREATE.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(prayer), BaseResponse.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<BaseResponse> responseEntity) {
            if (CancelAndShowToast(responseEntity))
                return;
            BaseResponse baseResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, " Status: " + baseResponse.getStatus());
                issueToastAndCancelDialog(baseResponse.getStatus());
                if (baseResponse.getStatus().equals(AppConfig.OK)) {
                    //TODO: Handle Success

                }

            }


        }
    }
}
