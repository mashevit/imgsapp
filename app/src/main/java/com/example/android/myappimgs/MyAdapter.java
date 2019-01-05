package com.example.android.myappimgs;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myappimgs.Imgs.ImgsViewModelFactory;
import com.example.android.myappimgs.Imgs.ImgssssViewModel;
import com.example.android.myappimgs.dataRoom.Imgs;
import com.example.android.myappimgs.dataRoom.ImgsRoomDB;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> implements MyHorizAdapter.MyHorizAdapterOnClickHandler, LifecycleObserver {
    private RecyclerView mRecyclerView;
    private MyHorizAdapter myAdapter;
    private TextView textView;
 //   private TextView textViewmain;
    MyAdapterOnClickHandler mClickHandler;
    Context mContext;
    LifecycleOwner lifecycleOwner;

    RecyclerView.RecycledViewPool viewPool;
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

        textView.setText(data.get(position).getSight());

        /*Custom*/LinearLayoutManager layoutManager =
                new /*Custom*/LinearLayoutManager(mContext, HORIZONTAL, false);
        //  mRecyclerView = (RecyclerView) view.findViewById(R.id.myrecy2);

        DividerItemDecoration decoration = new DividerItemDecoration(mContext, HORIZONTAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(layoutManager);


        mRecyclerView.setHasFixedSize(false);



        myAdapter = new MyHorizAdapter(mContext);
       // myAdapter.setData(new ArrayList<String>(){{add("blastring");}});
        myAdapter.setData(data.get(position).getListaaddr());
        //holder.innerRecyclerView.setRecycledViewPool(viewPool);
        mRecyclerView.setAdapter(myAdapter);
    //    mRecyclerView.setVisibility(View.GONE);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /* Setting the adapter attaches it to the RecyclerView in our layout. */





                myAdapter.setData(data.get(position).getListaaddr()); mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
//        ImgsViewModelFactory factory = new ImgsViewModelFactory(mDb, data.get(position));
//        // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
//        // for that use the factory created above AddTaskViewModel
////        ImgssssViewModel viewModel
////                = factory.create(ImgssssViewModel.class);
//       /* final */ImgssssViewModel viewModel
//                = ViewModelProviders.of((FragmentActivity) mContext, factory).get(ImgssssViewModel.class);
//
//
///*        final AddIngrediViewModel viewModel
//                = ViewModelProviders.of(this, factory).*/
//        // COMPLETED (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
//        viewModel.getDish().observe(lifecycleOwner, new Observer<List<Imgs>>() {
//            @Override
//            public void onChanged(@Nullable List<Imgs> imgs) {
//                ArrayList<String> topass=new ArrayList<String>();
//                for(int i=0;i<imgs.size();i++){ topass.addAll(imgs.get(i).getListaaddr());}
//                Log.d(TAG + "fddff5dfdfd", "Updating list of tasks from LiveData in ViewModel" + topass);
//
//                myAdapter.setData(topass);
//            }
//
//
//
//
//
//        });


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
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        lifecycleOwner.getLifecycle().removeObserver(this);
    }

    public MyAdapter(@NonNull Context context,/* MyAdapterOnClickHandler clickHandler,*/ LifecycleOwner lifecycleOwner) {
        mContext = context;
        //mClickHandler = clickHandler;
        this.data = new ArrayList<>();
        this.lifecycleOwner =lifecycleOwner;
        this.lifecycleOwner.getLifecycle().addObserver(this);
        mDb=ImgsRoomDB.getDatabase(mContext.getApplicationContext());
        viewPool = new RecyclerView.RecycledViewPool();
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
//        if (data != null) {
//            ImgDiffCallback postDiffCallback = new ImgDiffCallback(data, newData);
//            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallback);
//
//            data.clear();
//            data.addAll(newData);
//            diffResult.dispatchUpdatesTo(this);
//        } else {
            // first initialization
            data = newData;
       // }
    }

    class MyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyAdapterViewHolder(View itemView) {
            super(itemView);
       // textViewmain=itemView.findViewById(R.id.)


            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.myrecy2);
            mRecyclerView.setRecycledViewPool(viewPool);
            textView=itemView.findViewById(R.id.Sight);

        }

        @Override
        public void onClick(View v) {
        }
    }


    class ImgDiffCallback extends DiffUtil.Callback {

        private final List<String> oldPosts, newPosts;

        public ImgDiffCallback(List<String> oldPosts, List<String> newPosts) {
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
            return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));//.getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
        }
    }



}
