package gurtek.com.offlinedictionary;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ColumnInfo.TEXT;

/**
 * * Created by Gurtek Singh on 12/26/2017.
 * Sachtech Solution
 * gurtek@protonmail.ch
 */

/**
 * Model class for Word that Search
 * Parcelable class and can be clone
 */
@Entity(tableName = "entries")
public class Word implements Parcelable, Cloneable {

    @PrimaryKey(autoGenerate=true)
    public int id;

    /**
     * word which is searched
     */
    @ColumnInfo(typeAffinity = TEXT)
    public String word;

    /**
     * word type means which type
     * of word is this(like noun, plural,adv. etc.)
     */
    @ColumnInfo(typeAffinity = TEXT)
    public String wordtype;

    /**
     * definition of that word
     */
    @ColumnInfo(typeAffinity = TEXT)
    public String definition;

    public Word() {
    }

    protected Word(Parcel in) {
        word = in.readString();
        wordtype = in.readString();
        definition = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    public Word(String word, String wordType, String defination) {
        this.word=word;
        this.wordtype=wordType;
        this.definition=defination;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(wordtype);
        dest.writeString(definition);
    }
}
