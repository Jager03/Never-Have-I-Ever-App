package com.example.drinkbycr;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class CentralData {

    //Attributes
    ArrayList<String> myQuestions;
    Boolean QuestionAddedflag;
    int pos;


    //Constructor
    private CentralData(){

        this.myQuestions = new ArrayList<String>();
        this.QuestionAddedflag = false;
        //fillQuestions();
        System.out.println("CD Constructor");
    }

    //Singleton Pattern
    private static CentralData sCentralData;
    public static CentralData get(){
        if(sCentralData == null){
            sCentralData = new CentralData();
        }
        return sCentralData;
    }

    //getters
    public ArrayList<String> getQuestions(){
        return this.myQuestions;
    }
    public Boolean getQuestionAddedflag(){
        return this.QuestionAddedflag;
    }

    //setters
    public void setQuestions(ArrayList<String> al){
        this.myQuestions = al;
    }
    public void setQuestionAddedflag(Boolean b){
        this.QuestionAddedflag = b;
    }

    //Methods
    public void addQuestion(String s){
        this.myQuestions.add(s);
    }
    public void removeQuestion(int index){
        myQuestions.remove(index);
    }
    public void fillQuestions(){
        //Add questions to the set
        myQuestions.add("me he metido entre dos mejores amigos/as");
        myQuestions.add("me he bajado una botella de trago solo/a");
        myQuestions.add("he mandado nudes");
        myQuestions.add("he culeado en un carro");
        myQuestions.add("he culdeado en un baño");
        myQuestions.add("he culeado en la ducha");
        myQuestions.add("he culeado en el colegio/univerisdad");
        myQuestions.add("he despertado sin saber que pasó anoche");
        myQuestions.add("he vacildo con mas de una persona en una misma noche");
        myQuestions.add("he vacilado/culeado con un amigo/a");
        myQuestions.add("he puesto cachos");
        myQuestions.add("he sido el cacho");
        myQuestions.add("he tenido novio/a");
        myQuestions.add("me he comido a un alemán/ana");
        myQuestions.add("le he robado el culo a un/a amigo/a");
        myQuestions.add("he tenido un crush en un amigo/a");
        myQuestions.add("he hecho un trio");
        myQuestions.add("he vacilado con alguien del mismo sexo");
        myQuestions.add("he hecho un pico de 3/4/5");
        myQuestions.add("he tenido que maquillar un hickey");
        myQuestions.add("he hecho un hickey");
        myQuestions.add("he salido con alguien de tinder");
        myQuestions.add("he culeado con alguien de tinder");
        myQuestions.add("he hecho un video culeando");
        myQuestions.add("he hecho sexting");
        myQuestions.add("he comprado/vendido pacs");
        myQuestions.add("me he comido con alguien 2 años menor o más");
        myQuestions.add("he tomado la pastilla del día siguiente (o dado)");
        myQuestions.add("he ilusionado a alguien a propósito");
        myQuestions.add("he calentado huevos a propósito");
        myQuestions.add("me he comido a alguien de mi prom");
        myQuestions.add("me he bajado el novio/a a un amigo/a");
        myQuestions.add("he manejado ebria");
        myQuestions.add("he ido a clases ebria/ chuchaqui");
        myQuestions.add("he sido botado de una discoteca");
    }
    public void replaceQuestion(int position, String element){
        myQuestions.set(position, element);

    }


}
