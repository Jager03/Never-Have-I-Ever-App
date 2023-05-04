package com.example.drinkbycr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int max, index, min;
    TextView pregunta;
    CentralData CD;
    ArrayList<String> questionsSet;
    ArrayList<String> userQuestions;
    Button next;
    FloatingActionButton toAddQ;
    Boolean questionSetFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar el array de preguntas y flag
        questionsSet = new ArrayList<String>();
        questionSetFlag = false;

        //Primero popular el Array de preguntas
        CD.get().fillQuestions();
        questionsSet = CD.get().getQuestions();
        System.out.println("Numero de elementos en getQuestions " + CD.get().getQuestions().size());

        //Setting up the data
        setUpData();


        //Inicializar las variables
        pregunta = findViewById(R.id.question);
        next = findViewById(R.id.next);
        toAddQ = findViewById(R.id.toAddQuestion);
        min = 0;

        //Al inicio mostrar la primera pregunta
        setQuestion();

        //Listener para el boton de siguiente
        //si ya se acabaron las preguntas iniciar endgame activity
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionsSet.isEmpty()){
                    Intent intent = new Intent(MainActivity.this, EndGame.class);
                    startActivity(intent);
                }
                else setQuestion();

            }
        });

        //Listener boton iniciar actividad para agregar pregunta
        toAddQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddQuestionActivity.class);
                startActivity(intent);
            }
        });

        System.out.println("onCreate");

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        checkForDuplicates();

        System.out.println("onRestart");
    }

    public void setQuestion(){

        if(questionsSet.isEmpty()){
            pregunta.setText("No hay preguntas");
            return;
        }

        max = questionsSet.size() - 1;
        index =(int)Math.floor(Math.random()*(max-min+1)+min);

        pregunta.setText("Yo nunca " + questionsSet.get(index));
        questionsSet.remove(index);

        //Flag para no duplicar elementos en el momento de onRestart
        if(questionsSet.isEmpty()) questionSetFlag = true;
        System.out.println(questionsSet.size());
    }

    public void setUpData(){
        //Popular el Array de preguntas guardadas por el usuario
        loadData();

        //Unificar preguntas del Usuario con las fijas
        if(!userQuestions.isEmpty()){
            for(int i = 0; i < userQuestions.size(); i++){
                if(!questionsSet.contains(userQuestions.get(i))){
                    questionsSet.add(userQuestions.get(i));
                }

            }
        }
    }

    public void checkForDuplicates(){
        //Looping through every element of myQuestions
        ArrayList<String> allQuestions = CD.get().getQuestions();
        for(int i = 0; i < allQuestions.size(); i++){
            //If one element is new (exists in allQuestions but not in actual questionsSet) then
            //add it to the set
            if(!questionsSet.contains(allQuestions.get(i))){
                questionsSet.add(allQuestions.get(i));
            }
        }
    }

    private void loadData(){
        SharedPreferences sp = getSharedPreferences("MyUserQuestions", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("user questions", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        userQuestions = gson.fromJson(json, type);

        if(userQuestions == null){
            userQuestions = new ArrayList<String>();
        }
    }

    private void fillQuestions(){
        questionsSet.add("me he metido entre dos mejores amigos/as");
    }
}

