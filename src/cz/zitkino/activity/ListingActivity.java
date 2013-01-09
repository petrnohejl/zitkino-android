package cz.zitkino.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

import cz.zitkino.R;
import cz.zitkino.dialog.AboutDialogFragment;
import cz.zitkino.fragment.ListingFragment;

public class ListingActivity extends SherlockFragmentActivity
{
	private final String DIALOG_ABOUT = "about";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setActionBar();
		setContentView(R.layout.activity_listing);
		
		// restore saved state
		if(savedInstanceState != null)
		{
			handleSavedInstanceState(savedInstanceState);
		}
		
		// handle intent extras
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			handleExtras(extras);
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
	}
	
	
	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		// save current instance state
		super.onSaveInstanceState(outState);
	}
	
	
	@Override
	public void onRestoreInstanceState (Bundle savedInstanceState)
	{
		// restore saved state
		super.onRestoreInstanceState(savedInstanceState);
		
		if(savedInstanceState != null)
		{

		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// action bar menu
		MenuInflater menuInflater = new MenuInflater(this);
		menuInflater.inflate(R.menu.menu_listing, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		ListingFragment listingFragment;
		
		// action bar menu behaviour
		switch (item.getItemId()) 
		{
			case android.R.id.home:
				Intent intent = new Intent(this, ListingActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
				
			case R.id.ab_button_refresh:
				listingFragment = (ListingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_listing);
				if(listingFragment!=null) listingFragment.refreshData();
				return true;
				
			case R.id.ab_button_help:
				showAboutDialog();
				return true;
				
			case R.id.ab_button_list_expand:
				listingFragment = (ListingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_listing);
				if(listingFragment!=null) listingFragment.expand();
				return true;
				
			case R.id.ab_button_list_collapse:
				listingFragment = (ListingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_listing);
				if(listingFragment!=null) listingFragment.collapse();
				return true;
			
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void handleSavedInstanceState(Bundle savedInstanceState)
	{
		
	}
	
	
	private void handleExtras(Bundle extras)
	{
		
	}
	
	
	private void setActionBar()
	{
		ActionBar bar = getSupportActionBar();
		bar.setDisplayUseLogoEnabled(true);
		bar.setDisplayShowTitleEnabled(false);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setHomeButtonEnabled(true);
		
		setSupportProgressBarIndeterminateVisibility(false);
	}
	
	
	private void showAboutDialog()
	{
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		Fragment prev = getSupportFragmentManager().findFragmentByTag(DIALOG_ABOUT);
		if(prev != null) transaction.remove(prev);
		transaction.addToBackStack(null);
		
		// create and show the dialog
		DialogFragment newFragment = AboutDialogFragment.newInstance();
		newFragment.show(transaction, DIALOG_ABOUT);
	}
}
