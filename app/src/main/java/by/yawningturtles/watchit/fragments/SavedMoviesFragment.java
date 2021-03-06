package by.yawningturtles.watchit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import by.yawningturtles.watchit.R;
import by.yawningturtles.watchit.dal.Film;
import by.yawningturtles.watchit.dal.FilmLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedMoviesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Film> films = new ArrayList<>();


    public SavedMoviesFragment() {
        // Required empty public constructor
    }

    public class PlannedFilmAdapter extends RecyclerView.Adapter<PlannedFilmAdapter.ViewHolder> {
        private List<Film> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvType, tvTitle, tvYear, tvRuntime, tvGenre, tvCountry, tvDirector, tvActors, tvDate;
            public ImageView ivPoster;
            public ImageButton btWatch;

            public ViewHolder(View v) {
                super(v);
                tvType = (TextView) v.findViewById(R.id.tv_movie_type_item);
                tvTitle = (TextView) v.findViewById(R.id.tv_movie_title_item);
                tvYear = (TextView) v.findViewById(R.id.tv_movie_year_item);
                tvRuntime = (TextView) v.findViewById(R.id.tv_movie_runtime_item);
                tvGenre = (TextView) v.findViewById(R.id.tv_movie_genre_item);
                tvCountry = (TextView) v.findViewById(R.id.tv_movie_country_item);
                tvDirector = (TextView) v.findViewById(R.id.tv_movie_director_item);
                tvActors = (TextView) v.findViewById(R.id.tv_movie_actors_item);
                tvDate = (TextView) v.findViewById(R.id.tv_movie_date_item);
                ivPoster = (ImageView) v.findViewById(R.id.iv_movie_poster_item);
                btWatch = (ImageButton) v.findViewById(R.id.bt_watch);
            }
        }

        public PlannedFilmAdapter(List<Film> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public PlannedFilmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.planned_item_layout, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final Film f = mDataset.get(position);
            holder.tvYear.setText(Integer.toString(f.getReleaseYear()));
            holder.tvTitle.setText(f.getTitle());
            holder.tvType.setText(f.getType());
            holder.tvRuntime.setText(f.getRuntime());
            holder.tvGenre.setText(f.getGenre());
            holder.tvCountry.setText(f.getCountry());
            holder.tvDirector.setText(f.getDirector());
            holder.tvDate.setText(String.format("%02d.%02d.%d", f.getPlanningDate().get(Calendar.DAY_OF_MONTH),
                    f.getPlanningDate().get(Calendar.MONTH) + 1, f.getPlanningDate().get(Calendar.YEAR)));
            holder.tvActors.setText(f.getActors());
            holder.btWatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FilmLoader loader = new FilmLoader(getContext());
                    loader.setFilmToWatched(f, true);
                    int pos = -1;
                    for (int i = 0; i < mDataset.size(); i++) {
                        Film film = mDataset.get(i);
                        if (f.getFilmId().equals(film.getFilmId())) {
                            pos = i;
                            break;
                        }
                    }
                    if (pos >= 0) {
                        mDataset.remove(pos);
                        mAdapter.notifyItemRemoved(pos);
                    }
                }
            });
            if (f.getPosterURL() != null && !f.getPosterURL().equals("N/A")) {
                Picasso.with(getContext()).load(f.getPosterURL()).into(holder.ivPoster);
            }
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FilmLoader loader = new FilmLoader(getContext());
        films.addAll(loader.getPlannedFilms());

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.saved_result);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PlannedFilmAdapter(films);
        mRecyclerView.setAdapter(mAdapter);
    }
}
