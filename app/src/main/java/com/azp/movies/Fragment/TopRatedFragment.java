package com.azp.movies.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azp.movies.R;
import com.azp.movies.adapter.TopRatedAdapter;
import com.azp.movies.api.ApiInterface;
import com.azp.movies.model.TopRatedItem;
import com.azp.movies.model.TopRatedMovies;
import com.azp.movies.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private final String API_KEY = "a7637e9a9af949021b898a07c5c9bce5";
    private RecyclerView recyclerView;
    //    private RecyclerView.LayoutManager layoutManager;
    private List<TopRatedItem> topRatedItems = new ArrayList<>();
    private TopRatedAdapter topRatedAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView topHeadline;

    public TopRatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        OnLoadingSwipeRefresh("");

        topHeadline = rootView.findViewById(R.id.headline);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Top Rated Movies");
    }

    @Override
    public void onRefresh() {

    }

    public void LoadCategories(final String keyword) {
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface = ApiUtils.getAPI();

        Call<TopRatedMovies> topRatedMoviesCall = apiInterface.getTopRatedMovies(API_KEY);

        Log.d("movies", topRatedItems.size()+"");

        topRatedMoviesCall.enqueue(new Callback<TopRatedMovies>() {
            @Override
            public void onResponse(Call<TopRatedMovies> call, Response<TopRatedMovies> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!topRatedItems.isEmpty()) {
                        topRatedItems.clear();
                    }

                    topRatedItems = response.body().getTopRatedItems();

                    TopRatedAdapter.OnItemClickListener listener = (view, position) -> {

                    };

                    topRatedAdapter = new TopRatedAdapter(topRatedItems, listener);
                    recyclerView.setAdapter(topRatedAdapter);
                    topRatedAdapter.notifyDataSetChanged();

                    topHeadline.setVisibility(View.VISIBLE);
                    topHeadline.setText("Top Movies");
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<TopRatedMovies> call, Throwable t) {

                topHeadline.setVisibility(View.VISIBLE);
                topHeadline.setText("Unable to load the movies" + t.toString());
            }
        });
    }

    private void OnLoadingSwipeRefresh(final String keyword) {

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadCategories(keyword);
                    }
                }
        );
    }
}
