package com.ngohoang.along.appcore.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;


import com.ngohoang.along.appcore.common.schedulers.SchedulerProvider;
import com.ngohoang.along.appcore.data.model.Movie;
import com.ngohoang.along.appcore.data.source.MoviesDataSource;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Long on 10/6/2016.
 */
@Singleton
public class MoviesLocalDataSource implements MoviesDataSource {


    @NonNull
    private final BriteDatabase mDatabaseHelper;

    @NonNull
    private Func1<Cursor, Movie> mCompetitionMapperFunction;

    @Inject
    public MoviesLocalDataSource(Context context, SchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        MoviesDbHelper dbHelper = new MoviesDbHelper(context);
        SqlBrite sqlBrite = SqlBrite.create();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
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
