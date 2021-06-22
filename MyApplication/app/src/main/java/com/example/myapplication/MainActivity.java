package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SqlDatabaseHelper sqlDatabaseHelper ;
    TextView dataList;
    TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlDatabaseHelper = new SqlDatabaseHelper(MainActivity.this);
        Button delete = findViewById(R.id.delete_data);
        Button update = findViewById(R.id.update_data);
        Button insert = findViewById(R.id.insert_data);
        Button read = findViewById(R.id.refresh_data);
         dataList = (TextView) findViewById(R.id.textView2);
        counter = (TextView) findViewById(R.id.counter);
        counter.setText("");
        read.setOnClickListener(v -> {

            counter.setText("data count :" +sqlDatabaseHelper.totalcount());

            List<ParticipentModel> participentModelList =sqlDatabaseHelper.getAllParticipents();

            dataList.setText("");
            for (ParticipentModel participent : participentModelList){
                dataList.append("ID : "+ participent.getID()+ " name: "+participent.getName()+" email: "+participent.getEmail());
            }

        });
        insert.setOnClickListener(v -> ShowInputDialog());


        update.setOnClickListener(v -> ShowUpdateIDDialog( ));
        delete.setOnClickListener(v -> showDeleteDialog());


    }

    private void showDeleteDialog(){
        AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
        sqlDatabaseHelper = new SqlDatabaseHelper(MainActivity.this);
        View view =  getLayoutInflater().inflate(R.layout.delete_layout,null);
        al.setView(view);
        EditText id_input = view.findViewById(R.id.id_input);
        al.show();
        Button delete_btn =view.findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(v -> sqlDatabaseHelper.deleteParticipent(id_input.getText().toString()));
    }

    private void ShowInputDialog(){
        AlertDialog.Builder  al = new AlertDialog.Builder(MainActivity.this);
        sqlDatabaseHelper = new SqlDatabaseHelper(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.insert_layout,null);
        EditText name = view.findViewById(R.id.name);
        EditText email = view.findViewById(R.id.email);
        Button insert_button = view.findViewById(R.id.insert_button);
        al.setView(view);
        insert_button.setOnClickListener(v -> {
            ParticipentModel participent = new ParticipentModel();
            participent.setName(name.getText().toString());
            participent.setEmail(email.getText().toString());
            sqlDatabaseHelper.addParticipant(participent);
        });
        al.show();
    }
    private void ShowUpdateIDDialog(){
        AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
        View view =  getLayoutInflater().inflate(R.layout.update_selection_layout,null);
        al.setView(view);
        final EditText id_input = view.findViewById(R.id.id_input);
        Button select =view.findViewById(R.id.select);
        final AlertDialog alertDialog =al.show();
        select.setOnClickListener(v ->
                ShowDataDialog(id_input.getText().toString()));


    }
    private void ShowDataDialog(String id){
        sqlDatabaseHelper = new SqlDatabaseHelper(MainActivity.this);

        ParticipentModel participentModel = sqlDatabaseHelper.getParticipent(Integer.parseInt(id));
        AlertDialog.Builder  al = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.update_layout,null);
        EditText name = view.findViewById(R.id.name);
        EditText email = view.findViewById(R.id.email);
        Button update_button = view.findViewById(R.id.update_btn);
        al.setView(view);
        name.setText(participentModel.getName());
        email.setText(participentModel.getEmail());
        update_button.setOnClickListener(v -> {

            ParticipentModel participent = new ParticipentModel();
            participent.setID(id);
            participent.setName(name.getText().toString());
            participent.setEmail(email.getText().toString());
            sqlDatabaseHelper.updateParticipent(participent);
            System.out.println("update button is working");
        });
        al.show();

    }

}