package com.example.homework511;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ListView books;
    private Random random = new Random();
    private BookDataAdapter adapter;
    private List<Drawable> images = new ArrayList<>();
    private List<String> subtitles = new ArrayList<>();
    private Button mAddBtn;
    private File subtitlesStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        books = findViewById(R.id.bookList);
        mAddBtn = findViewById(R.id.addBtn);
        adapter = new BookDataAdapter(this, null);
        books.setAdapter(adapter);

        fillImages();
        fillSubtitles();

        books.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(MainActivity.this, "Нажмите \"Удалить\" чтобы удалить =)", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
        });

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                generateRandomBookData();
            }
        });

        subtitlesStorage = new File(getApplicationContext().getExternalFilesDir(null), "subtitles.txt");
    }
    /*переделать метод так, чтобы список примеров формировался из этого файла, т.е. сначала всё должно
    сохраниться в файл, а потом из него
    уже передаваться в адаптер, и при нажатии кнопки "удалить", удаляться из файла*/
    private void generateRandomBookData() {
        BookData book = new BookData(images.get(random.nextInt(images.size())),"A nice book" + adapter.getCount(),subtitles.get(random.nextInt(subtitles.size())));
        adapter.addItem(book);
        BufferedWriter subtitlesWriter = null;
        BufferedReader subtitlesReader = null;
        StringBuilder subBuilder = new StringBuilder();

        try {
            subtitlesWriter = new BufferedWriter(new FileWriter(subtitlesStorage, true));
            subtitlesWriter.write(book.getSubtitle() + ";");
            subtitlesWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try {
            subtitlesReader = new BufferedReader(new FileReader(subtitlesStorage.getAbsoluteFile()));
            String aSub;
            try {
                while ((aSub = subtitlesReader.readLine()) != null) {
                    subBuilder.append(aSub);
                    subBuilder.append("\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            subtitlesReader.close();
        }*/
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void fillImages() {
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.ic_book));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.ic_book1));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.ic_book2));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.ic_book3));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.ic_book4));
    }

    private void fillSubtitles() {
        subtitles.add("W.S. Maugham");
        subtitles.add("S. King");
        subtitles.add("J.K.Rowling");
        subtitles.add("I.Murdoch");
        subtitles.add("J.Austen");
    }
}
