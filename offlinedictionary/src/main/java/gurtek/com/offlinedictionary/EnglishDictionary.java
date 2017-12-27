package gurtek.com.offlinedictionary;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * * Created by Gurtek Singh on 12/26/2017.
 * Sachtech Solution
 * gurtek@protonmail.ch
 */

public class EnglishDictionary {

    public static final String ADDED = "added";
    private static String DATABASE_NAME = "dictionary.db";
    private final RoomDatabase.Builder<AppDatabase> appDatabaseBuilder;
    private Application application;
    private AppDatabase db;
    private SharedPreferences preferences;

    public EnglishDictionary(final Application application) {

        appDatabaseBuilder = Room.databaseBuilder(application,
                AppDatabase.class, DATABASE_NAME + "new")
                .allowMainThreadQueries();

        this.application = application;

        preferences = application.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);

        db = appDatabaseBuilder.build();
    }

    /**
     * assets database file name must be
     *
     * @link DATABASE_NAME
     */

    public Maybe<Boolean> importDbFileFromAssets() {
        if (!isimported()) {
            return startimporting(null, null)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return Maybe.just(true);
    }

    /**
     * @param progress show progress hwo many
     *                 are inserted
     */

    public Maybe<Boolean> importDbFileFromAssets(PublishSubject<Integer> progress) {
        if (!isimported()) {
           return startimporting(null, progress).subscribeOn(Schedulers.computation())
                   .observeOn(AndroidSchedulers.mainThread());
        }
        return Maybe.just(true);
    }

    private boolean isimported() {
        return preferences.getBoolean(ADDED, false);
    }


    /**
     * @param path provide path where db file is stored
     *             for better transpancy remove file
     *             after imported
     */
    public Maybe<Boolean> importSdDatabase(final String path) {
        if (!isimported()) {
            return startimporting(path, null).subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }else {
            return Maybe.just(true);
        }


    }

    private Maybe<Boolean> startimporting(String path, PublishSubject<Integer> progress) {

        return Maybe.create(e -> {

            SQLiteAssetHelper sqLiteAssetHelper = new SQLiteAssetHelper(application,
                    DATABASE_NAME, path, null, 1);
            SQLiteDatabase db = sqLiteAssetHelper.getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM entries", null);
            ArrayList<Word> arrayList = new ArrayList<>(cursor.getCount());

            if (cursor.moveToFirst()) {
                do {
                    Word word = new Word(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2));

                    arrayList.add(word);

                    if (progress != null) {
                        int percent = (int) ((double)cursor.getPosition() / cursor.getCount())*100;
                        progress.onNext(percent);
                    }

                }
                while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

            insetinDb(arrayList);

            markAsImported();

            e.onSuccess(true);
            e.onComplete();


        });


    }

    private void insetinDb(ArrayList<Word> arrayList) {
        db.wordDao().insertAll(arrayList);
    }

    /**
     * value saved to pref so never imported
     * again after first import
     */
    private void markAsImported() {
        preferences.edit().putBoolean(ADDED, true).apply();
    }

    @NonNull
    public List<Word> searchWord(String word) {
        return db.wordDao().searchWord(word);
    }

}
