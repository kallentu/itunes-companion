package com.kallentu.display_itunes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.ceau.itunesapi.response.Result;

/**
 * Array adapter for displaying the API results from {@link MainActivity}.
 */
public class iTunesResultsListAdapter extends ArrayAdapter<Result> {
    private final List<Result> results;

    public iTunesResultsListAdapter(@NonNull Context context, int resource, List<Result> results) {
        super(context, resource);
        this.results = results;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Result result = results.get(position);

        // Checks for existing view already used
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_result, parent, false);
        }

        TextView resultSongTitle = (TextView) convertView.findViewById(R.id.result_song_title);
        TextView resultArtist = (TextView) convertView.findViewById(R.id.result_artist);
        TextView resultAlbum = (TextView) convertView.findViewById(R.id.result_album);

        resultSongTitle.setText(result.getTrackName());
        resultArtist.setText(result.getArtistName());
        resultAlbum.setText(result.getCollectionName());

        return convertView;
    }

    @Nullable
    @Override
    public Result getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return results.size();
    }
}
