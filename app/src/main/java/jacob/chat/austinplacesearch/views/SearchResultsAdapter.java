package jacob.chat.austinplacesearch.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jacob.chat.austinplacesearch.R;
import jacob.chat.austinplacesearch.models.FoursquareCategory;
import jacob.chat.austinplacesearch.models.FoursquareVenue;
import jacob.chat.austinplacesearch.util.ViewUtil;

/**
 * Created by jacob on 2/7/18.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter {

    private List<FoursquareVenue> venues;
    private OnItemClickListener onItemClickListener;

    public SearchResultsAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setVenues(List<FoursquareVenue> venues) {
        this.venues = venues;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result, parent, false);

        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchResultViewHolder searchResult = (SearchResultViewHolder) holder;
        Context context = searchResult.itemView.getContext();

        FoursquareVenue venue = venues.get(position);

        searchResult.venueName.setText(venue.getName());

        int miles = ViewUtil.metersToMiles(venue.getLocation().getDistance());
        String milesString = miles < 1 ? context.getString(R.string.closeBy) :
            context.getResources().getQuantityString(R.plurals.distanceInMiles, miles, miles);

        searchResult.venueDistance.setText(milesString);

        if (venue.getCategories().isEmpty()) {
            searchResult.venueCategory.setText("");
            searchResult.venueIcon.setImageResource(R.drawable.ic_local_cafe_black_24dp);
        } else {
            FoursquareCategory category = venue.getCategories().get(0);
            searchResult.venueCategory.setText(category.getName());
            Picasso.with(context)
                    .load(category.getIcon().getPrefix() + "88" + category.getIcon().getSuffix())
                    .placeholder(R.drawable.ic_local_cafe_black_24dp)
                    .into(searchResult.venueIcon);
        }

        searchResult.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(venue);
            }
        });

        searchResult.venueFavorited.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemFavoriteClick(venue);
            }
        });
    }

    @Override
    public int getItemCount() {
        return venues == null ? 0 : venues.size();
    }

    private class SearchResultViewHolder extends RecyclerView.ViewHolder {

        TextView venueName;
        ImageView venueIcon;
        TextView venueCategory;
        TextView venueDistance;
        ImageView venueFavorited;

        SearchResultViewHolder(View itemView) {
            super(itemView);
            venueName = itemView.findViewById(R.id.venueName);
            venueIcon = itemView.findViewById(R.id.venueIcon);
            venueCategory = itemView.findViewById(R.id.venueCategory);
            venueDistance = itemView.findViewById(R.id.venueDistance);
            venueFavorited = itemView.findViewById(R.id.venueFavorited);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(FoursquareVenue venue);
        void onItemFavoriteClick(FoursquareVenue venue);
    }
}
