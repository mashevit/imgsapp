package com.example.android.myappimgs;

import android.graphics.Bitmap;

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

public class ChildItem implements ViewModel {
    private String imgeadress="hhhhg";
    public ChildItem(String a){
        this.imgeadress=a;
    }
    String getTitle() {return imgeadress;} /* your method */
    int getTitleColor() {return 0;} /* your method */
   // Bitmap getImage() {...} /* your method */
   void setTitle(String a) {this.imgeadress=a;}
}