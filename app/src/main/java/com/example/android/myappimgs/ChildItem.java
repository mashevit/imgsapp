package com.example.android.myappimgs;

import android.graphics.Bitmap;

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

public class ChildItem implements ViewModel {
    public String getSight() {
        return sight;
    }

    public ChildItem(String imgeadress, String sight) {
        this.imgeadress = imgeadress;
        this.sight = sight;
    }

    public void setSight(String sight) {
        this.sight = sight;
    }

    private String imgeadress="hhhhg";
    private String sight="sdsd";
    public ChildItem(String a){
        this.imgeadress=a;
    }
    String getTitle() {return imgeadress;} /* your method */
    int getTitleColor() {return 0;} /* your method */
   // Bitmap getImage() {...} /* your method */
   void setTitle(String a) {this.imgeadress=a;}
}