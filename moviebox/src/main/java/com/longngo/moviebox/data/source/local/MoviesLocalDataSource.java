package com.longngo.moviebox.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.longngo.moviebox.data.model.Movie;
import com.longngo.moviebox.data.source.MoviesDataSource;
import com.longngo.moviebox.FootballFanApplication;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Long on 10/6/2016.
 */
@Singleton
public class MoviesLocalDataSource implements MoviesDataSource {
    @Nullable
    private static MoviesLocalDataSource INSTANCE;

    @NonNull
    private final BriteDatabase mDatabaseHelper;

    @NonNull
    private Func1<Cursor, Movie> mCompetitionMapperFunction;

    // Prevent direct instantiation.
    private MoviesLocalDataSource() {
        checkNotNull(FootballFanApplication.getMainComponent().context(), "context cannot be null");
        checkNotNull(FootballFanApplication.getMainComponent().schedulerProvider(), "scheduleProvider cannot be null");
        MoviesDbHelper dbHelper = new MoviesDbHelper(FootballFanApplication.getMainComponent().context());
        SqlBrite sqlBrite = SqlBrite.create();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, FootballFanApplication.getMainComponent().schedulerProvider().io());
        mCompetitionMapperFunction = new Func1<Cursor, Movie>() {
            @Override
            public Movie call(Cursor c) {
                String itemId = c.getString(c.getColumnIndexOrThrow(MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_ENTRY_ID));

                String numberOfGames =
                        c.getString(c.getColumnIndexOrThrow(MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_N_O_G));
                String numberOfTeams =
                        c.getString(c.getColumnIndexOrThrow(MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_N_O_T));
                String lastUpdated =
                        c.getString(c.getColumnIndexOrThrow(MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_L_UPDATED));
                String year =
                        c.getString(c.getColumnIndexOrThrow(MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_YEAR));
                String caption =
                        c.getString(c.getColumnIndexOrThrow(MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_CAPTION));
                String league =
                        c.getString(c.getColumnIndexOrThrow(MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_LEAGUE));
                return null;
            }
        };
    }

    public static MoviesLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoviesLocalDataSource();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    public Observable<List<Movie>> getMovieList() {
        String[] projection = {
                MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_ENTRY_ID,
                MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_N_O_G,
                MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_N_O_T,
                MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_L_UPDATED,
                MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_YEAR,
                MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_CAPTION,
                MoviesPersistenceContract.CompetitionEntry.COLUMN_NAME_LEAGUE
        };
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection), MoviesPersistenceContract.CompetitionEntry.TABLE_NAME);
        return mDatabaseHelper.createQuery(MoviesPersistenceContract.CompetitionEntry.TABLE_NAME, sql)
                .mapToList(mCompetitionMapperFunction);
    }

    @Override
    public Observable<List<Movie>> getMovieList(int page) {
        return null;
    }

    @Override
    public void saveCompetition(@NonNull Movie competition) {

    }

}
