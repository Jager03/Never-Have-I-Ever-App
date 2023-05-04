package com.example.drinkbycr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddQuestionActivity extends AppCompatActivity implements RecyclerViewInterface {

    EditText newQuestionET;
    FloatingActionButton saveQuestion, saveEdit, deleteQuestion, backEditQuestion;
    String newQuestion;
    SharedPreferences sp;
    ArrayList<String> NewQuestionsAL;
    CentralData CD;
    Boolean flag;
    RecyclerView savedRV;
    TextView tusPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        newQuestionET = findViewById(R.id.insertQuestion);
        saveQuestion = findViewById(R.id.saveQuestion);
        sp = getSharedPreferences("MyUserQuestions", Context.MODE_PRIVATE);
        saveEdit = findViewById(R.id.saveEdit);
        deleteQuestion = findViewById(R.id.deleteQuestion);
        savedRV = findViewById(R.id.savedRV);
        tusPreguntas = findViewById(R.id.TusPreguntas);
        backEditQuestion = findViewById(R.id.backEditQuestion);
        flag = false;

        saveEdit.setVisibility(View.GONE);
        deleteQuestion.setVisibility(View.GONE);
        backEditQuestion.setVisibility(View.GONE);

        loadData();
        setupRV();

        if(NewQuestionsAL.isEmpty()) tusPreguntas.setText("No tienes preguntas guardadas!");

        saveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                newQuestion = newQuestionET.getText().toString();
                NewQuestionsAL.add(newQuestion);
                CD.get().addQuestion(newQuestion);
                Toast.makeText(AddQuestionActivity.this, "Pregunta guardada", Toast.LENGTH_SHORT).show();
                saveData();
                setupRV();
                if(flag) tusPreguntas.setText("Tus preguntas(Toca para editar):");
                newQuestionET.setText("");

                //Set flag to notify MainActivity that a new question was added
                CD.get().setQuestionAddedflag(true);
            }
        });

        backEditQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdit.setVisibility(View.GONE);
                deleteQuestion.setVisibility(View.GONE);
                saveQuestion.setVisibility(View.VISIBLE);
                backEditQuestion.setVisibility(View.GONE);
                newQuestionET.setText("");
            }
        });
    }

    private void setupRV(){
        QT_RecyclerViewAdapter adapter = new QT_RecyclerViewAdapter(this, NewQuestionsAL, this);
        savedRV.setAdapter(adapter);
        savedRV.setLayoutManager(new LinearLayoutManager(this));
    }

    private void saveData(){
        newQuestion = newQuestionET.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(NewQuestionsAL);
        editor.remove("user questions");
        editor.putString("user questions", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sp = getSharedPreferences("MyUserQuestions", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("user questions", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        NewQuestionsAL = gson.fromJson(json, type);
        if(NewQuestionsAL == null){
            NewQuestionsAL = new ArrayList<String>();
        }
    }

    @Override
    public void onItemClick(int position) {
        saveEdit.setVisibility(View.VISIBLE);
        deleteQuestion.setVisibility(View.VISIBLE);
        saveQuestion.setVisibility(View.GONE);
        backEditQuestion.setVisibility(View.VISIBLE);

        newQuestionET.setText(NewQuestionsAL.get(position));

        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuestion = newQuestionET.getText().toString();
                NewQuestionsAL.set(position, newQuestion);
                CD.get().replaceQuestion(position, newQuestion);
                saveData();
                setupRV();

                saveEdit.setVisibility(View.GONE);
                deleteQuestion.setVisibility(View.GONE);
                saveQuestion.setVisibility(View.VISIBLE);
                newQuestionET.setText("");

                //Set flag to notify MainActivity that a new question was added
                CD.get().setQuestionAddedflag(true);
                System.out.println("Boolean is " + CD.get().getQuestionAddedflag());
                Toast.makeText(AddQuestionActivity.this, "Pregunta editada con exito", Toast.LENGTH_SHORT).show();
            }
        });

        deleteQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewQuestionsAL.remove(position);
                saveData();
                setupRV();
                if(NewQuestionsAL.isEmpty()) tusPreguntas.setText("No tienes preguntas guardadas!");

                saveEdit.setVisibility(View.GONE);
                deleteQuestion.setVisibility(View.GONE);
                saveQuestion.setVisibility(View.VISIBLE);
                newQuestionET.setText("");

                CD.get().setQuestionAddedflag(true);
                CD.get().removeQuestion(position);
                Toast.makeText(AddQuestionActivity.this, "Pregunta eliminada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRV();
    }
}