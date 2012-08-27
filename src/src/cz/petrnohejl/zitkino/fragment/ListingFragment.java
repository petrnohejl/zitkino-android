package cz.petrnohejl.zitkino.fragment;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import cz.petrnohejl.zitkino.R;
import cz.petrnohejl.zitkino.adapter.ListingAdapter;
import cz.petrnohejl.zitkino.client.ApiCall;
import cz.petrnohejl.zitkino.client.OnApiCallListener;
import cz.petrnohejl.zitkino.client.RequestManager;
import cz.petrnohejl.zitkino.client.ResponseStatus;
import cz.petrnohejl.zitkino.client.entity.Movie;
import cz.petrnohejl.zitkino.client.request.MovieRequest;
import cz.petrnohejl.zitkino.client.response.MovieResponse;
import cz.petrnohejl.zitkino.client.response.Response;
import cz.petrnohejl.zitkino.task.TaskSherlockFragment;
import cz.petrnohejl.zitkino.utility.DateConvertor;
import cz.petrnohejl.zitkino.utility.ViewState;


public class ListingFragment extends TaskSherlockFragment implements OnApiCallListener
{
	private View mRootView;
	private ViewState.Visibility mViewState = null;
	private ListingAdapter mAdapter;
	private RequestManager mRequestManager = new RequestManager();
	
	private ArrayList<String> mGroups = new ArrayList<String>();
	private ArrayList<ArrayList<Movie>> mMovies = new ArrayList<ArrayList<Movie>>();
	
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
		setRetainInstance(true);
		
		// restore saved state
		if(savedInstanceState != null)
		{
			handleSavedInstanceState(savedInstanceState);
		}
		
		// handle intent extras
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras != null)
		{
			handleExtras(extras);
		}
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		mRootView = inflater.inflate(R.layout.layout_listing, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
				
		// load and show data
		if(mViewState==null || mViewState==ViewState.Visibility.OFFLINE)
		{
			if(RequestManager.isOnline(getActivity()))
			{
				loadData();
			}
			else
			{
				showOffline();
			}
		}
		else if(mViewState==ViewState.Visibility.CONTENT)
		{
			renderView();
			showList();
		}
		else if(mViewState==ViewState.Visibility.PROGRESS)
		{
			showProgress();
		}
	}
	
	
	@Override
	public void onStart()
	{
		super.onStart();
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		// stop adapter
		if(mAdapter!=null) mAdapter.stop();
	}
	
	
	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	
	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		// cancel async tasks
		mRequestManager.cancelAllRequests();
	}
	
	
	@Override
	public void onDetach()
	{
		super.onDetach();
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// save current instance state
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// action bar menu
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// action bar menu behaviour
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
	{
		// context menu
		super.onCreateContextMenu(menu, view, menuInfo);
	}
	 
	
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item)
	{
		// context menu behaviour
		return super.onContextItemSelected(item);
	}
	
	
	@Override
	public void onApiCallRespond(final ApiCall call, final ResponseStatus status, final Response response)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(response.getClass().getSimpleName().equalsIgnoreCase("MovieResponse"))
				{
					MovieResponse movieResponse = (MovieResponse) response;
					
					// error
					if(movieResponse.isError())
					{
//						Log.d("ZITKINO", "onApiCallRespond: movie response error");
//						Log.d("ZITKINO", "onApiCallRespond status code: " + status.getStatusCode());
//						Log.d("ZITKINO", "onApiCallRespond status message: " + status.getStatusMessage());
//						Log.d("ZITKINO", "onApiCallRespond error: " + movieResponse.getErrorType() + ": " + movieResponse.getErrorMessage());
					}
					
					// response
					else
					{
//						Log.d("ZITKINO", "onApiCallRespond: movie response ok");
//						Log.d("ZITKINO", "onApiCallRespond status code: " + status.getStatusCode());
//						Log.d("ZITKINO", "onApiCallRespond status message: " + status.getStatusMessage());
						
						// data movies
						Iterator<ArrayList<Movie>> iterator1 = movieResponse.getMovies().iterator();
						while(iterator1.hasNext())
						{
							ArrayList<Movie> currentGroup = iterator1.next();
							ArrayList<Movie> newGroup = new ArrayList<Movie>();
							
							Iterator<Movie> iterator2 = currentGroup.iterator();
							while(iterator2.hasNext())
							{
								Movie movie = iterator2.next();
								newGroup.add(new Movie(movie));
							}
							
							mMovies.add(newGroup);
						}
						
						// data groups
						Iterator<String> iterator3 = movieResponse.getGroups().iterator();
						while(iterator3.hasNext())
						{
							String group = iterator3.next();
							mGroups.add(new String(group));
						}
		
						// show list container
						showList();
						
						// render view
						renderView();
					}
				}
				
				mRequestManager.finishRequest(call);
			}
		});
	}


	@Override
	public void onApiCallFail(final ApiCall call, final ResponseStatus status, final boolean parseFail)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(call.getRequest().getClass().getSimpleName().equalsIgnoreCase("MovieRequest"))
				{
//					Log.d("ZITKINO", "onApiCallFail: movie request fail");
//					Log.d("ZITKINO", "onApiCallFail status code: " + status.getStatusCode());
//					Log.d("ZITKINO", "onApiCallFail status message: " + status.getStatusMessage());
//					Log.d("ZITKINO", "onApiCallFail parse fail: " + parseFail);
					
					// show list container
					showList();
					
					// render view
					renderView();
				}
				
				mRequestManager.finishRequest(call);
			}
		});
	}
	
	
	private void handleSavedInstanceState(Bundle savedInstanceState)
	{

	}
	
	
	private void handleExtras(Bundle extras)
	{

	}
	
	
	private void loadData()
	{
		if(!mRequestManager.hasRunningRequest(MovieRequest.class))
		{
			showProgress();
			
			// movie request with paging
			MovieRequest request = new MovieRequest();
			mRequestManager.executeRequest(request, this);
		}
	}
	
	
	private void showList()
	{
		// show list container
		FrameLayout containerList = (FrameLayout) mRootView.findViewById(R.id.container_list);
		LinearLayout containerProgress = (LinearLayout) mRootView.findViewById(R.id.container_progress);
		LinearLayout containerOffline = (LinearLayout) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.VISIBLE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.Visibility.CONTENT;
	}
	
	
	private void showProgress()
	{
		// show progress container
		FrameLayout containerList = (FrameLayout) mRootView.findViewById(R.id.container_list);
		LinearLayout containerProgress = (LinearLayout) mRootView.findViewById(R.id.container_progress);
		LinearLayout containerOffline = (LinearLayout) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.VISIBLE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.Visibility.PROGRESS;
	}
	
	
	private void showOffline()
	{
		// show offline container
		FrameLayout containerList = (FrameLayout) mRootView.findViewById(R.id.container_list);
		LinearLayout containerProgress = (LinearLayout) mRootView.findViewById(R.id.container_progress);
		LinearLayout containerOffline = (LinearLayout) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.VISIBLE);
		mViewState = ViewState.Visibility.OFFLINE;
	}
	
	
	private void renderView()
	{
		// reference
		ExpandableListView listView = (ExpandableListView) mRootView.findViewById(android.R.id.list);
		TextView emptyView = (TextView) mRootView.findViewById(android.R.id.empty);
		
		// listview content
		if(mAdapter==null)
		{
			// adapter
			mAdapter = new ListingAdapter(getActivity(), mGroups, mMovies);
			
			// set adapter
			listView.setAdapter(mAdapter);
			
			// expand first group
			listView.expandGroup(0);
		}
		else
		{
			// refill adapter
			mAdapter.refill(getActivity(), mGroups, mMovies);
			
			// set adapter
			listView.setAdapter(mAdapter);
		}
		
		// listview item onclick
		listView.setOnChildClickListener(new OnChildClickListener()
		{
			@Override
			public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id)
			{
				// listview item onclick
				if(mAdapter!=null) mAdapter.setSelectedPosition(groupPosition);
				
				Movie movie = mMovies.get(groupPosition).get(childPosition);
				String subject = getString(R.string.layout_listing_share_subject);
				StringBuilder builder = new StringBuilder();
				builder.append(movie.getTitle());
				builder.append(" ");
				builder.append(getString(R.string.layout_listing_show));
				builder.append(" ");
				builder.append(DateConvertor.dateToString(movie.getDate(), "dd.MM.yyyy"));
				builder.append(" ");
				builder.append(getString(R.string.layout_listing_in_cinema));
				builder.append(" ");
				builder.append(movie.getCinema());
				builder.append(".");
				
				Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
				shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, builder.toString());
				startActivity(Intent.createChooser(shareIntent, getString(R.string.layout_listing_share)));
				
				return true;
			}
		});
		
		// listview empty view
		listView.setEmptyView(emptyView);
	}
	
	
	private void clearData()
	{
		mGroups.clear();
		Iterator<ArrayList<Movie>> iterator = mMovies.iterator();
		while(iterator.hasNext())
		{
			ArrayList<Movie> group = iterator.next();
			group.clear();
		}
		mMovies.clear();
	}
	
	
	public void refresh()
	{
		// load and show data
		if(RequestManager.isOnline(getActivity()))
		{
			clearData();
			loadData();
		}
		else
		{
			Toast.makeText(getActivity(), R.string.layout_listing_offline, Toast.LENGTH_LONG).show();
		}
	}
	
	
	public void expand()
	{
		if(mAdapter!=null && mRootView!=null)
		{
			ExpandableListView listView = (ExpandableListView) mRootView.findViewById(android.R.id.list);
    		int count =  mAdapter.getGroupCount();
    		for (int i = 0; i <count ; i++) listView.expandGroup(i);
    		mRootView.invalidate();
		}
	}
	
	
	public void collapse()
	{
		if(mAdapter!=null && mRootView!=null)
		{
			ExpandableListView listView = (ExpandableListView) mRootView.findViewById(android.R.id.list);
    		int count =  mAdapter.getGroupCount();
    		for (int i = 0; i <count ; i++) listView.collapseGroup(i);
    		mRootView.invalidate();
		}
	}
}
