package com.example.books;



import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Лист с данными всех книг
    public List<Book> books = new ArrayList<>();;

    // ключи для передачи и получения данных
    static final String ID_KEY = "ID";
    static final String NAME_KEY = "NAME";
    static final String WRITER_KEY = "WRITER";
    static final String GENRE_KEY = "GENRE";
    static final String COMMENT_KEY = "COMMENT";
    static final String BOOL_KEY = "BOOL";

    // переменные нужны для полученя данных
    public String NAME;
    public String WRITER;
    public String GENRE;
    public String COMMENT;
    public Integer ID;
    public Boolean FLAG;

    // Возврат из дочерней активити
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // получение данных
                        Intent intent = result.getData();
                        ID = intent.getIntExtra(ID_KEY, 0);
                        FLAG = intent.getBooleanExtra(BOOL_KEY, false);
                        NAME = intent.getStringExtra(NAME_KEY);
                        WRITER = intent.getStringExtra(WRITER_KEY);
                        GENRE = intent.getStringExtra(GENRE_KEY);
                        COMMENT = intent.getStringExtra(COMMENT_KEY);
                        String[] name = intent.getStringArrayExtra("NAME_ARRAY_KEY");
                        String[] writer = intent.getStringArrayExtra("WRITER_ARRAY_KEY");
                        String[] genre = intent.getStringArrayExtra("GENRE_ARRAY_KEY");
                        String[] comment = intent.getStringArrayExtra("COMMENT_ARRAY_KEY");
                        books.clear();
                        //заполнение массива
                        if (FLAG) {
                            for(int i = 0; i < name.length;i++)
                            {
                                books.add(new Book(name[i],writer[i],genre[i],comment[i]));
                            }
                            books.set(ID,new Book(NAME,WRITER,GENRE,COMMENT));
                            if (ID == books.size() - 1)
                                books.add(new Book("Добавить книгу", "Писатель", "Жанр", "Ваш отзыв"));
                        }
                        if (!FLAG & !(ID == books.size() - 1)) {

                            for(int i = 0; i < name.length;i++)
                            {
                                if (i!=ID) books.add(new Book(name[i],writer[i],genre[i],comment[i]));
                            }
                        }
                        setList();
                    }
                }
            });


    // для сохраниения данных при изменении ориентации
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String[] name = new String[books.size()];
        String[] writer = new String[books.size()];
        String[] genre = new String[books.size()];
        String[] comment = new String[books.size()];
        for(int i = 0;i < books.size();i++)
        {
            name[i] = books.get(i).name;
            writer[i] = books.get(i).writer;
            genre[i] = books.get(i).writer;
            comment[i] = books.get(i).comment;
        }
        outState.putStringArray("NAME_ARRAY_KEY",name);
        outState.putStringArray("WRITER_ARRAY_KEY",writer);
        outState.putStringArray("GENRE_ARRAY_KEY",genre);
        outState.putStringArray("COMMENT_ARRAY_KEY",comment);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // получение данных при изменении ориентации
        if (savedInstanceState != null)
        {
            String[] name = savedInstanceState.getStringArray("NAME_ARRAY_KEY");
            String[] writer = savedInstanceState.getStringArray("WRITER_ARRAY_KEY");
            String[] genre = savedInstanceState.getStringArray("GENRE_ARRAY_KEY");
            String[] comment = savedInstanceState.getStringArray("COMMENT_ARRAY_KEY");
            books.clear();
            for(int i = 0; i < name.length;i++)
            {
                books.add(new Book(name[i],writer[i],genre[i],comment[i]));
            }
        }
        else {
            books.add(new Book("Добавить книгу", "Писатель", "Жанр", "Ваш отзыв"));
        }
        // заполнение списка
        setList();
        // определения слушетеля для нажатия на элемент в списке
        ListView listView = findViewById(R.id.List);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //обработка нажития на каждый конкретный эленемт
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Transition(position);  // вызывает метод для передачи данных в дочернию активити
            }
        });
    }


    // метод для заполнения списка
    public void setList()
    {
        ListView listView = findViewById(R.id.List);
        String[] name = new String[books.size()];
        String v;
        for(int i = 0;i < books.size();i++)
        {
            v = books.get(i).name;
            name[i] = v;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, name);
        listView.setAdapter(adapter);
    }

    // отправка данных и запуст дочерней активити
    public void Transition(Integer id)
    {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(NAME_KEY, books.get(id).name);
        intent.putExtra(WRITER_KEY,books.get(id).writer);
        intent.putExtra(GENRE_KEY,books.get(id).genre);
        intent.putExtra(COMMENT_KEY,books.get(id).comment);
        intent.putExtra(ID_KEY, id);
        String[] name = new String[books.size()];
        String[] writer = new String[books.size()];
        String[] genre = new String[books.size()];
        String[] comment = new String[books.size()];
        for(int i = 0;i < books.size();i++)
        {
            name[i] = books.get(i).name;
            writer[i] = books.get(i).writer;
            genre[i] = books.get(i).writer;
            comment[i] = books.get(i).comment;
        }
        intent.putExtra("NAME_ARRAY_KEY",name);
        intent.putExtra("WRITER_ARRAY_KEY",writer);
        intent.putExtra("GENRE_ARRAY_KEY",genre);
        intent.putExtra("COMMENT_ARRAY_KEY",comment);
        if(id == books.size()-1) intent.putExtra(BOOL_KEY, true);
        else intent.putExtra(BOOL_KEY, false);
        mStartForResult.launch(intent);
    }

}