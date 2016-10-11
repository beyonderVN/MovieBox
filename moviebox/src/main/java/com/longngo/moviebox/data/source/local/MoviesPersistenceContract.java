package com.longngo.moviebox.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by Long on 10/6/2016.
 * The Contract used for DB to save the data locally
 */

public class MoviesPersistenceContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private MoviesPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class CompetitionEntry implements BaseColumns {
        public static final String TABLE_NAME = "competition";
        public static final String COLUMN_NAME_ENTRY_ID = "competition_id";
        public static final String COLUMN_NAME_N_O_G = "numberofgames";
        public static final String COLUMN_NAME_N_O_T = "numberofteams";
        public static final String COLUMN_NAME_L_UPDATED = "lastupdated";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_CAPTION = "caption";
        public static final String COLUMN_NAME_LEAGUE = "league";
    }
}
