**OFFLINE DICTIONARY**

*What is Offline Dictionary*
   
    Search meaning of any word of english offline. 
    This library search through 170000 words to find meaning 
     of particular word.
     
     
     
  *Usage*   
  
  on Application on Create init Dictionary

 
     @Override
     public void onCreate() {
         super.onCreate();
         Dictionary.init(this);
      }
 
 
 pre populated database with inital db
 
 _loading from assets _  

 add dictionary.db to **assets/databases** folder
 
 
        englishDictionary = Dictionary.getEnglishDictionary();
        englishDictionary.importDbFileFromAssets().subscribe();
        
  adding from sdcard  get parentpath database name will be inserted at end by
  default
         
         
           String absolutePath = file.getParentFile().getAbsolutePath();
           englishDictionary.importSdDatabase(absolutePath).subscribe();
          
          
   if you want to show progress show many records are loaded in
            database
            
            
        PublishSubject<Integer> progress= PublishSubject.create();

        englishDictionary.importDbFileFromAssets(progress).subscribe();

        progress.subscribe(percent -> Log.d("data imported",""+percent));
        
  never forget to add subscribe to it.
  
 _Search a word_
 
    List<Word> words = englishDictionary.searchWord(box.getText().toString());

  it return all the possible meaning for that word it's 
    size will be 0 if no word found
    
    
  _And one more thing _
  
  Add this line in your project.gradle file 
  

    buildscript {
      ext{
          supportDependencies = [
                  roomrx : "android.arch.persistence.room:rxjava2:1.0.0"
          ]
      }
    .....
    }