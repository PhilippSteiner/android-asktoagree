package com.steiner_consult.workers;

import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.fragments.HomeFragment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Philipp on 19.03.15.
 */
public class BibelWorker extends BaseWorker {

    private HomeFragment homeFragment;

    public BibelWorker(HomeFragment homeFragment) {
        super((BaseActivity) homeFragment.getActivity());
        this.homeFragment = homeFragment;
    }

    public void loadBibleText() {
        new GetBibleTextAsyncTask().execute();
    }

    private class GetBibleTextAsyncTask extends AsyncTask<Void, Void, ResponseEntity<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<String> doInBackground(Void... params) {
            try {
                final String url = "http://bible2.net/service/today/inline/EnglishStandardVersion";
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().getForEntity(url, String.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<String> responseEntity) {
            if (CancelAndShowToast(responseEntity))
                return;
            String completeHTML = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, "HTML: " + completeHTML);
                homeFragment.setText(removeLinks(completeHTML));
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");
            }
            baseActivity.getProgressDialog().cancel();
        }

        private String removeLinks(String text) {
            String links = text.substring(text.indexOf("<a"), text.lastIndexOf("</a>") + 4);
            Log.d(TAG, links);
            return text.replace(links, "");
        }
    }

}
