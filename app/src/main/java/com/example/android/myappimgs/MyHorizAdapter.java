package com.example.android.myappimgs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myappimgs.dataRoom.Imgs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyHorizAdapter extends RecyclerView.Adapter<MyHorizAdapter.MyAdapterViewHolder>  {

    MyHorizAdapter.MyHorizAdapterOnClickHandler mClickHandler;
    Context mContext;
    ArrayList<String> newData;

    ImageView tv;
    @NonNull
    @Override
    public MyHorizAdapter.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutId = R.layout.horizrecyhold;
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);

        view.setFocusable(true);

        return new MyHorizAdapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {


        Picasso.get()
                .load(newData.get(position)).fit()
                .placeholder(R.mipmap.qq)
                .centerCrop()
                .into(tv);
//tv.setText(newData.get(position));
    }


    public void setData(ArrayList<String> newData) {
     this.newData=newData;
    }


    public MyHorizAdapter(@NonNull Context context, MyHorizAdapter.MyHorizAdapterOnClickHandler clickHandler) {
        mContext = context;

    }

    public interface MyHorizAdapterOnClickHandler {
        void onClick1(String pos);
    }



    @Override
    public int getItemCount() {
       return newData.size();
    }


    class MyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyAdapterViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {
        }
    }

}