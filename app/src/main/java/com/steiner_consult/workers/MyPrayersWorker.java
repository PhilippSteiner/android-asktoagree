package com.steiner_consult.workers;

import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.fragments.MyPrayerPagerFragment;
import com.steiner_consult.models.responses.PrayersResponse;
import com.steiner_consult.utilities.AppConfig;
import com.steiner_consult.utilities.NetworkURL;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Philipp on 07.03.15.
 */
public class MyPrayersWorker extends BaseWorker {

    private final String TAG = this.getClass().getName();
    private MyPrayerPagerFragment myPrayerPagerFragment;

    public MyPrayersWorker(MyPrayerPagerFragment myPrayerPagerFragment) {
        super((BaseActivity) myPrayerPagerFragment.getActivity());
        myPrayerPagerFragment = myPrayerPagerFragment;
    }

    public void loadPrayers() {
        new MyPrayersAsyncTask().execute();
    }


    private class MyPrayersAsyncTask extends AsyncTask<Void, Void, ResponseEntity<PrayersResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<PrayersResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.PRAYER_LIST.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.GET, getRequestEntity(null), PrayersResponse.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<PrayersResponse> responseEntity) {
            PrayersResponse prayersResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, " Status: " + prayersResponse.getStatus());
                issueStatusToast(prayersResponse.getStatus());
                if (prayersResponse.getStatus().equals(AppConfig.OK)) {
                    myPrayerPagerFragment.setData(prayersResponse.getPrayers());
                }
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");

            }

        }
    }
}
