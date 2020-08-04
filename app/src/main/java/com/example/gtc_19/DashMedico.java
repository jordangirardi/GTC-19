package com.example.gtc_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Objetos.User;

public class DashMedico extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_medico);
        getSupportActionBar().hide();

        final EditText emailBusca = (EditText) findViewById(R.id.busca_pac);
        Button btn_cadastrar_pac = (Button) findViewById(R.id.btn_cad_pac);
        Button btn_buscar_pac = (Button) findViewById(R.id.btn_busca_pac);


        //cadastro de novo paciente
        btn_cadastrar_pac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaCadastroPaciente = new Intent(DashMedico.this, CadastroPaciente.class);
                startActivity(telaCadastroPaciente);
            }
        });


        btn_buscar_pac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailBusca.getText().toString().equals("")) {
                    Toast.makeText(DashMedico.this, "É necessário informar um e-mail",Toast.LENGTH_SHORT).show();
                } else {
                    DocumentReference docRef = db.collection("User").document(emailBusca.getText().toString());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User pacienteBuscado = documentSnapshot.toObject(User.class);
                            if (!documentSnapshot.exists()) {
                                Toast.makeText(DashMedico.this, "Paciente não encontrado.", Toast.LENGTH_SHORT).show();
                            } else {
                                verificaTipoUsuario(pacienteBuscado);
                            }
                        }

                    });
                }

            }
        });
    }

    public void verificaTipoUsuario(User pacienteBuscado) {
        switch (pacienteBuscado.getTipoUser()) {
            case 0:
                Intent telaDadosPaciente = new Intent(DashMedico.this, DadosPaciente.class);
                Bundle dados = new Bundle();
                dados.putString("email", pacienteBuscado.getEmail());
                dados.putInt("tipoUser",2);
                telaDadosPaciente.putExtras(dados);
                startActivity(telaDadosPaciente);
                break;

            default:
                Toast.makeText(DashMedico.this, "Usuario Informado não é um paciente", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
