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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioFragment extends Fragment {

    public UsuarioFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static UsuarioFragment newInstance(String param1, String param2) {

        return new UsuarioFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    TextView nombreUsu, telefono, reservas , correo, out;


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


        return view;

    }
}