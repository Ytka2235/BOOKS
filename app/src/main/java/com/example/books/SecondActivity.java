package com.example.books;

import static com.example.books.MainActivity.BOOL_KEY;
import static com.example.books.MainActivity.COMMENT_KEY;
import static com.example.books.MainActivity.GENRE_KEY;
import static com.example.books.MainActivity.ID_KEY;
import static com.example.books.MainActivity.NAME_KEY;
import static com.example.books.MainActivity.WRITER_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class SecondActivity extends AppCompatActivity {

    public Integer id;
    public EditText nameView;
    public EditText writerView;
    public EditText genreView;
    public EditText commentView;
    public String[] name;
    public String[] writer;
    public String[] genre;
    public String[] comment;

    // получение данных и вывод этих данных
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Bundle extras = getIntent().getExtras();
        id = extras.getInt(ID_KEY);
        nameView = findViewById(R.id.name);
        nameView.setText(extras.getString(NAME_KEY));
        writerView = findViewById(R.id.writer);
        writerView.setText(extras.getString(WRITER_KEY));
        genreView = findViewById(R.id.genre);
        genreView.setText(extras.getString(GENRE_KEY));
        commentView = findViewById(R.id.comment);
        commentView.setText(extras.getString(COMMENT_KEY));
        name = extras.getStringArray("NAME_ARRAY_KEY");
        writer = extras.getStringArray("WRITER_ARRAY_KEY");
        genre = extras.getStringArray("GENRE_ARRAY_KEY");
        comment = extras.getStringArray("COMMENT_ARRAY_KEY");
    }
    // метод при нажатии на кнопку назад
    public void butBackClick(View view)
    {
        Back(true);
    }
    // метод при нажатии на кнопку удалить
    public void butDeleteClick(View view) {
        Back(false);
    }
    // отправка данных в главную активити и завершение дочерней активити
    public void Back(Boolean flag)
    {
        Intent intent = new Intent();
        intent.putExtra(ID_KEY, id);
        intent.putExtra(NAME_KEY, nameView.getText().toString());
        intent.putExtra(WRITER_KEY, writerView.getText().toString());
        intent.putExtra(GENRE_KEY, genreView.getText().toString());
        intent.putExtra(COMMENT_KEY, commentView.getText().toString());
        intent.putExtra(BOOL_KEY, flag);
        intent.putExtra("NAME_ARRAY_KEY",name);
        intent.putExtra("WRITER_ARRAY_KEY",writer);
        intent.putExtra("GENRE_ARRAY_KEY",genre);
        intent.putExtra("COMMENT_ARRAY_KEY",comment);
        setResult(RESULT_OK, intent);
        finish();
    }

}