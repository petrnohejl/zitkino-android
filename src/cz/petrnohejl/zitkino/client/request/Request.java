package cz.petrnohejl.zitkino.client.request;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonParseException;

import android.os.Bundle;

import cz.petrnohejl.zitkino.client.response.Response;


public abstract class Request
{	
	protected final String BASE_URL = "http://zitkino.cz/";
	
	private Bundle mMetaData = null;
	
	public abstract String getRequestMethod();
	public abstract String getAddress();
	public abstract byte[] getContent();
	public abstract String getBasicAuthUsername();
	public abstract String getBasicAuthPassword();
	public abstract Response parseResponse(InputStream stream) throws IOException, JsonParseException;
	
	public Bundle getMetaData()
	{
		return mMetaData;
	}
	public void setMetaData(Bundle metaData)
	{
		mMetaData = metaData;
	}
}
