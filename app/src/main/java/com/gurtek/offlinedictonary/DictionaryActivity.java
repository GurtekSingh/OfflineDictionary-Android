package com.gurtek.offlinedictonary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import gurtek.com.offlinedictionary.Dictionary;
import gurtek.com.offlinedictionary.EnglishDictionary;
import gurtek.com.offlinedictionary.Word;
import io.reactivex.subjects.PublishSubject;

public class DictionaryActivity extends AppCompatActivity {

    private EditText box;
    private Button action;
    private EnglishDictionary englishDictionary;
    private TextView textView;
    private View importe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        englishDictionary = Dictionary.getEnglishDictionary();


        PublishSubject<Integer> progress= PublishSubject.create();

        /**
         * Reading file from assets
         * */
      //  englishDictionary.importDbFileFromAssets(progress).subscribe();



        progress.subscribe(integer -> Log.d("data imported",""+integer));

        box = findViewById(R.id.seach_box);
        action = findViewById(R.id.btn);
        textView = findViewById(R.id.list);
        importe = findViewById(R.id.importfile);

        action.setOnClickListener(view -> searchWord());

        importe.setOnClickListener(view -> openIntent());


    }

    private void openIntent() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(Intent.createChooser(intent, "Open folder"),12);

    }

    private void searchWord() {
        englishDictionary.searchWord(box.getText().toString());
        List<Word> words = englishDictionary.searchWord(box.getText().toString());

        textView.setText("");

        int size = words.size();
        for (int i = 0; i < size; i++) {
            textView.append(words.get(i).definition);
            textView.append("/n");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String path = data.getData().getPath();
        File file=new File(path);
        String absolutePath = file.getParentFile().getAbsolutePath();
        englishDictionary.importSdDatabase(absolutePath).subscribe();
    }
}
