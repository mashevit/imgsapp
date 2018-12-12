package com.example.android.myappimgs;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImgModel {

    @SerializedName("sight")
    @Expose
    private String sight;
    @SerializedName("imgaddr")
    @Expose
    private Object imgaddr;
    @SerializedName("trip")
    @Expose
    private String trip;
    @SerializedName("addr")
    @Expose
    private List<String> addr = null;
    @SerializedName("sightname")
    @Expose
    private String sightname;

    public String getSight() {
        return sight;
    }

    public void setSight(String sight) {
        this.sight = sight;
    }

    public Object getImgaddr() {
        return imgaddr;
    }

    public void setImgaddr(Object imgaddr) {
        this.imgaddr = imgaddr;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public List<String> getAddr() {
        return addr;
    }

    public void setAddr(List<String> addr) {
        this.addr = addr;
    }

    public String getSightname() {
        return sightname;
    }

    public void setSightname(String sightname) {
        this.sightname = sightname;
    }

}