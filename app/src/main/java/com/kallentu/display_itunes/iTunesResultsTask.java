package com.kallentu.display_itunes;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import be.ceau.itunesapi.Search;
import be.ceau.itunesapi.response.Response;
import be.ceau.itunesapi.response.Result;

// TODO (5): Call listadapter to populate MainActivity with results

/** Asynchronous task for obtaining iTunes API results for {@link MainActivity} */
public class iTunesResultsTask extends AsyncTask<String, Integer, List<Result>> {

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
        Log.e("iTunesResultsTask", "Success!");
    }
}
