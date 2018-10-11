package com.kallentu.display_itunes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

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
        final Result result = results.get(position);

        // Checks for existing view already used
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_result, parent, false);
        }

        TextView resultSongTitle = (TextView) convertView.findViewById(R.id.result_song_title);
        TextView resultArtist = (TextView) convertView.findViewById(R.id.result_artist);
        TextView resultAlbum = (TextView) convertView.findViewById(R.id.result_album);
        ImageView resultAlbumArt = (ImageView) convertView.findViewById(R.id.result_album_art);

        resultSongTitle.setText(result.getTrackName());
        resultArtist.setText(result.getArtistName());
        resultAlbum.setText(result.getCollectionName());

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(result.getLargestArtworkUrl(), resultAlbumArt);

        // Opens information about the track when clicked
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SongInfoActivity.class);
                intent.putExtra("Song Title", result.getTrackName())
                        .putExtra("Artist", result.getArtistName())
                        .putExtra("Album", result.getCollectionName())
                        .putExtra("Album Art", result.getLargestArtworkUrl())
                        .putExtra("Genre", String.valueOf(result.getPrimaryGenreName()))
                        .putExtra("Price", String.valueOf(result.getTrackPrice()))
                        .putExtra("Release Date", result.getReleaseDate())
                        .putExtra("iTunes Song URL", result.getTrackViewUrl());
                getContext().startActivity(intent);
            }
        });

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

    void setResults(List<Result> res) {
        results.clear();
        results.addAll(res);
        notifyDataSetChanged();
    }
}
