package com.azp.movies.utils;

import com.azp.movies.api.ApiClient;
import com.azp.movies.api.ApiInterface;

public class ApiUtils {

    public ApiUtils() {
    }

    public static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w342";

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static ApiInterface getAPI() {
        return ApiClient.getApiClient(BASE_URL).create(ApiInterface.class);
    }

    public static String getBasePosterPath(String posterPath) {
        return BASE_POSTER_PATH + posterPath;
    }
}
