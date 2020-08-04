package com.example.gtc_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//imports do Firebase
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import Objetos.User;

public class MainActivity extends AppCompatActivity {

    private Button BtnLogin;
    private EditText txtLogin;
    private EditText txtSenha;
    private boolean isUserJaCadastrado;
    //firestore database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        BtnLogin = (Button) findViewById(R.id.btnLogin);
        txtLogin = (EditText) findViewById(R.id.txt_user_login);
        txtSenha = (EditText) findViewById(R.id.txt_senha_login);


        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtLogin.getText().toString().equals("") || txtSenha.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Existem campos vazios.", Toast.LENGTH_SHORT).show();
                } else if (txtLogin.getText().toString().equals("adm") && txtSenha.getText().toString().equals("adm")) {
                    Toast.makeText(MainActivity.this, "Adm", Toast.LENGTH_SHORT).show();
                } else if (txtLogin.getText().toString().equals("a") && txtSenha.getText().toString().equals("a")) {
                    Intent TelaDashMedico = new Intent(MainActivity.this, DashMedico.class);
//                    finish();
                    startActivity(TelaDashMedico);
                } else if ((txtLogin.getText().toString().equals("aa") && txtSenha.getText().toString().equals("aa"))) {
                    Intent analista = new Intent(MainActivity.this, DashAnalista.class);
                    startActivity(analista);
                } else {
                    DocumentReference docRef = db.collection("User").document(txtLogin.getText().toString());
                    docRef.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User pacienteBuscado = documentSnapshot.toObject(User.class);
                                    if (!documentSnapshot.exists()) {
                                        Toast.makeText(MainActivity.this, "Dados de Login incorretos", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent dashPaciente = new Intent(MainActivity.this, DashPaciente.class);
                                        Bundle dados = new Bundle();
                                        dados.putString("nome", pacienteBuscado.getNome());
                                        dados.putString("endereco", pacienteBuscado.getEndereco());
                                        dados.putString("telefone", pacienteBuscado.getTelefone());
                                        dados.putString("status", pacienteBuscado.getStatus());
                                        dashPaciente.putExtras(dados);
                                        startActivity(dashPaciente);
                                        //seta dados do paciente nos campos


                                    }
                                }

                            });


                }
            }
        });
    }


    // public boolean VerificaUsuarioCadastrado(final String userLogin, final String userSenha) {


//        DocumentReference docRef = db.collection("User").document(userLogin);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot documento = task.getResult();
//                    if (documento.getData() != null) {
//                        Toast.makeText(MainActivity.this, "Usuário já cadastrado!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(MainActivity.this, "Usuário não cadastrado!", Toast.LENGTH_SHORT).show();
//
//
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Erro ao realizar solicitação" + task.getException(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//        });
    //return isUserJaCadastrado;
}

//}
