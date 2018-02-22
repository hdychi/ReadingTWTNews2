package com.example.administrator.readingtwtnews2.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.readingtwtnews2.Models.NewsBean;
import com.example.administrator.readingtwtnews2.OnItemClickListener;
import com.example.administrator.readingtwtnews2.R;
import com.github.piasy.biv.view.BigImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<IViewHolder>{
    private List<NewsBean.DataBean> mImages;
    private OnItemClickListener<NewsBean.DataBean> mOnItemClickListener;

    public void setCALL_ACTIVITY(int CALL_ACTIVITY) {
        this.CALL_ACTIVITY = CALL_ACTIVITY;
    }

    private int CALL_ACTIVITY = 0;
    public static int TYPE_NOT_FOOTER = 0;
    public static int TYPE_FOOTER = 1;
    public final static int CALL_NEWS = 0;
    public final static int CALL_RECORD = 1;
    private int loadState;
    private Context mContext;
    public static int IS_LOADING = 0;
    public static int NOT_LOADING = 1;
    public  RecyclerAdapter(Context context) {
        mImages = new ArrayList<>();
        loadState = 1;
        this.mContext = context;
    }
    public void setOnItemClickListener(OnItemClickListener listener){
       mOnItemClickListener = listener;
    }

    public void addAll(List<NewsBean.DataBean> images) {
        int positionStart = mImages.size();
        int itemCount = images.size();
        mImages.addAll(images);
        if (positionStart > 0 && itemCount > 0) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else {
            notifyDataSetChanged();
        }
    }
    public boolean contains(NewsBean.DataBean o){
        return mImages.contains(o);
    }

    public void remove(int position) {
        mImages.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(){
        mImages.clear();
        notifyDataSetChanged();
    }

    public NewsBean.DataBean getItem(int powsition){
        return mImages.get(powsition);
    }
    public  void add(NewsBean.DataBean item){

        mImages.add(item);

        notifyDataSetChanged();

    }
    public void addFront(NewsBean.DataBean item){
        mImages.add(0,item);
        notifyDataSetChanged();
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView itemView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        if(viewType==TYPE_NOT_FOOTER) {
            final ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }
        else{
            final FooterViewHolder holder = new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false));
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(IViewHolder holder, int position) {
        View itemLayout = (View) holder.itemView;
        if(holder instanceof  ViewHolder){

            NewsBean.DataBean nowItem = mImages.get(position);
            final TextView title =(TextView)itemLayout.findViewById(R.id.title);
            final  TextView introduct = (TextView)itemLayout.findViewById(R.id.introduct);
            final BigImageView imageView = (BigImageView) itemLayout.findViewById(R.id.bigImage);
            title.setText(nowItem.getSubject());
            introduct.setText(nowItem.getSummary());
            imageView.showImage(Uri.parse(nowItem.getPic()));
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if(mOnItemClickListener!=null){
                      mOnItemClickListener.onItemClick(position,nowItem,v);
                  }
                }
            });
        }
        else{
            FooterViewHolder footerViewHolder = (FooterViewHolder)holder;
            if(CALL_ACTIVITY==CALL_NEWS){
                if(loadState==0){
                    footerViewHolder.getText().setText("加载中");
                    footerViewHolder.getProgressBar().setVisibility(View.VISIBLE);
                }
                else{
                    footerViewHolder.getText().setText("查看更多");
                    footerViewHolder.getProgressBar().setVisibility(View.GONE);
                }
            }
            else{
                footerViewHolder.getText().setText("没有更多了");
                footerViewHolder.getProgressBar().setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mImages.size()+1;
    }
    @Override
    public int getItemViewType(int position){
        if(position+1==getItemCount()){
            return TYPE_FOOTER;
        }
        else{
            return  TYPE_NOT_FOOTER;
        }
    }
    public void setLoadState(int state){
        this.loadState = state;
    }

    static class ViewHolder extends IViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    static class FooterViewHolder extends IViewHolder {
        private TextView footerView;
        private ProgressBar progressBar;
        public FooterViewHolder(View view){
            super(view);
            footerView = (TextView)view.findViewById(R.id.footerText);
            progressBar = (ProgressBar)view.findViewById(R.id.pulldown_footer_loading);
        }
        public TextView getText(){
            return  this.footerView;
        }
        public ProgressBar getProgressBar(){
            return  this.progressBar;
        }
    }

}
