package com.steiner_consult.models.requests;

/**
 * Created by Philipp on 12.03.15.
 */
public class InviteRequest {

    private long friendId;

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }
}
