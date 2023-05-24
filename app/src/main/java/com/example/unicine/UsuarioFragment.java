package com.example.unicine;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioFragment extends Fragment {

    public UsuarioFragment() {
        // Required empty public constructor
    }

    int cuentaTickets = 0;
    List<String> tickets;
    List<String> ticketsBorrados;
    boolean canUserBeDeleted;
    String userId;
    boolean bucleRecorrido = false;


    private long elapsedTime;

    // TODO: Rename and change types and number of parameters
    public static UsuarioFragment newInstance(long param1) {


        return new UsuarioFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    TextView nombreUsu, telefono, reservas , correo, out, eliminarCuenta;
    long startTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_usuario, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();
        }



        nombreUsu = (TextView) view.findViewById(R.id.textViewNombreUsuario);
        telefono = (TextView) view.findViewById(R.id.textViewTelefonoUsuario);
        correo = (TextView) view.findViewById(R.id.textViewEmailUsuario);
        out = (TextView) view.findViewById(R.id.textViewLogOut);
        reservas = (TextView) view.findViewById(R.id.textViewReservas);
        eliminarCuenta = (TextView)view.findViewById(R.id.textViewEliminarCuenta);


        onStart();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            String email = mAuth.getCurrentUser().getEmail();
            CollectionReference usersRef = db.collection("users");
            Query query = usersRef.whereEqualTo("email", email);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String corre = document.getString("email");
                            String nombre = document.getString("name");
                            String tele = document.getString("phone");

                            Log.d("TAG", corre);
                            Log.d("TAG", nombre);
                            Log.d("TAG", tele);
                            Log.d("TAG", email);

                            assert corre != null;
                            if (corre.equals(email)) {

                                nombreUsu.setText(nombre);
                                telefono.setText(tele);
                                correo.setText(corre);

                            }
                        }
                    } else {
                        Log.d("Error", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();

                Intent volver = new Intent(getContext(), Login.class);
                startActivity(volver);


            }
        });

        reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), Reservas.class);

                startActivity(intent);
            }
        });

        eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup();

            }
        });

        return view;

    }

    private void showPopup() {
        final Dialog popupDialog = new Dialog(getContext());
        popupDialog.setContentView(R.layout.poup_layout);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("users")
                .whereEqualTo("name", user.getDisplayName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userId = document.getId();
                                Log.d("User ID", "User ID for " + user.getDisplayName() + " is: " + userId);
                            }
                        } else {
                            Log.w("Firestore Error", "Error getting documents: ", task.getException());
                        }
                    }
                });

        final EditText editTextPopup = popupDialog.findViewById(R.id.edittext_popup);
        Button buttonPopup = popupDialog.findViewById(R.id.button_popup);

        buttonPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Acción a realizar al pulsar el botón "Aceptar"
                String inputText = editTextPopup.getText().toString();


                checkIfUserCanBeDeleted(userId, inputText);


                popupDialog.dismiss();
            }
        });

        popupDialog.show();
    }


    private void deleteUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // El usuario se eliminó correctamente

                                Toast.makeText(getContext(), "Ha eliminado su cuenta con exito", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), Login.class);
                                startActivity(intent);

                            } else {
                                // Hubo un error al eliminar el usuario
                                Toast.makeText(getContext(), "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void eliminarTicket(String idTicket) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("ticket").document(idTicket)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Aquí puedes obtener los atributos del documento
                                    List<String> asientosReser = (List<String>) document.get("Asientos");
                                    String idSesiones = document.getString("IdSesion");
                                    // Borrar los asientos del documento de "sesiones"
                                    db.collection("sesiones").document(idSesiones)
                                            .update("AsientosReservados", FieldValue.arrayRemove(asientosReser.toArray()))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "Asientos eliminados con éxito de la colección \"sesiones\"");

                                                    // Borrar el documento de "ticket"
                                                    db.collection("ticket").document(idTicket)
                                                            .delete()
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.d(TAG, "Documento eliminado con éxito de la colección \"ticket\"");

                                                                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                                                    FirebaseUser currentUser = mAuth.getCurrentUser();

                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.w(TAG, "Error al eliminar el documento de la colección \"ticket\"", e);
                                                                }
                                                            });

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error al eliminar los asientos de la colección \"sesiones\"", e);
                                                }
                                            });


                                } else {
                                    Log.d(TAG, "El documento no existe");
                                }
                            } else {
                                Log.w(TAG, "Error al obtener el documento", task.getException());
                            }
                        }
                    });
    }

    private void checkIfUserCanBeDeleted(String email, String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(email, password);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // El usuario se reautenticó correctamente, lo que indica que se puede eliminar
                                canUserBeDeleted = true;

                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user != null) {
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("users")
                                            .whereEqualTo("name", user.getDisplayName())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            // obtener el array de tickets
                                                            tickets = (List<String>) document.get("tickets");

                                                            // iterar sobre los IDs de tickets y ejecutar el método para eliminar cada uno
                                                            for (String idTicket : tickets) {
                                                                eliminarTicket(idTicket);
                                                                bucleRecorrido = true;
                                                                ticketsBorrados = (List<String>) document.get("tickets");
                                                            }

                                                                Log.d(TAG, "Size " + ticketsBorrados.size());

                                                                    db.collection("users")
                                                                            .whereEqualTo("name", user.getDisplayName())
                                                                            .get()
                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        // Si la consulta se realizó con éxito
                                                                                        if (!task.getResult().isEmpty()) {
                                                                                            // Obtener el id del primer documento que encontró
                                                                                            String userId = task.getResult().getDocuments().get(0).getId();

                                                                                            // Eliminar el documento completo en lugar de actualizar el campo "tickets"
                                                                                            db.collection("users").document(userId)
                                                                                                    .delete()
                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Void aVoid) {
                                                                                                            Log.d(TAG, "Documento de usuario eliminado con éxito");

                                                                                                            deleteUser();

                                                                                                        }
                                                                                                    })
                                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                                        @Override
                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                            Log.w(TAG, "Error al eliminar el documento de usuario", e);
                                                                                                        }
                                                                                                    });

                                                                                        }
                                                                                    }
                                                                                }
                                                                            });

                                                        }
                                                    } else {
                                                        Log.w(TAG, "Error al obtener los documentos", task.getException());
                                                    }
                                                }
                                            });
                                }

                            } else {
                                // No se pudo reautenticar al usuario, lo que indica que no se puede eliminar
                                canUserBeDeleted = false;
                                Toast.makeText(getContext(), "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();

                                // Aquí puedes mostrar un mensaje o realizar acciones basadas en el valor de canUserBeDeleted
                            }
                        }
                    });
        }
    }


}
