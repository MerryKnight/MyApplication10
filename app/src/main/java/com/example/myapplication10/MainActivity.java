package com.example.myapplication10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences =
                getSharedPreferences("myPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Button btn = findViewById(R.id.button);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5= findViewById(R.id.button5);
        EditText e1 = findViewById(R.id.editTextText);

        TextView t1 = findViewById(R.id.textView2);
        btn5.setOnClickListener(new View.OnClickListener() { //putName
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() { //putName
            @Override
            public void onClick(View view) {
                String username = e1.getText().toString();
                // Сохранение строкового значения
                editor.putString("username", username);
                editor.putInt("sessionCount", 1);
                editor.putBoolean("loggedIn", true);
                editor.apply();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() { //getName
            @Override
            public void onClick(View view) {
                String username = sharedPreferences.getString("username",
                        "defaultUsername");

                Log.d("RRR", username);
                int sessionCount = sharedPreferences.getInt("sessionCount", 0);
                boolean isLoggedIn = sharedPreferences.getBoolean("loggedIn", false);
                t1.setText("Имя: "+ username + "\n"
                        + "Номер сессии: " + sessionCount + "\n" +
                          "Залогирован: "+ isLoggedIn);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() { //changeName
            @Override
            public void onClick(View view) {
                String username = e1.getText().toString();
                editor.putString("username", username);
                editor.apply();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() { //deleteName
            @Override
            public void onClick(View view) {
                editor.remove("username");
                editor.apply();
            }
        });

    }
}