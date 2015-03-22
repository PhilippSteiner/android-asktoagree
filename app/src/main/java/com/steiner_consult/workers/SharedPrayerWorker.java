package com.steiner_consult.workers;

import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.fragments.SharedPrayersFragment;
import com.steiner_consult.models.responses.PrayersResponse;
import com.steiner_consult.utilities.AppConfig;
import com.steiner_consult.utilities.NetworkURL;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Philipp on 22.03.15.
 */
public class SharedPrayerWorker extends BaseWorker {

    private SharedPrayersFragment sharedPrayersFragment;

    public SharedPrayerWorker(SharedPrayersFragment sharedPrayersFragment) {
        super((BaseActivity) sharedPrayersFragment.getActivity());
        this.sharedPrayersFragment = sharedPrayersFragment;
    }

    public void loadSharedPrayers() {
        new SharedPrayersAsyncTask().execute();
    }

    private class SharedPrayersAsyncTask extends AsyncTask<Void, Void, ResponseEntity<PrayersResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<PrayersResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.PRAYER_SHARED_LIST.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.GET, getRequestEntity(null), PrayersResponse.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<PrayersResponse> responseEntity) {
            if (CancelAndShowToast(responseEntity))
                return;
            PrayersResponse prayersResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, " Status: " + prayersResponse.getStatus());
                if (prayersResponse.getStatus().equals(AppConfig.OK)) {
                    sharedPrayersFragment.setData(prayersResponse.getPrayers());
                }
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");
            }
            issueToastAndCancelDialog(prayersResponse.getStatus());
        }
    }
}
