package gurtek.com.offlinedictionary;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * * Created by Gurtek Singh on 12/26/2017.
 * Sachtech Solution
 * gurtek@protonmail.ch
 */
@Dao
public interface WordDao {

    @Query("SELECT * FROM entries where word like :searchWord")
    List<Word> searchWord(String searchWord);


    @Insert
    void insertAll(List<Word> products);
}
