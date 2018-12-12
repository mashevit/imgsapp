package com.example.android.myappimgs;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myappimgs.dataRoom.Imgs;
import com.example.android.myappimgs.dataRoom.ImgsRoomDB;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> implements MyHorizAdapter.MyHorizAdapterOnClickHandler{
    private RecyclerView mRecyclerView;
    private MyHorizAdapter myAdapter;
    private TextView textView;
 //   private TextView textViewmain;
    MyAdapterOnClickHandler mClickHandler;
    Context mContext;
   // private Cursor mCursor;
    private List<Imgs> data;
    private final String TAG = MainActivity.class.getSimpleName();
    private ImgsRoomDB mDb;
    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutId = R.layout.mainrecyhold;
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);

        view.setFocusable(true);

        return new MyAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyAdapterViewHolder holder, final int position) {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);

        /* setLayoutManager associates the LayoutManager we created above with our RecyclerView */

        textView.setText(data.get(position).getSight() );      //mCursor.getString(MainActivity.INDEX_SIGHT_MAIN));
        DividerItemDecoration decoration = new DividerItemDecoration(mContext, HORIZONTAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        /*
         * The ForecastAdapter is responsible for linking our weather data with the Views that
         * will end up displaying our weather data.
         *
         * Although passing in "this" twice may seem strange, it is actually a sign of separation
         * of concerns, which is best programming practice. The ForecastAdapter requires an
         * Android Context (which all Activities are) as well as an onClickHandler. Since our
         * MainActivity implements the ForecastAdapter ForecastOnClickHandler interface, "this"
         * is also an instance of that type of handler.
         */
        myAdapter = new MyHorizAdapter(mContext, this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setData(data.get(position).getListaaddr());

     //   ImgsViewModelFactory factory = new ImgsViewModelFactory(mDb, "mTaskId");
        // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
        // for that use the factory created above AddTaskViewModel
/*

        private void subscribeToModel() {
            final DatabaseCreator databaseCreator = DatabaseCreator.getInstance();
            LiveData<List<Item>> itemLiveData = databaseCreator.getDatabase().itemDao().getAllLiveData();
            if(itemLiveData != null) {
                itemLiveData.observe(this, new Observer<List<Item>>() {
                    @Override
                    public void onChanged(@Nullable List<Item> items) {
                        // Do something useful
                    }
                });
            }
        }

*/




/*
        final ImgssssViewModel viewModel
                = ViewModelProviders.of(this, factory).get(ImgssssViewModel.class);
        ImgssssViewModel imgViewModel=ViewModelProviders.of(this).get(ImgViewModel.class);
        imgViewModel.getAllQuestions().observe(this, new Observer<List<Imgs>>() {
            @Override
            public void onChanged(@Nullable List<Imgs> taskEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                myAdapter.setData(taskEntries);
            }
        });*/



    }


    public MyAdapter(@NonNull Context context, MyAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
        this.data = new ArrayList<>();

    }

    @Override
    public void onClick1(String pos) {

    }

    public interface MyAdapterOnClickHandler {
        void onClick(String pos);
    }
    void swapCursor(Cursor newCursor) {
    //    mCursor = newCursor;
        notifyDataSetChanged();
    }
   public  void onClick(String pos){

}

    @Override
    public int getItemCount() {//todo:
        return data.size();

    //    if (null == mCursor) return 0;
     //   return mCursor.getCount();//return 8;//mDish.size();
    }



    public void setData(List<Imgs> newData) {
   //     data=newData;
        if (data != null) {
            ImgDiffCallback postDiffCallback = new ImgDiffCallback(data, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallback);

            data.clear();
            data.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        } else {
            // first initialization
            data = newData;
        }
    }

    class MyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyAdapterViewHolder(View itemView) {
            super(itemView);
       // textViewmain=itemView.findViewById(R.id.)


            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.myrecy2);
            textView=itemView.findViewById(R.id.Sight);
            mDb=ImgsRoomDB.getDatabase(mContext.getApplicationContext());
        }

        @Override
        public void onClick(View v) {
        }
    }


    class ImgDiffCallback extends DiffUtil.Callback {

        private final List<Imgs> oldPosts, newPosts;

        public ImgDiffCallback(List<Imgs> oldPosts, List<Imgs> newPosts) {
            this.oldPosts = oldPosts;
            this.newPosts = newPosts;
        }

        @Override
        public int getOldListSize() {
            return oldPosts.size();
        }

        @Override
        public int getNewListSize() {
            return newPosts.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).getId() == newPosts.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
        }
    }



}
