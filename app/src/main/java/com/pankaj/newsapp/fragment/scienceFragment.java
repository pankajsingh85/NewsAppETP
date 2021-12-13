package com.pankaj.newsapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pankaj.newsapp.JSONResponse;
import com.pankaj.newsapp.News;
import com.pankaj.newsapp.NewsListAdapter;
import com.pankaj.newsapp.R;
import com.pankaj.newsapp.api.ApiClient;
import com.pankaj.newsapp.api.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class scienceFragment  extends Fragment implements NewsListAdapter.NewsItemClicked {

    final static String apiKey="00dea44cfeef418fba8e0fdbf6af0ae4";

    View view;
    private RecyclerView recyclerView;
    List<News> newsList;
    ApiInterface apiInterface;
    Context context;
    NewsListAdapter newsListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout, container, false);

        context=container.getContext();
        newsList=new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient("http://newsapi.org/");
        apiInterface = retrofit.create(ApiInterface.class);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        newsListAdapter = new NewsListAdapter(view.getContext(),newsList,this);
        recyclerView.setAdapter(newsListAdapter);
        recyclerView.setHasFixedSize(true);

        getNews();

        return view;
    }

    public void getNews() {
        Call<JSONResponse> call = apiInterface.getNews("in","science", apiKey);
        call.enqueue(new Callback<JSONResponse>() {
                         @Override
                         public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                             if (response.code()!= 200) {
                                 Log.e("network problem","check the connection");
                             }
                             else{
                                 JSONResponse jsonResponse = response.body();
                                 newsList=new ArrayList<>(Arrays.asList(jsonResponse.getArticles()));
                                 newsListAdapter.setNewsList(newsList);
                             }
                         }

                         @Override
                         public void onFailure(Call<JSONResponse> call, Throwable t) {
                             Log.e("error",t.getMessage());
                         }
                     }
        );
    }

    @Override
    public void onItemClicked(News newsList) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(newsList.getUrl()));
    }
}
