package com.kallentu.display_itunes;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import javax.xml.transform.Result;

/**
 * More information on specific song from clicking in {@link iTunesResultsListAdapter}.
 */
public class SongInfoActivity extends AppCompatActivity {

    private static final String SONG_TITLE = "Song Title";
    private static final String ARTIST = "Artist";
    private static final String ALBUM = "Album";
    private static final String ALBUM_ART = "Album Art";
    private static final String GENRE = "Genre";
    private static final String PRICE = "Price";
    private static final String RELEASE_DATE = "Release Date";
    private static final String ITUNES_SONG_URL = "iTunes Song URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_info);

        // Obtain all information from intent
        Intent intent = getIntent();
        String songTitle = intent.getStringExtra(SONG_TITLE);
        String artist = intent.getStringExtra(ARTIST);
        String album = intent.getStringExtra(ALBUM);
        String albumArt = intent.getStringExtra(ALBUM_ART);
        String genre = "Genre: " + intent.getStringExtra(GENRE);
        // Temporary Assumption: Prices are in USD
        String price = "Price: $" + intent.getStringExtra(PRICE);
        String releaseDate = "Release Date: " + intent.getStringExtra(RELEASE_DATE);
        final String songiTunesURL = intent.getStringExtra(ITUNES_SONG_URL);

        // Find all views needed to populate information with
        ImageView albumArtImageView = (ImageView) findViewById(R.id.info_album_art);
        TextView songTitleTextView = (TextView) findViewById(R.id.info_song_title);
        TextView artistTextView = (TextView) findViewById(R.id.info_artist);
        TextView albumTextView = (TextView) findViewById(R.id.info_album);
        TextView genreTextView = (TextView) findViewById(R.id.info_genre);
        TextView priceTextView = (TextView) findViewById(R.id.info_price);
        TextView releaseDateTextView = (TextView) findViewById(R.id.info_release_date);
        Button buyOniTunesButton = (Button) findViewById(R.id.info_buy_itunes_button);

        // Set song data in each view
        new ImageTask(albumArtImageView).execute(albumArt);
        songTitleTextView.setText(songTitle);
        artistTextView.setText(artist);
        albumTextView.setText(album);
        genreTextView.setText(genre);
        priceTextView.setText(price);
        releaseDateTextView.setText(releaseDate);
        buyOniTunesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(songiTunesURL));
                startActivity(browserIntent);
            }
        });
    }
}
