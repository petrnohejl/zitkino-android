package cz.zitkino.client;

import cz.zitkino.client.response.Response;


public interface OnApiCallListener
{
	public void onApiCallRespond(ApiCall call, ResponseStatus status, Response response);
	public void onApiCallFail(ApiCall call, ResponseStatus status, boolean parseFail);
}
