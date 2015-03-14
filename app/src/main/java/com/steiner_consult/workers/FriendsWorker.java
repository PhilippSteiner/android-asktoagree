package com.steiner_consult.workers;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.fragments.BaseFragment;
import com.steiner_consult.fragments.InviteFragment;
import com.steiner_consult.interfaces.FriendFragment;
import com.steiner_consult.models.AppUser;
import com.steiner_consult.models.requests.ConfirmFriendRequest;
import com.steiner_consult.models.requests.InviteRequest;
import com.steiner_consult.models.responses.BaseResponse;
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

    private FriendFragment friendFragment;
    private InviteRequest inviteRequest;
    private ConfirmFriendRequest confirmFriendRequest;

    public FriendsWorker(FriendFragment friendFragment) {
        super((BaseActivity) ((Fragment) friendFragment).getActivity());
        this.friendFragment = friendFragment;
    }

    public void loadUsers() {
        new GetUsersAsyncTask().execute();
    }

    public void loadFriends() {
        new GetFriendsAsyncTask().execute();
    }

    public void loadRequests() {
        new GetRequestsAsyncTask().execute();
    }

    public void loadResponses() {
        new GetRespondAsyncTask().execute();
    }

    public void sendInvite(AppUser appUser) {
        inviteRequest = new InviteRequest();
        inviteRequest.setFriendId(appUser.getId());
        new InviteAsyncTask().execute();
    }

    public void sendConfirmFriendship(AppUser appUser) {
        confirmFriendRequest = new ConfirmFriendRequest();
        confirmFriendRequest.setFriendId(appUser.getId());
        new ConfirmFriendAsyncTask().execute();
    }

    private class GetUsersAsyncTask extends AsyncTask<Void, Void, ResponseEntity<UsersResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((InviteFragment) friendFragment).getLoadingBar().show();
            //((BaseFragment) friendFragment).getProgressDialog().show();
            //baseActivity.getProgressDialog().show();
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
            if (CancelAndShowToast(responseEntity))
                return;
            UsersResponse usersResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, " Status: " + usersResponse.getStatus());
                //issueStatusToast(usersResponse.getStatus());
                if (usersResponse.getStatus().equals(AppConfig.OK)) {
                    if (usersResponse.getUsers().size() > 0)
                        Log.d(TAG, "Count: " + usersResponse.getUsers().size());
                        friendFragment.setListAdapterData(usersResponse.getUsers());
                }
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");
            }
            //((BaseFragment) friendFragment).getProgressDialog().cancel();
            ((InviteFragment) friendFragment).getLoadingBar().hide();
        }

        public boolean CancelAndShowToast(ResponseEntity response) {
            if (response == null) {
                Toast.makeText(baseActivity, R.string.toast_loading_error, Toast.LENGTH_SHORT).show();
                if(friendFragment != null)
                    //((BaseFragment) friendFragment).getProgressDialog().cancel();
                    ((InviteFragment) friendFragment).getLoadingBar().hide();
                return true;
            }
            return false;
        }
    }

    private class InviteAsyncTask extends AsyncTask<Void, Void, ResponseEntity<BaseResponse>> {

        @Override
        protected ResponseEntity<BaseResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.FRIEND_INVITE.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(inviteRequest), BaseResponse.class);
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
                issueStatusToast(baseResponse.getStatus());
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");
            }
        }
    }

    private class GetRequestsAsyncTask extends AsyncTask<Void, Void, ResponseEntity<UsersResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<UsersResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.FRIEND_REQUEST.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.GET, getRequestEntity(null), UsersResponse.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<UsersResponse> responseEntity) {
            if (CancelAndShowToast(responseEntity))
                return;
            UsersResponse usersResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, " Status: " + usersResponse.getStatus());
                issueStatusToast(usersResponse.getStatus());
                if (usersResponse.getStatus().equals(AppConfig.OK)) {
                    if (usersResponse.getUsers().size() > 0)
                        Log.d(TAG, "Count: " + usersResponse.getUsers().size());
                    friendFragment.setListAdapterData(usersResponse.getUsers());
                }
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");
            }
        }
    }

    private class GetRespondAsyncTask extends AsyncTask<Void, Void, ResponseEntity<UsersResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<UsersResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.FRIEND_RESPONSE.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.GET, getRequestEntity(null), UsersResponse.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<UsersResponse> responseEntity) {
            if (CancelAndShowToast(responseEntity))
                return;
            UsersResponse usersResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, " Status: " + usersResponse.getStatus());
                issueStatusToast(usersResponse.getStatus());
                if (usersResponse.getStatus().equals(AppConfig.OK)) {
                    if (usersResponse.getUsers().size() > 0)
                        Log.d(TAG, "Count: " + usersResponse.getUsers().size());
                    friendFragment.setListAdapterData(usersResponse.getUsers());
                }
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");
            }
        }
    }

    private class ConfirmFriendAsyncTask extends AsyncTask<Void, Void, ResponseEntity<BaseResponse>> {

        @Override
        protected ResponseEntity<BaseResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.FRIEND_CONFIRM.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(confirmFriendRequest), BaseResponse.class);
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
                issueStatusToast(baseResponse.getStatus());
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");
            }
        }
    }

    private class GetFriendsAsyncTask extends AsyncTask<Void, Void, ResponseEntity<UsersResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.getProgressDialog().show();
        }

        @Override
        protected ResponseEntity<UsersResponse> doInBackground(Void... params) {
            try {
                final String url = NetworkURL.FRIENDS_LIST.getUrl();
                Log.d(TAG, "PostRequest to: " + url);
                return getRestTemplate().exchange(url, HttpMethod.GET, getRequestEntity(null), UsersResponse.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<UsersResponse> responseEntity) {
            if (CancelAndShowToast(responseEntity))
                return;
            UsersResponse usersResponse = responseEntity.getBody();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Log.d(TAG, " Status: " + usersResponse.getStatus());
                issueStatusToast(usersResponse.getStatus());
                if (usersResponse.getStatus().equals(AppConfig.OK)) {
                    if (usersResponse.getUsers().size() > 0)
                        Log.d(TAG, "Count: " + usersResponse.getUsers().size());
                    friendFragment.setListAdapterData(usersResponse.getUsers());
                }
            } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d(TAG, "Unauthorized!");
            }
        }
    }
}
