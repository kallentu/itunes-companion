package com.kallentu.display_itunes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/* Primary activity for obtaining user query and searching. */
public class MainActivity extends AppCompatActivity {
    private EditText searchBar;

    // TODO (2): Use input to search for a response
    // TODO (3): Make a class that stores the responses from iTunes
    // TODO (4): Make a list adapter that stores each result

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
                    //getiTunesResponse();
                    return true;
                }
                return false;
            }
        });
    }
}
