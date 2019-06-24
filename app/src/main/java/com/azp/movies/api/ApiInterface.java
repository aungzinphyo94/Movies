package com.azp.movies.api;

import com.azp.movies.model.TopRatedMovies;

import retrofit2.Call;
        import retrofit2.http.GET;
        import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<TopRatedMovies> getTopRatedMovies(
            @Query("api_key") String apiKey
    );

}
