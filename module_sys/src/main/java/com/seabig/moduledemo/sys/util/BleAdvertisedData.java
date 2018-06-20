package com.seabig.moduledemo.sys.util;

import java.util.List;
import java.util.UUID;

/**
 * Created by YJZ on 2018/5/21.
 */

public class BleAdvertisedData {

    private List<UUID> mUuids;
    private String mName;
    public BleAdvertisedData(List<UUID> uuids, String name){
        mUuids = uuids;
        mName = name;
    }

    public List<UUID> getUuids(){
        return mUuids;
    }

    public String getName(){
        return mName;
    }

}
