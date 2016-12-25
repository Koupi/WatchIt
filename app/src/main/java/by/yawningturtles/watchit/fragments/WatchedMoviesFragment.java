package by.yawningturtles.watchit.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import by.yawningturtles.watchit.R;
import by.yawningturtles.watchit.dal.Film;
import by.yawningturtles.watchit.dal.FilmLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class WatchedMoviesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public WatchedMoviesFragment() {
        // Required empty public constructor
    }

    public class WatchedFilmAdapter extends RecyclerView.Adapter<WatchedFilmAdapter.ViewHolder> {
        private List<Film> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvType, tvTitle, tvYear, tvRuntime, tvGenre, tvCountry, tvDirector, tvActors, tvDate;
            public ImageView ivPoster;

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
            }
        }

        public WatchedFilmAdapter(List<Film> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public WatchedFilmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.watched_item_layout, parent, false);
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
                    f.getPlanningDate().get(Calendar.MONTH), f.getPlanningDate().get(Calendar.YEAR)));
            holder.tvActors.setText(f.getActors());
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
        return inflater.inflate(R.layout.fragment_watched_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FilmLoader loader = new FilmLoader(getContext());
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.watched_result);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new WatchedFilmAdapter(loader.getPlannedFilms());
        mRecyclerView.setAdapter(mAdapter);
    }

}
