package com.example.android.myappimgs;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.binder.CompositeViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */
public class CompositeViewRendererFragment extends Fragment {

  //  private YourDataProvider mYourDataProvider = new YourDataProvider();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragmentlist, container, false);

        final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();

        adapter.registerRenderer(
                new CompositeViewBinder<>(
                        R.layout.item_simple_composite,
                        R.id.recycler_view,
                        DefaultCompositeViewModel.class
                        // Collections.singletonList(new BetweenSpacesItemDecoration(10, 10))
                ).registerRenderer(getAnyViewRenderer())
        );
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

        adapter.setItems(Main2Activity.getParentItems());

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        //  recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

           return view;
    }

    private ViewBinder getAnyViewRenderer() {
        return new ViewBinder<>(
                R.layout.item_simple_square,
                ChildItem.class,
                (model, finder, payloads) -> finder.find(R.id.text, (ViewProvider<ImageView>) imageView -> {
                    Glide.with(this).load(model.getTitle()).into(imageView);})
        );
    }




    private List<DefaultCompositeViewModel> getParentItems() {
        ArrayList<DefaultCompositeViewModel> parents = new ArrayList();

        for (int i=0;i<5;i++) {
            ArrayList<ChildItem> children = new ArrayList();
            //Imgs qqw=topass.get(i);
            for (int h=0;h<7;h++) {
                // ChildItem qwq=new ChildItem(qqw.getImgaddr());
                children.add(new ChildItem("kjkjjk"));
            }
            parents.add(new DefaultCompositeViewModel(children));
        }
        return parents;
    }




//         tvm = findViewById(R.id.trip);
//
//
//        adapter = new RendererRecyclerViewAdapter();
//        adapter.registerRenderer(getParentItemViewBinder().registerRenderer(getChildItemViewBinder()));
//        adapter.setItems(getParentItems());
//
////        mRecyclerView = (RecyclerView) findViewById(R.id.myrecy);
////        mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
////        if(topass==null){
}