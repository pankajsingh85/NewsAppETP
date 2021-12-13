package com.pankaj.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private final NewsItemClicked listener;
    private Context context;
    private List<News> newsList;


    public NewsListAdapter(Context context, List<News> list,NewsItemClicked listener) {
        this.context = context;
        this.newsList = list;
        this.listener=listener;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 listener.onItemClicked(newsList.get(viewHolder.getAdapterPosition()));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        News news=newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.author.setText(news.getAuthor());
        Glide.with(context).load(news.getUrlToImage()).apply(RequestOptions.centerCropTransform()).into(holder.newsImg);

    }
    @Override
    public int getItemCount() {
        return newsList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,author;
        ImageView newsImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            author=itemView.findViewById(R.id.author);
            newsImg=itemView.findViewById(R.id.image);
        }
    }



public interface NewsItemClicked {
        void onItemClicked(News newsList);
}

}



