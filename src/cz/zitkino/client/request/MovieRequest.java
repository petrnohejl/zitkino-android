package cz.zitkino.client.request;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonParseException;

import cz.zitkino.client.parser.MovieParser;
import cz.zitkino.client.response.Response;
import cz.zitkino.utility.Logcat;


public class MovieRequest extends Request
{
	private final String REQUEST_METHOD = "GET";
	private final String REQUEST_URL = "zitkino.json";
		

	public MovieRequest()
	{

	}

	
	@Override
	public String getRequestMethod()
	{
		return REQUEST_METHOD;
	}


	@Override
	public String getAddress()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(BASE_URL);
		builder.append(REQUEST_URL);
		
		Logcat.d("ZITKINO", builder.toString());
		return builder.toString();
	}


	@Override
	public byte[] getContent()
	{
		return null;
	}


	@Override
	public String getBasicAuthUsername()
	{
		return null;
	}


	@Override
	public String getBasicAuthPassword()
	{
		return null;
	}


	@Override
	public Response parseResponse(InputStream stream) throws IOException, JsonParseException
	{
		return MovieParser.parse(stream);
	}
}
