package by.yawningturtles.watchit.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.yawningturtles.watchit.MovieActivity;
import by.yawningturtles.watchit.R;
import by.yawningturtles.watchit.dal.ConnectionException;
import by.yawningturtles.watchit.dal.FilmLoader;
import by.yawningturtles.watchit.dal.ShortFilm;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private EditText etTitle, etYear;
    private Spinner spType;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<ShortFilm> shortFilms = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }


    public class ShortFilmAdapter extends RecyclerView.Adapter<ShortFilmAdapter.ViewHolder> {
        private List<ShortFilm> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvType, tvTitle, tvYear;
            public ImageButton btLookMore;

            public ViewHolder(View v) {
                super(v);
                tvType = (TextView) v.findViewById(R.id.tv_movie_type_item);
                tvTitle = (TextView) v.findViewById(R.id.tv_movie_title_item);
                tvYear = (TextView) v.findViewById(R.id.tv_movie_year_item);
                btLookMore = (ImageButton) v.findViewById(R.id.bt_look_more);
            }
        }

        public ShortFilmAdapter(List<ShortFilm> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public ShortFilmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_item_layout, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            ShortFilm f = mDataset.get(position);
            holder.tvYear.setText(Integer.toString(f.getReleaseYear()));
            holder.tvTitle.setText(f.getTitle());
            holder.tvType.setText(f.getType());
            final String id = shortFilms.get(position).getFilmId();
            holder.btLookMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchFragment.this.getContext(), MovieActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTitle = (EditText) getView().findViewById(R.id.et_movie_title);
        etYear = (EditText) getView().findViewById(R.id.et_movie_year);
        spType = (Spinner) getView().findViewById(R.id.spinner_movie_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.type_array, R.layout.type_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapter);

        ImageButton searchMoviesBt = (ImageButton) getView().findViewById(R.id.bt_search);
        searchMoviesBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovies(v);
            }
        });

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.search_result);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ShortFilmAdapter(shortFilms);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void searchMovies(View view) {
        String type = spType.getSelectedItem().toString().toLowerCase();
        String title = etTitle.getText().toString().trim();
        if(title.isEmpty()) {
            Toast.makeText(getContext(), "You cannot leave title blank.", Toast.LENGTH_LONG).show();
            return;
        }
        String year = etYear.getText().toString().trim();
        LoadTask ps = new LoadTask(getContext());
        ps.execute(title, type, year);
    }

    public void showMovies(List<ShortFilm> films) {
        shortFilms.clear();
        if (films.size() > 0) {
            shortFilms.addAll(films);
            mAdapter.notifyDataSetChanged();
            return;
        }
        Toast.makeText(getContext(), "No movies found.", Toast.LENGTH_LONG).show();
    }

    private class LoadTask extends AsyncTask<String, Void, List<ShortFilm>> {
        private final Context context;
        private ProgressDialog progress;

        LoadTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress= new ProgressDialog(this.context);
            progress.setMessage("Loading movies...");
            progress.show();
        }

        @Override
        protected void onPostExecute(List<ShortFilm> films) {
            super.onPostExecute(films);
            progress.dismiss();
            showMovies(films);
        }

        @Override
        protected List<ShortFilm> doInBackground(String... params) {
            try {
                FilmLoader loader = new FilmLoader(context);
                if (params[1].equalsIgnoreCase("any type")) {
                    if (params[2].trim().isEmpty()) {
                        return loader.getShortFilmBySearch(params[0]);
                    } else {
                        return loader.getShortFilmBySearch(params[0], Integer.parseInt(params[2].trim()));
                    }
                } else {
                    if (params[2].trim().isEmpty()) {
                        return loader.getShortFilmBySearch(params[0], params[1]);
                    } else {
                        return loader.getShortFilmBySearch(params[0], params[1], Integer.parseInt(params[2].trim()));
                    }
                }
            }
            catch (ConnectionException e){ return null;}
        }
    }

}
