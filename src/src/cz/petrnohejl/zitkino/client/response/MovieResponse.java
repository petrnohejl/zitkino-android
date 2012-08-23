package cz.petrnohejl.zitkino.client.response;

import java.util.ArrayList;

import cz.petrnohejl.zitkino.client.entity.Movie;


public class MovieResponse extends Response
{
	private ArrayList<String> mGroups;
	private ArrayList<ArrayList<Movie>> mMovies;
	
	
	public MovieResponse()
	{

	}


	public ArrayList<String> getGroups()
	{
		return mGroups;
	}
	public void setGroups(ArrayList<String> groups)
	{
		mGroups = groups;
	}
	public ArrayList<ArrayList<Movie>> getMovies()
	{
		return mMovies;
	}
	public void setMovies(ArrayList<ArrayList<Movie>> movies)
	{
		mMovies = movies;
	}
}
