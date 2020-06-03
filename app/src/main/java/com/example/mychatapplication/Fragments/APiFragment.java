package com.example.mychatapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mychatapplication.Adapter.Adapter;
import com.example.mychatapplication.Model.Article;
import com.example.mychatapplication.Model.News;
import com.example.mychatapplication.R;
import com.example.mychatapplication.Utils;
import com.example.mychatapplication.api.ApiClient;
import com.example.mychatapplication.api.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    EditText srch;
    public static final String API_KEY="fbdde4638ef6426d85c3e77e96959eed";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articleList=new ArrayList<>();
    private Adapter adapter;
    ImageView a,b;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String TAG=APiFragment.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_a_pi, container, false);
        srch=view.findViewById(R.id.search);
        a=view.findViewById(R.id.imageButton2);
        b=view.findViewById(R.id.imageButton3);
        swipeRefreshLayout=view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        recyclerView=view.findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        onLoadingSwipeRefresh("");
        return view;
    }
    public void search(View view){
        String kword=srch.getText().toString();
        loadJson(kword);
    }
    public void loadJson(String keyword){
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        String country= Utils.getCountry();
        String language=Utils.getLanguage();
        Call<News> call;
        if(keyword.length()>0){
            call=apiInterface.getNewsSearch(keyword,language,"publishedAt",API_KEY);
        }
        else{
            call=apiInterface.getNews(country,API_KEY);
        }
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful()&&response.body().getArticles()!=null){
                    if(!articleList.isEmpty()){
                        articleList.clear();
                    }
                    articleList=response.body().getArticles();
                    adapter=new Adapter(articleList,getActivity());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(),"No Result",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        loadJson("");
    }
    private void onLoadingSwipeRefresh(String keyword){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadJson("");
            }
        });
    }
}