package com.azp.movies.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TopRatedMovies{

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<TopRatedItem> topRatedItems;

	@SerializedName("total_results")
	private int totalResults;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<TopRatedItem> results){
		this.topRatedItems = results;
	}

	public List<TopRatedItem> getTopRatedItems(){
		return topRatedItems;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}
}