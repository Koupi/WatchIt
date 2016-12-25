package by.yawningturtles.watchit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import by.yawningturtles.watchit.dal.ConnectionException;
import by.yawningturtles.watchit.dal.Film;
import by.yawningturtles.watchit.dal.FilmLoader;

public class MovieActivity extends AppCompatActivity {

    private TextView tvTitle, tvType, tvGenre, tvYear, tvPlot, tvRuntime, tvCountry, tvDirector, tvActors;
    private ImageView ivPoster;

    private Film film = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        tvTitle = (TextView) findViewById(R.id.movie_title);
        tvType = (TextView) findViewById(R.id.movie_type);
        tvGenre = (TextView) findViewById(R.id.movie_genre);
        tvYear = (TextView) findViewById(R.id.movie_year);
        tvPlot = (TextView) findViewById(R.id.movie_plot);
        tvRuntime = (TextView) findViewById(R.id.movie_runtime);
        tvCountry = (TextView) findViewById(R.id.movie_country);
        tvDirector = (TextView) findViewById(R.id.movie_director);
        tvActors = (TextView) findViewById(R.id.movie_actors);
        ivPoster = (ImageView) findViewById(R.id.movie_poster);

        Intent intent = getIntent();
        LoadMovieTask ps = new LoadMovieTask(this);
        ps.execute(intent.getStringExtra("id"));

        final FloatingActionButton save = (FloatingActionButton) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMovie();
            }
        });
    }

    protected void saveMovie() {
        if (film != null) {
            showDialog(1);
        }
    }

    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog tpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Toast.makeText(MovieActivity.this, String.format("Film planned for %02d.%02d.%d.", dayOfMonth, month, year), Toast.LENGTH_LONG).show();
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    FilmLoader loader = new FilmLoader(MovieActivity.this);
                    loader.setFilmToPlanned(film, true, cal);
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    public void showFilm(Film film) {
        if (film == null) {
            Toast.makeText(this, "Can't load film data.", Toast.LENGTH_LONG).show();
            return;
        }
        this.film = film;
        tvTitle.setText(film.getTitle());
        tvType.setText(film.getType());
        tvGenre.setText(film.getGenre());
        tvYear.setText(Integer.toString(film.getReleaseYear()));
        tvPlot.setText(film.getPlot());
        tvRuntime.setText(film.getRuntime());
        tvCountry.setText(film.getCountry());
        tvDirector.setText(film.getDirector());
        tvActors.setText(film.getActors());

        if (film.getPosterURL() != null && !film.getPosterURL().equals("N/A")) {
            Picasso.with(this).load(film.getPosterURL()).into(ivPoster);
        }
    }

    private class LoadMovieTask extends AsyncTask<String, Void, Film> {
        private final Context context;
        private ProgressDialog progress;

        LoadMovieTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress= new ProgressDialog(this.context);
            progress.setMessage("Loading movie...");
            progress.show();
        }

        @Override
        protected void onPostExecute(Film film) {
            super.onPostExecute(film);
            progress.dismiss();
            showFilm(film);
        }

        @Override
        protected Film doInBackground(String... params) {
            try {
                FilmLoader loader = new FilmLoader(context);
                return loader.getFilmByID(params[0]);
            }
            catch (ConnectionException e) {
                return null;
            }
        }
    }
}
