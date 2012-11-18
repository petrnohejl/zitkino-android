package cz.petrnohejl.zitkino.client.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;

import cz.petrnohejl.zitkino.client.entity.Movie;
import cz.petrnohejl.zitkino.client.response.MovieResponse;
import cz.petrnohejl.zitkino.utility.DateConvertor;


public class MovieParser
{
	public static final String GROUP_DATE_FORMAT = "EEEE dd.MM.";
	
	
	public static MovieResponse parse(InputStream stream) throws IOException, JsonParseException
	{
		MovieResponse response = null;
		
		
		// init parser
		JsonFactory factory = new JsonFactory();
		org.codehaus.jackson.JsonParser parser = null;
		parser = factory.createJsonParser(stream);

		
		// parse JSON
		if(parser.nextToken() == JsonToken.START_OBJECT)
		while(parser.nextToken() != JsonToken.END_OBJECT)
		{
			if(parser.getCurrentName().equals("data"))
			{
				ArrayList<String> groups = new ArrayList<String>();
				ArrayList<ArrayList<Movie>> movies = new ArrayList<ArrayList<Movie>>();
				String todayName = DateConvertor.dateToString(new Date(), GROUP_DATE_FORMAT);
				
				if(parser.nextToken() == JsonToken.START_ARRAY)
				while(parser.nextToken() != JsonToken.END_ARRAY)
				{
					String title = null;
					String hash = null;
					String cinema = null;
					Date date = null;
					
					if(parser.getCurrentToken() == JsonToken.START_OBJECT)
					while(parser.nextToken() != JsonToken.END_OBJECT)
					{
						if(parser.getCurrentName().equals("title"))
						{
							if(parser.getCurrentToken() == JsonToken.VALUE_STRING) title=parser.getText();
						}
						else if(parser.getCurrentName().equals("hash"))
						{
							if(parser.getCurrentToken() == JsonToken.VALUE_STRING) hash=parser.getText();
						}
						else if(parser.getCurrentName().equals("cinema"))
						{
							if(parser.getCurrentToken() == JsonToken.VALUE_STRING) cinema=parser.getText();
						}
						else if(parser.getCurrentName().equals("date"))
						{
							if(parser.getCurrentToken() == JsonToken.VALUE_STRING) 
							{
								date = DateConvertor.stringToDate(parser.getText(), "yyyy-MM-dd");
							}
						}
					}
					
					// unknown date
					if(date==null) continue;
					
					// movie object
					Movie movie = new Movie();
					movie.setTitle(title);
					movie.setHash(hash);
					movie.setCinema(cinema);
					movie.setDate(date);
					
					// group
					String groupName = DateConvertor.dateToString(date, GROUP_DATE_FORMAT);
					if(groupName.equals(todayName)) groupName = "dnes";
					int groupIndex = groups.indexOf(groupName);
					
					if(groupIndex==-1)
					{
						// add new group name
						groups.add(groupName); 
						
						// add new movie group
						ArrayList<Movie> movieGroup = new ArrayList<Movie>();
						movieGroup.add(movie);
						movies.add(movieGroup);
					}
					else
					{
						// add to existing group
						movies.get(groupIndex).add(movie);
					}
				}

				response = new MovieResponse();
				response.setGroups(groups);
				response.setMovies(movies);
			}
		}
		

		// close parser
		if(parser!=null) parser.close();
		return response;
	}
}
