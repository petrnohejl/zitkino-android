package cz.petrnohejl.zitkino.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import cz.petrnohejl.zitkino.R;
import cz.petrnohejl.zitkino.client.entity.Movie;
import cz.petrnohejl.zitkino.utility.StringConvertor;

public class ListingAdapter extends BaseExpandableListAdapter 
{
	private Context mContext;
	private ArrayList<String> mGroups;
	private ArrayList<ArrayList<Movie>> mMovies;
	private int mSelectedPosition = -1;
	
	
	public ListingAdapter(Context context, ArrayList<String> groups, ArrayList<ArrayList<Movie>> movies)
	{
		mContext = context;
		mGroups = groups;
		mMovies = movies;
	}
	
	
	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
        return mMovies.get(groupPosition).get(childPosition);
    }


	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
        return childPosition;
    }


	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		// inflate view
		View view = convertView;
		if(view == null) 
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.layout_listing_item, null);
			
			// view holder
			ViewHolder holder = new ViewHolder();
			holder.textViewTitle = (TextView) view.findViewById(R.id.layout_listing_item_title);
			holder.textViewDescription = (TextView) view.findViewById(R.id.layout_listing_item_description);
			view.setTag(holder);
		}
		
		// entity
		Movie movie = (Movie) getChild(groupPosition, childPosition);
		
		if(movie != null)
		{
			// view holder
			ViewHolder holder = (ViewHolder) view.getTag();
			
			// content
			holder.textViewTitle.setText(StringConvertor.capitalize(movie.getTitle()));
			holder.textViewDescription.setText(movie.getCinema());
		}
		
		return view;
	}


	@Override
	public int getChildrenCount(int groupPosition) 
	{
        return mMovies.get(groupPosition).size();
    }


	@Override
	public Object getGroup(int groupPosition)
	{
        return mGroups.get(groupPosition);
    }


	@Override
	public int getGroupCount()
	{
        return mGroups.size();
    }


	@Override
	public long getGroupId(int groupPosition)
	{
        return groupPosition;
    }


	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
        // inflate view
 		View view = convertView;
 		if(view == null) 
 		{
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_listing_group, null);
            
            // view holder
 			ViewHolderGroup holder = new ViewHolderGroup();
 			holder.textViewTitle = (TextView) view.findViewById(R.id.layout_listing_group_title);
 			view.setTag(holder);
        }
 		
 		// entity
 		String group = (String) getGroup(groupPosition);
 		
 		if(group != null)
		{
 			// view holder
 			ViewHolderGroup holder = (ViewHolderGroup) view.getTag();
 					
 			// content
 			holder.textViewTitle.setText(group);
		}

        return view;
    }


	@Override
	public boolean hasStableIds()
	{
		return true;
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}
	
	
	public void refill(Context context, ArrayList<String> groups, ArrayList<ArrayList<Movie>> movies)
	{
		mContext = context;
		mGroups = groups;
		mMovies = movies;
		notifyDataSetChanged();
	}
	
	
	public void stop()
	{
		// TODO: stop image loader
	}
	
	
	public void setSelectedPosition(int position)
	{
		mSelectedPosition = position;
		notifyDataSetChanged();
	}
	
	
	public int getSelectedPosition()
	{
		return mSelectedPosition;
	}
	
	
	static class ViewHolder
	{
		TextView textViewTitle;
		TextView textViewDescription;
	}
	
	
	static class ViewHolderGroup
	{
		TextView textViewTitle;
	}
}
