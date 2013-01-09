package cz.zitkino.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

import cz.zitkino.R;
import cz.zitkino.fragment.ListingFragment;

public class ListingActivity extends SherlockFragmentActivity
{
	private AlertDialog mAboutDialog;
	
	
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
		
		if(mAboutDialog!=null) mAboutDialog.dismiss();
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
				showAbout();
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
	
	
	private void showAbout()
	{
		final SpannableString str = new SpannableString(getText(R.string.dialog_about_text));
		Linkify.addLinks(str, Linkify.WEB_URLS);
		
		final TextView content = new TextView(this);
		content.setText(str);
		content.setMovementMethod(LinkMovementMethod.getInstance());
		content.setPadding(40, 40, 40, 40);

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setTitle(R.string.dialog_about_title);
		alertDialog.setView(content);
		alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
			}
		});
		mAboutDialog = alertDialog.create();
		mAboutDialog.show();
	}
}
