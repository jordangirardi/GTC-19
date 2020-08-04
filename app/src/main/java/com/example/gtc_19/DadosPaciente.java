package com.example.gtc_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import Objetos.User;
import io.grpc.Context;

import static java.lang.Boolean.getBoolean;

public class DadosPaciente extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_paciente);
        getSupportActionBar().hide();


        final EditText nomePaciente = (EditText) findViewById(R.id.nome_pac);
        final EditText enderecoPaciente = (EditText) findViewById(R.id.end_pac);
        final EditText telefonePaciente = (EditText) findViewById(R.id.tel_pac);
        final EditText telefoneEmergenciaPaciente = (EditText) findViewById(R.id.teleme_pac);
        final EditText email = (EditText) findViewById(R.id.email_pac);
        final EditText dataNascPaciente = (EditText) findViewById(R.id.datanasc_pac);
        final EditText statusAtual = (EditText) findViewById(R.id.status_teste);
        final EditText tipoSanguineo = (EditText) findViewById(R.id.tipo_sanguineo);


        final CheckBox checkProblemasRespiratorios = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox checkDiabetes = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox checkColesterol = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox checkHipertensao = (CheckBox) findViewById(R.id.checkBox4);
        final CheckBox checkAlergia = (CheckBox) findViewById(R.id.checkBox5);
        final CheckBox checkGestante = (CheckBox) findViewById(R.id.checkBox6);

        Button btnAlterarDados = (Button) findViewById(R.id.alterar_cad_pac);


        Intent telaDashAnalista = getIntent();
        final Bundle dados = telaDashAnalista.getExtras();
        final String EmailBusca = dados.getString("email");
        final int tipoUser = dados.getInt("tipoUser");

        DocumentReference docRef = db.collection("User").document(EmailBusca);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User pacienteBuscado = documentSnapshot.toObject(User.class);
                        if (!documentSnapshot.exists()) {
                            Toast.makeText(DadosPaciente.this, "Erro ao carregar dados do cliente", Toast.LENGTH_SHORT).show();
                        } else {
                            //seta dados do paciente nos campos
                            nomePaciente.setText(pacienteBuscado.getNome());
                            enderecoPaciente.setText(pacienteBuscado.getEndereco());
                            telefonePaciente.setText(pacienteBuscado.getTelefone());
                            telefoneEmergenciaPaciente.setText(pacienteBuscado.getTelefoneEmergencia());
                            email.setText(pacienteBuscado.getEmail());
                            dataNascPaciente.setText(pacienteBuscado.getDataNasc());
                            statusAtual.setText(pacienteBuscado.getStatus());
                            tipoSanguineo.setText(pacienteBuscado.getTiposanguineo());

                            if (pacienteBuscado.getIsAlergico() == 1) {
                                checkAlergia.setChecked(true);
                            }
                            if (pacienteBuscado.getIsColesterol() == 1) {
                                checkColesterol.setChecked(true);
                            }
                            if (pacienteBuscado.getIsProblemasRespiratorios() == 1) {
                                checkProblemasRespiratorios.setChecked(true);
                            }
                            if (pacienteBuscado.getIsDiabetico() == 1) {
                                checkDiabetes.setChecked(true);
                            }
                            if (pacienteBuscado.getIsHipertenso() == 1) {
                                checkHipertensao.setChecked(true);
                            }
                            if (pacienteBuscado.getIsGestante() == 1) {
                                checkGestante.setChecked(true);
                            }

                            //get iamgem do paciente
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(email.getText().toString());
                            ImageView imageView = findViewById(R.id.imageView6);
                            Glide.with(DadosPaciente.this)
                                    .load(storageReference)
                                    .into(imageView);
                        }
                    }

                });

        btnAlterarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tipoUser) {
                    case 1:
                        Intent AlterarStatusTeste = new Intent(DadosPaciente.this, CadastroPaciente2.class);
                        Bundle dados = new Bundle();
                        dados.putString("email", EmailBusca);
                        AlterarStatusTeste.putExtras(dados);
                        startActivity(AlterarStatusTeste);
                        break;

                    case 2:
                        //tela de cadastro com os dados já setados.

                    default:
                        Toast.makeText(DadosPaciente.this, "Erro ao processar solicitação", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
