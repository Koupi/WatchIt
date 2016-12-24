package by.yawningturtles.watchit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import by.yawningturtles.watchit.MovieActivity;
import by.yawningturtles.watchit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button showMovieBt = (Button) getView().findViewById(R.id.show_movie);
        showMovieBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMovie(v);
            }
        });
    }

    public void showMovie(View view) {
        Intent intent = new Intent(getContext(), MovieActivity.class);
        startActivity(intent);
    }

}
