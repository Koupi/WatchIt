package by.yawningturtles.watchit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.yawningturtles.watchit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WatchedMoviesFragment extends Fragment {


    public WatchedMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watched_movies, container, false);
    }

}