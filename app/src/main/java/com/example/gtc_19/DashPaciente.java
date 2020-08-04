package com.example.gtc_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Objetos.User;

public class DashPaciente extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_paciente);
        getSupportActionBar().hide();


        final EditText nomePaciente = (EditText) findViewById(R.id.nome_pac_dashpac);
        final EditText enderecoPaciente = (EditText) findViewById(R.id.end_pac_dashpac);
        final EditText telefone = (EditText) findViewById(R.id.tel_pac_dashpac);
        final EditText StatusTeste = (EditText) findViewById(R.id.status_teste_pac_dashpac);

        Button btnVoltar = (Button) findViewById(R.id.btn_back_dashpac);

        Intent dadosLogin = getIntent();
        Bundle dados = dadosLogin.getExtras();

        nomePaciente.setText(dados.getString("nome"));
        enderecoPaciente.setText(dados.getString("endereco"));
        telefone.setText(dados.getString("telefone"));
        StatusTeste.setText(dados.getString("status"));

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
