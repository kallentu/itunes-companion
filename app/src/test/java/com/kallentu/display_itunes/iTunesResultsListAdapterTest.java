package com.kallentu.display_itunes;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import be.ceau.itunesapi.response.Result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/** Unit tests for {@link iTunesResultsListAdapter} */
@RunWith(RobolectricTestRunner.class)
public class iTunesResultsListAdapterTest {
    private Context mockContext = RuntimeEnvironment.application;
    private iTunesResultsListAdapter adapter;
    private List<Result> results;
    private Result res1;
    private Result res2;

    @Before
    public void setUp() {
        results = new ArrayList<>();
        adapter = new iTunesResultsListAdapter(mockContext, R.layout.adapter_result, results);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mockContext).build();
        ImageLoader.getInstance().init(config);

        res1 = new Result();
        res1.setTrackName("Meow");
        res1.setArtistName("Purrfect Paws");
        res1.setCollectionName("Cool Cats");
        res1.setArtworkUrl100("https://placekitten.com/100/100");

        res2 = new Result();
        res2.setTrackName("I'm hungry and thirsty.");
        res2.setArtistName("Purrfect Paws");
        res2.setCollectionName("Cool Cats");
        res2.setArtworkUrl100("https://placekitten.com/300/300");
    }

    @Test
    public void adapter_notifyDataChanged_noItems() {
        adapter.notifyDataSetChanged();

        assertEquals(adapter.getCount(), 0);
    }

    @Test
    public void adapter_notifyDataSetChanged_multipleItems() {
        results.add(res1);
        results.add(res2);
        adapter.notifyDataSetChanged();

        assertEquals(adapter.getCount(), 2);
    }

    /** Get Result item from adapter. */
    @Test
    public void adapter_getItem() {
        results.add(res1);
        results.add(res2);
        adapter.notifyDataSetChanged();

        assertEquals(adapter.getCount(), 2);
        assertSame(res1, adapter.getItem(0));
        assertSame(res2, adapter.getItem(1));
    }

    /** Get Result item id from adapter (which is position). */
    @Test
    public void adapter_getItemId() {
        results.add(res1);
        results.add(res2);
        adapter.notifyDataSetChanged();

        assertEquals(adapter.getCount(), 2);
        assertSame((long) 0, adapter.getItemId(0));
        assertSame((long) 1, adapter.getItemId(1));
    }

    /** Ensures that changes reflect with removal of Results. */
    @Test
    public void adapter_remove() {
        results.add(res1);
        results.add(res2);
        adapter.notifyDataSetChanged();
        results.remove(res1);
        adapter.notifyDataSetChanged();

        assertEquals(adapter.getCount(), 1);
    }

    /** Ensures that changes reflect with clearing of all Results. */
    @Test
    public void adapter_clear() {
        results.add(res1);
        results.add(res2);
        adapter.notifyDataSetChanged();
        results.clear();
        adapter.notifyDataSetChanged();

        assertEquals(adapter.getCount(), 0);
    }

    /** Ensures that the view is populated correctly with data. */
    @Test
    public void adapter_viewsDisplayed() {
        LinearLayout view = new LinearLayout(mockContext);

        results.add(res1);
        adapter.notifyDataSetChanged();

        TextView songTitle = adapter.getView(0, null, view).findViewById(R.id.result_song_title);
        TextView artist = adapter.getView(0, null, view).findViewById(R.id.result_artist);
        TextView album = adapter.getView(0, null, view).findViewById(R.id.result_album);

        assertEquals(adapter.getCount(), 1);
        assertEquals(res1.getTrackName(), songTitle.getText());
        assertEquals(res1.getArtistName(), artist.getText());
        assertEquals(res1.getCollectionName(), album.getText());
    }
}
