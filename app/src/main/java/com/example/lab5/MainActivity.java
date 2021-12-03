package com.example.lab5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab5.db.AppDatabase;
import com.example.lab5.db.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserListAdapter userListAdapter;
    TextView totalRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button addNewUserButton = findViewById(R.id.addNewUserButton);
        addNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddNewUserActivity.class ), 100);
            }
        });

        EditText searchInput = findViewById(R.id.searchInput);
        final TextView foundRecords = findViewById(R.id.foundRecords);
        totalRecords = findViewById(R.id.totalRecords);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                findUserList(editable.toString());

                if(editable.toString().length() > 0) {
                    foundRecords.setVisibility(View.VISIBLE);
                    totalRecords.setVisibility(View.INVISIBLE);
                    foundRecords.setText("Found records: " + countFindUsers(editable.toString()));
                } else {
                    foundRecords.setVisibility(View.INVISIBLE);
                    totalRecords.setVisibility(View.VISIBLE);
                    totalRecords.setText("Total records: " + countAllUsers());
                }
            }
        });

        totalRecords.setText("Total records: " + countAllUsers());

        initRecyclerView();

        loadUserList();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapter = new UserListAdapter(this);
        recyclerView.setAdapter(userListAdapter);
    }
    private void loadUserList(){
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> userList = db.userDao().getAllUsers();
        userListAdapter.setUserList(userList);

    }
    private void findUserList(String searchText) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> userList = db.userDao().userSearch(searchText);

        userListAdapter.setUserList(userList);
    }

    private int countAllUsers() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        return db.userDao().allUsersNumber();
    }

    private int countFindUsers(String searchText) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        return db.userDao().usersCount(searchText);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100){
            loadUserList();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}