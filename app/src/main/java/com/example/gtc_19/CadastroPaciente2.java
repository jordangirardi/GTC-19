package com.example.gtc_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import Objetos.User;

import static java.lang.Boolean.getBoolean;

public class CadastroPaciente2 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_paciente2);
        getSupportActionBar().hide();


        final EditText nomePaciente = (EditText) findViewById(R.id.cad_nome_pac_dashana);
        final EditText enderecoPaciente = (EditText) findViewById(R.id.cad_end_pac_dashana);
        final EditText telefonePaciente = (EditText) findViewById(R.id.cad_tel_pac_dashana);
        final EditText telefoneEmergenciaPaciente = (EditText) findViewById(R.id.cad_teleme_pac_dashana);
        final EditText email = (EditText) findViewById(R.id.cad_email_pac_dashana);
        final EditText dataNascPaciente = (EditText) findViewById(R.id.cad_datanasc_pac_dashana);
        final EditText tipoSanguineo = (EditText) findViewById(R.id.tiposangue_pac_dashana);

        final CheckBox checkProblemasRespiratorios = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox checkDiabetes = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox checkColesterol = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox checkHipertensao = (CheckBox) findViewById(R.id.checkBox4);
        final CheckBox checkAlergia = (CheckBox) findViewById(R.id.checkBox5);
        final CheckBox checkGestante = (CheckBox) findViewById(R.id.checkBox6);

        Button btnAtualizaStatus = (Button) findViewById(R.id.salvar_cad_pac_dashana);


        final Spinner status_teste = (Spinner) findViewById(R.id.spinner_teste);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.status_testecovid, android.R.layout.simple_spinner_item);
        status_teste.setAdapter(adapter);

        Intent DadosPaciente = getIntent();
        Bundle dados = DadosPaciente.getExtras();
        final String Emailbusca = dados.getString("email");

        DocumentReference docRef = db.collection("User").document(Emailbusca);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User pacienteBuscado = documentSnapshot.toObject(User.class);
                        if (!documentSnapshot.exists()) {
                            Toast.makeText(CadastroPaciente2.this, "Erro ao carregar dados do cliente", Toast.LENGTH_SHORT).show();
                        } else {
                            //seta dados do paciente nos campos
                            nomePaciente.setText(pacienteBuscado.getNome());
                            enderecoPaciente.setText(pacienteBuscado.getEndereco());
                            telefonePaciente.setText(pacienteBuscado.getTelefone());
                            telefoneEmergenciaPaciente.setText(pacienteBuscado.getTelefoneEmergencia());
                            email.setText(pacienteBuscado.getEmail());
                            dataNascPaciente.setText(pacienteBuscado.getDataNasc());
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

                        }
                    }

                });

        btnAtualizaStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> paciente = new HashMap<>();
//
//                int Diabetico = 0;
//                int Hipertenso = 0;
//                int Colesterol = 0;
//                int Alergico = 0;
//                int ProblemasRespiratorios = 0;
//                int Gestante = 0;
//
//                if (checkDiabetes.isChecked()) {
//                    Diabetico = 1;
//                }
//                if (checkHipertensao.isChecked()) {
//                    Hipertenso = 1;
//                }
//                if (checkColesterol.isChecked()) {
//                    Colesterol = 1;
//                }
//                if (checkAlergia.isChecked()) {
//                    Alergico = 1;
//                }
//                if (checkProblemasRespiratorios.isChecked()) {
//                    ProblemasRespiratorios = 1;
//                }
//                if (checkGestante.isChecked()) {
//                    Gestante = 1;
//                }
//
//
//                paciente.put("Nome", nomePaciente.getText().toString());
//                paciente.put("Endereco", enderecoPaciente.getText());
//                paciente.put("Telefone", telefonePaciente.getText().toString());
//                paciente.put("TelefoneEmergencia", telefoneEmergenciaPaciente.getText().toString());
//                paciente.put("Email", email.getText().toString());
//                paciente.put("DataNasc", dataNascPaciente.getText().toString());
//                paciente.put("TipoSanguineo", tipoSanguineo.getText().toString());
//                paciente.put("TipoUser", 0);
//                paciente.put("Status", status_teste.getSelectedItem().toString());
//                paciente.put("isProblemasRespiratorios", ProblemasRespiratorios);
//                paciente.put("isDiabetico", Diabetico);
//                paciente.put("isColesterol", Colesterol);
//                paciente.put("isHipertenso", Hipertenso);
//                paciente.put("isAlergico", Alergico);
//                paciente.put("isGestante", Gestante);


                db.collection("User").document(email.getText().toString())
                        .update("Status",status_teste.getSelectedItem().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CadastroPaciente2.this, "Status Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
//                                limparCampos();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CadastroPaciente2.this, "Erro ao atualizar status do paciente", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
