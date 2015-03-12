package com.steiner_consult.workers;

import android.os.AsyncTask;
import android.util.Log;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.fragments.InviteFragment;
import com.steiner_consult.models.responses.UsersResponse;
import com.steiner_consult.utilities.AppConfig;
import com.steiner_consult.utilities.NetworkURL;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Philipp on 12.03.15.
 */
public class FriendsWorker extends BaseWorker {

    private InviteFragment inviteFragment;

    public FriendsWorker(InviteFragment inviteFragment) {
        super((BaseActivity)inviteFragment.getActivity());
        this.inviteFragment = inviteFragment;
    }

    public void loadUsers() {
        new GetUsersAsyncTask().execute();
    }

    private class GetUsersAsyncTask extends AsyncTask<Void, Void, ResponseEntity<UsersResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<UsersResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.USER_LIST.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.GET, getRequestEntity(null), UsersResponse.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<UsersResponse> responseEntity) {
            UsersResponse usersResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, " Status: " + usersResponse.getStatus());
                issueStatusToast(usersResponse.getStatus());
                if (usersResponse.getStatus().equals(AppConfig.OK)) {
                    if (usersResponse.getUsers().size() > 0)
                        Log.d(TAG, "Count: " + usersResponse.getUsers().size());
                        inviteFragment.setData(usersResponse.getUsers());
                }
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");

            }

        }
    }
}
