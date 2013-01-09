package cz.zitkino.client.entity;

import java.util.Date;


public class Movie
{
	private String mTitle;
	private String mHash;
	private String mCinema;
	private Date mDate;


	// empty constructor
	public Movie()
	{
	
	}
	
	
	// copy constructor
	public Movie(Movie copyModel)
	{
		if(copyModel.mTitle!=null) mTitle = new String(copyModel.mTitle);
		if(copyModel.mHash!=null) mHash = new String(copyModel.mHash);
		if(copyModel.mCinema!=null) mCinema = new String(copyModel.mCinema);
		if(copyModel.mDate!=null) mDate = new Date(copyModel.mDate.getTime());
	}


	public String getTitle()
	{
		return mTitle;
	}
	public void setTitle(String title)
	{
		mTitle = title;
	}
	public String getHash()
	{
		return mHash;
	}
	public void setHash(String hash)
	{
		mHash = hash;
	}
	public String getCinema()
	{
		return mCinema;
	}
	public void setCinema(String cinema)
	{
		mCinema = cinema;
	}
	public Date getDate()
	{
		return mDate;
	}
	public void setDate(Date date)
	{
		mDate = date;
	}
}
