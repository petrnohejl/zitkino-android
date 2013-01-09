package cz.zitkino.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cz.zitkino.R;


public class AboutDialogFragment extends DialogFragment
{
	private View mRootView;
	
	
	public static AboutDialogFragment newInstance()
	{
		AboutDialogFragment fragment = new AboutDialogFragment();
		return fragment;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setCancelable(true);
		setRetainInstance(true);
	}
	
	
	@Override
	public void onDestroyView()
	{
		// http://code.google.com/p/android/issues/detail?id=17423
		if(getDialog() != null && getRetainInstance()) getDialog().setDismissMessage(null);
		super.onDestroyView();
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mRootView = inflater.inflate(R.layout.dialog_about, null);
		
		// set clickable text view
		SpannableString str = new SpannableString(getText(R.string.dialog_about_text));
		Linkify.addLinks(str, Linkify.WEB_URLS);
		
		TextView textViewAbout = (TextView) mRootView.findViewById(R.id.dialog_about_text);
		textViewAbout.setText(str);
		textViewAbout.setMovementMethod(LinkMovementMethod.getInstance());

		// create dialog
		builder
		.setTitle(R.string.dialog_about_title)
		.setIcon(R.drawable.ic_launcher)
		.setView(mRootView)
		.setPositiveButton(R.string.dialog_about_rate_button, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				try
				{
					Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.app_uri)));
			        startActivity(intent);
				}
				catch(android.content.ActivityNotFoundException e)
				{
					Toast.makeText(getActivity(), R.string.dialog_about_rate_error, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		// create dialog from builder
		final AlertDialog dialog = builder.create();
		return dialog;
	}
}
