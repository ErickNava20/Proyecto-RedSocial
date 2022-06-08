package com.uca.redsocialuca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;
import java.util.HashMap;

public class Registro extends AppCompatActivity {
    EditText Correo, Nombre, Contraseña, Edad, Apellidos;
    Button RegistrarUsuario;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar !=null;
        actionBar.setTitle("Registro");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        Correo = findViewById(R.id.Correo);
        Nombre = findViewById(R.id.Nombre);
        Contraseña = findViewById(R.id.contraseña);
        Edad = findViewById(R.id.Edad);
        Apellidos = findViewById(R.id.Apellido);
        RegistrarUsuario = findViewById(R.id.RegistarBoton);
        
        firebaseAuth = firebaseAuth.getInstance();
        RegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = Correo.getText().toString();
                String contraseña = Contraseña.getText().toString();
                
                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    Correo.setError("Correo no valido");
                    Correo.setFocusable(true);
                }else if (contraseña.length()<6){
                    Contraseña.setError("Contrasela debe ser maryor a 6 caracteres");
                    Contraseña.setFocusable(true);
                }else{
                    Registrar(correo,contraseña);
                }
            }
        });

    }

    private void Registrar(String correo, String contraseña) {
        firebaseAuth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                            assert user != null;
                            String uid = user.getUid();
                            String correo = Correo.getText().toString();
                            String contraseña = Contraseña.getText().toString();
                            String nombre = Nombre.getText().toString();
                            String apellido = Apellidos.getText().toString();
                            String edad = Edad.getText().toString();

                            HashMap <Object,String> DatosUsuario = new HashMap<>();

                            DatosUsuario.put("uid",uid);
                            DatosUsuario.put("correo",correo);
                            DatosUsuario.put("pass", contraseña);
                            DatosUsuario.put("nombre", nombre);
                            DatosUsuario.put("apellido", apellido);
                            DatosUsuario.put("edad", edad);

                            DatosUsuario.put("Imagen", "");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("USUARIOS_DE_APP");
                            reference.child(uid).setValue(DatosUsuario);
                            Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registro.this,Inicio.class));

                        }else {
                            Toast.makeText(Registro.this, "Algo salio mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registro.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}