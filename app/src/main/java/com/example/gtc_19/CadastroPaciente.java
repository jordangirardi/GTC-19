package com.example.gtc_19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class CadastroPaciente extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView foto_paciente;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_paciente);
        getSupportActionBar().hide();

        final Button btn_save_new_paciente = (Button) findViewById(R.id.salvar_cad_pac);


        final EditText nomePaciente = (EditText) findViewById(R.id.cad_nome_pac);
        final EditText enderecoPaciente = (EditText) findViewById(R.id.cad_end_pac);
        final EditText telefonePaciente = (EditText) findViewById(R.id.cad_tel_pac);
        final EditText telefoneEmergenciaPaciente = (EditText) findViewById(R.id.cad_teleme_pac);
        final EditText emailPaciente = (EditText) findViewById(R.id.cad_email_Pac);
        final EditText dataNascPaciente = (EditText) findViewById(R.id.cad_datanasc_pac);
        final CheckBox checkProblemasRespiratorios = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox checkDiabetes = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox checkColesterol = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox checkHipertensao = (CheckBox) findViewById(R.id.checkBox4);
        final CheckBox checkAlergia = (CheckBox) findViewById(R.id.checkBox5);
        final CheckBox checkGestante = (CheckBox) findViewById(R.id.checkBox6);


        foto_paciente = (ImageView) findViewById(R.id.fotoPaciente);
        Button btnCapturarFoto = (Button) findViewById(R.id.btn_tirarFoto);


        //configuração do spinner com os tipos sanguíneos
        final Spinner tipoSanguineo = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_sang, android.R.layout.simple_spinner_item);
        tipoSanguineo.setAdapter(adapter);


        btn_save_new_paciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> paciente = new HashMap<>();

                int Diabetico = 0;
                int Hipertenso = 0;
                int Colesterol = 0;
                int Alergico = 0;
                int ProblemasRespiratorios = 0;
                int Gestante = 0;

                if (checkDiabetes.isChecked()) {
                    Diabetico = 1;
                }
                if (checkHipertensao.isChecked()) {
                    Hipertenso = 1;
                }
                if (checkColesterol.isChecked()) {
                    Colesterol = 1;
                }
                if (checkAlergia.isChecked()) {
                    Alergico = 1;
                }
                if (checkProblemasRespiratorios.isChecked()) {
                    ProblemasRespiratorios = 1;
                }
                if (checkGestante.isChecked()) {
                    Gestante = 1;
                }


                paciente.put("Nome", nomePaciente.getText().toString());
                paciente.put("Endereco", enderecoPaciente.getText().toString());
                paciente.put("Telefone", telefonePaciente.getText().toString());
                paciente.put("TelefoneEmergencia", telefoneEmergenciaPaciente.getText().toString());
                paciente.put("Email", emailPaciente.getText().toString());
                paciente.put("DataNasc", dataNascPaciente.getText().toString());
                paciente.put("TipoSanguineo", tipoSanguineo.getSelectedItem().toString());
                paciente.put("TipoUser", 0);
                paciente.put("Status", "Em análise");
                paciente.put("isProblemasRespiratorios", ProblemasRespiratorios);
                paciente.put("isDiabetico", Diabetico);
                paciente.put("isColesterol", Colesterol);
                paciente.put("isHipertenso", Hipertenso);
                paciente.put("isAlergico", Alergico);
                paciente.put("isGestante", Gestante);


                db.collection("User").document(emailPaciente.getText().toString())
                        .set(paciente)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CadastroPaciente.this, "Paciente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
//                                limparCampos();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CadastroPaciente.this, "Erro ao cadastrar paciente", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Create a storage reference from our app
                mStorageRef = FirebaseStorage.getInstance().getReference();


// Create a reference to "mountains.jpg"
                final StorageReference imgRef = mStorageRef.child(emailPaciente.getText().toString());

// Create a reference to 'images/mountains.jpg'
//                StorageReference imgRefCompleta = mStorageRef.child("images/mountains.jpg");
//
//// While the file names are the same, the references point to different files
//                imgRef.getName().equals(imgRefCompleta.getName());    // true
//                imgRef.getPath().equals(imgRefCompleta.getPath());    // false

                // Get the data from an ImageView as bytes
                foto_paciente.setDrawingCacheEnabled(true);
                foto_paciente.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) foto_paciente.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = imgRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(CadastroPaciente.this,"Ocorreu um erro ao salvar os arquivos", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CadastroPaciente.this,"Sucesso ao salvar arquivo", Toast.LENGTH_SHORT).show();
                        imgRef.getDownloadUrl();
                    }
                });


            }
        });

        btnCapturarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapturarImagem();
            }
        });
    }

    public void CapturarImagem() {

        Intent Camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(Camera, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle dados = data.getExtras();
            Bitmap imagem = (Bitmap) dados.get("data");
            foto_paciente.setImageBitmap(imagem);
        }
    }
}

