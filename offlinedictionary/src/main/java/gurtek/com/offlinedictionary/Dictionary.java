package gurtek.com.offlinedictionary;

import android.app.Application;

/**
 * * Created by Gurtek Singh on 12/26/2017.
 * Sachtech Solution
 * gurtek@protonmail.ch
 */

public class Dictionary {

    private static EnglishDictionary englishDictionary;

    /**
     * private constructor to
     * restrict client from
     * creating instance of this class
     */
    private Dictionary() {
    }

    /**
     * @param application init Dictionary to make sure
     *                    dictionary instance created only once
     */
    public static void init(Application application) {
        englishDictionary = new EnglishDictionary(application);
    }

    /**
     * @return @{@link EnglishDictionary} responsible for search word
     * @throws IllegalAccessException if @{@link Dictionary} is not
     * init before calling this method
     */
    public static EnglishDictionary getEnglishDictionary()  {

      /*  if (englishDictionary == null) {
            throw new IllegalAccessException("Dictionary must be initialized before you!!!");
        }*/

        return englishDictionary;
    }



}
