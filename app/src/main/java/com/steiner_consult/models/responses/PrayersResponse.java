package com.steiner_consult.models.responses;

import com.steiner_consult.models.Prayer;

import java.util.ArrayList;

/**
 * Created by Philipp on 07.03.15.
 */
public class PrayersResponse extends BaseResponse {

    private ArrayList<Prayer> prayers = new ArrayList<>();

    public ArrayList<Prayer> getPrayers() {
        return prayers;
    }

    public void setPrayers(ArrayList<Prayer> prayers) {
        this.prayers = prayers;
    }
}
