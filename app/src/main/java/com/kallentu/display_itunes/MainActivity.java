package com.kallentu.display_itunes;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import be.ceau.itunesapi.Search;
import be.ceau.itunesapi.response.Response;
import be.ceau.itunesapi.response.Result;

/** Primary activity for obtaining user query and searching. */
public class MainActivity extends AppCompatActivity {
    private EditText searchBar;
    private List<Result> results = new ArrayList<>();
    private iTunesResultsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        searchBar = this.findViewById(R.id.userQueryEditText);
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // When user presses enter in the search bar
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    getiTunesResponse();
                    return true;
                }
                return false;
            }
        });
    }

    /** Runs async task {@link iTunesResultsTask} to get iTunes API results from search query. */
    private void getiTunesResponse() {
        String query = searchBar.getText().toString().replace(' ', '+');

        // Sets the list of results on main page
        adapter = new iTunesResultsListAdapter(getApplicationContext(), R.layout.adapter_result, results);
        ListView resultsList = (ListView)findViewById(R.id.results_list);
        resultsList.setAdapter(adapter);
        
        if (!query.equals("")) new iTunesResultsTask().execute(query);
    }

    /** Asynchronous task for obtaining iTunes API results for {@link MainActivity} */
    @SuppressLint("StaticFieldLeak")
    class iTunesResultsTask extends AsyncTask<String, Integer, List<Result>> {

        @Override
        protected List<Result> doInBackground(String... strings) {
            Response response = new Search(strings[0]).execute();
            return response.getResults();
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            super.onPostExecute(results);
            adapter.setResults(results);
        }
    }

}
