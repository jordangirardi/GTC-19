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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Objetos.User;

public class DashAnalista extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_analista);
        getSupportActionBar().hide();


        Button btnBuscarPaciente = (Button) findViewById(R.id.btn_busca_pac_dashana);
        final EditText emailBusca = (EditText) findViewById(R.id.busca_pac_dashana);


//      busca do paciente
        btnBuscarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = db.collection("User").document(emailBusca.getText().toString());
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User pacienteBuscado = documentSnapshot.toObject(User.class);
                        if (!documentSnapshot.exists()) {
                            Toast.makeText(DashAnalista.this, "Paciente não encontrado.", Toast.LENGTH_SHORT).show();
                        } else {
                            verificaTipoUsuario(pacienteBuscado);
                        }
                    }

                });
            }
        });
    }

    public void verificaTipoUsuario(User pacienteBuscado) {
        switch (pacienteBuscado.getTipoUser()) {
            case 0:
                Toast.makeText(DashAnalista.this, "paciente", Toast.LENGTH_SHORT).show();
                Intent telaDadosPaciente = new Intent(DashAnalista.this, DadosPaciente.class);
                Bundle dados = new Bundle();
                dados.putString("email", pacienteBuscado.getEmail());
                dados.putInt("tipoUser",1);
                telaDadosPaciente.putExtras(dados);
                startActivity(telaDadosPaciente);
                break;

            default:
                Toast.makeText(DashAnalista.this, "Usuario Informado não é um paciente", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}