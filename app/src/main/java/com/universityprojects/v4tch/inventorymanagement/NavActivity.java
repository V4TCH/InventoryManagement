package com.universityprojects.v4tch.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.*;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.*;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView signedInUsername;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText rackName, rackAmount;
    private Button add_button, cam_button;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rackName = (EditText)findViewById(R.id.et_rackName);
        rackAmount = (EditText)findViewById(R.id.et_rackAmount);
        add_button = (Button)findViewById(R.id.btn);
        cam_button = (Button)findViewById(R.id.cam_btn);
        recyclerView = (RecyclerView)findViewById(R.id.list);
        final Barcode qrcode = getIntent().getParcelableExtra("barcode");

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("inventories").push();
                Map<String, Object> map = new HashMap<>();
                map.put("id", databaseReference.getKey());
                map.put("rackName", rackName.getText().toString());
                map.put("rackAmount", rackAmount.getText().toString());
                databaseReference.setValue(map);
                fetch();
            }
        });

        cam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camIntent = new Intent(NavActivity.this, ScannerActivity.class);
                startActivity(camIntent);
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mAuth.signOut();
            Intent logoutIntent = new Intent(NavActivity.this, MainActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logoutIntent);
            Toast.makeText(NavActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inventory) {

        } else if (id == R.id.nav_sign_out) {
            mAuth.signOut();
            Intent logoutIntent = new Intent(NavActivity.this, MainActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logoutIntent);
            Toast.makeText(NavActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout root;
        TextView txtTitle, txtDesc;

         ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            txtTitle = itemView.findViewById(R.id.list_title);
            txtDesc = itemView.findViewById(R.id.list_desc);
        }

        void setTxtTitle(String string) {
            txtTitle.setText(string);
        }

        void setTxtDesc(String string) {
            txtDesc.setText(string);
        }
    }

    private void fetch() {
        DatabaseReference query = FirebaseDatabase.getInstance().getReference().child("inventories");

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, new SnapshotParser<Model>() {
                            @NonNull
                            @Override
                            public Model parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Model(snapshot.child("id").getValue().toString(),
                                        snapshot.child("rackName").getValue().toString(),
                                        snapshot.child("rackAmount").getValue().toString());
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, Model model) {
                holder.setTxtTitle(model.getmTitle());
                holder.setTxtDesc(model.getmDesc());

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(NavActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}