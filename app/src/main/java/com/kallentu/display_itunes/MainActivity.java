package com.kallentu.display_itunes;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import be.ceau.itunesapi.Search;
import be.ceau.itunesapi.response.Response;
import be.ceau.itunesapi.response.Result;

// TODO (6): Add tests for the adapter
// TODO (7): Stylize the adapter contents/xml
// TODO (8): Add album art if available

/** Primary activity for obtaining user query and searching. */
public class MainActivity extends AppCompatActivity {
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        new iTunesResultsTask().execute(query);
    }

    /** Asynchronous task for obtaining iTunes API results for {@link MainActivity} */
    @SuppressLint("StaticFieldLeak")
    class iTunesResultsTask extends AsyncTask<String, Integer, List<Result>> {

        @Override
        protected List<Result> doInBackground(String... strings) {
            List<Result> results = new ArrayList<>();
            for (String query : strings) {
                Response response = new Search(query).execute();
                results.addAll(response.getResults());
            }
            return results;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            super.onPostExecute(results);

            // Sets the list of results on main page
            iTunesResultsListAdapter adapter = new iTunesResultsListAdapter(getApplicationContext(), R.layout.adapter_result, results);
            ListView resultsList = (ListView)findViewById(R.id.results_list);
            resultsList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}
