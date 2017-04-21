package com.example.rishabh.mynotes;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    FragmentManager manager;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getFragmentManager();
        session = new SessionManager(getApplicationContext());
        Boolean b=session.checkLogin(manager);
        if (b) {
            FragmentTransaction transaction = manager.beginTransaction();
            ShowList fragment = new ShowList();
            transaction.add(R.id.activity_login, fragment, "showList");
            transaction.commit();
        }
    }
    }
