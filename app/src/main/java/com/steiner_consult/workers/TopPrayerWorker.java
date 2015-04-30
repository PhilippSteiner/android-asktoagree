package com.steiner_consult.workers;

import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.fragments.TopPrayerFragment;
import com.steiner_consult.models.requests.PagingRequest;
import com.steiner_consult.models.responses.PrayersResponse;
import com.steiner_consult.utilities.AppConfig;
import com.steiner_consult.utilities.NetworkURL;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Philipp on 27.03.15.
 */
public class TopPrayerWorker extends BaseWorker {

    private TopPrayerFragment topPrayerFragment;
    private PagingRequest pagingRequest;

    public TopPrayerWorker(TopPrayerFragment topPrayerFragment) {
        super((BaseActivity) topPrayerFragment.getActivity());
        this.topPrayerFragment = topPrayerFragment;
    }

    public void loadNextPage(int offset) {
        pagingRequest = new PagingRequest();
        pagingRequest.setPage(offset);
        new TopPrayerAsyncTask().execute();
    }

    private class TopPrayerAsyncTask extends AsyncTask<Void, Void, ResponseEntity<PrayersResponse>> {

        @Override
        protected ResponseEntity<PrayersResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.PRAYER_TOP_SHARED.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(pagingRequest), PrayersResponse.class);
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
                Log.d(TAG, " Status: " + prayersResponse.getStatus() + "Count: " + prayersResponse.getPrayers().size());
                if (prayersResponse.getStatus().equals(AppConfig.OK)) {
                    topPrayerFragment.addData(prayersResponse.getPrayers());
                }
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");
            }
            issueToastAndCancelDialog(prayersResponse.getStatus());
        }
    }
}
