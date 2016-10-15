package com.longngo.moviebox.wear;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngohoang.along.appcore.common.DynamicHeightImageView;
import com.ngohoang.along.appcore.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.ngohoang.along.appcore.presentation.presentor.main.MainPresentationModel;
import com.ngohoang.along.appcore.presentation.presentor.main.MainPresenter;
import com.ngohoang.along.appcore.presentation.presentor.main.MainView;
import com.ngohoang.along.appcore.presentation.viewmodel.BaseVM;
import com.ngohoang.along.appcore.presentation.viewmodel.MovieVM;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity<MainPresentationModel,MainView,MainPresenter> implements MainView,WearableListView.ClickListener{

    // Sample dataset for the list
    String[] elements = { "List Item 1", "List Item 2", "List Item 3" };
    // Assign an adapter to the list
    Adapter adapter;
    WearableListView wearableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();


        // Get the list component from the layout of the activity
        wearableListView =
                (WearableListView) findViewById(R.id.wearable_list);


        // Set a click listener
        wearableListView.setClickListener(this);
    }

    @Override
    protected void performFieldInjection() {
        WearApplication.getMainComponent().inject(this);
    }

    @NonNull
    @Override
    protected MainPresentationModel createPresentationModel() {
        return new MainPresentationModel();
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);

    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();

    }

    @Override
    public void onExitAmbient() {

        super.onExitAmbient();
    }



    @Override
    protected void onStart() {
        super.onStart();
        presenter.fetchRepository(1);
    }

    @Override
    public void showProcess() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void updateView() {
        if(adapter == null){
            adapter = new Adapter(this, presenter.getPresentationModel());
            wearableListView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(WearableListView.ViewHolder v) {
        Integer tag = (Integer) v.itemView.getTag();
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    private static final class Adapter extends WearableListView.Adapter {
        private List<BaseVM> mDataset;
        private final Context mContext;
        private final LayoutInflater mInflater;
        MainPresentationModel mainPresentationModel;


        public Adapter(Context context, MainPresentationModel presentationModel) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mainPresentationModel =presentationModel;
            mDataset = mainPresentationModel.getVisitableList();
        }

        // Provide a reference to the type of views you're using
        public static class ItemViewHolder extends WearableListView.ViewHolder {
            private TextView textView;
            private DynamicHeightImageView imageView;
            public ItemViewHolder(View itemView) {
                super(itemView);
                // find the text view within the custom item's layout
                textView = (TextView) itemView.findViewById(R.id.name);
                imageView = (DynamicHeightImageView) itemView.findViewById(R.id.circle);
            }
        }

        // Create new views for list items
        // (invoked by the WearableListView's layout manager)
        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
            // Inflate our custom layout for list items
            return new ItemViewHolder(mInflater.inflate(R.layout.layout_item, null));
        }

        // Replace the contents of a list item
        // Instead of creating new views, the list tries to recycle existing ones
        // (invoked by the WearableListView's layout manager)
        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder,
                                     int position) {
            if(mDataset.get(position) instanceof MovieVM){
                // retrieve the text view
                ItemViewHolder itemHolder = (ItemViewHolder) holder;
                MovieVM movieVM = (MovieVM) mDataset.get(position);
                TextView view = itemHolder.textView;
                // replace text contents
                view.setText(movieVM.getMovie().getTitle());
                Picasso.with(mContext).load("https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getBackdropPath())
                        .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(movieVM.getMovie().getId()))
                        .into(itemHolder.imageView);
                // replace list item's metadata
                holder.itemView.setTag(position);
            }

        }

        // Return the size of your dataset
        // (invoked by the WearableListView's layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
