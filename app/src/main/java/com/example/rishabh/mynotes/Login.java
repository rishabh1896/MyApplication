package com.example.rishabh.mynotes;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Login extends android.app.Fragment {
    private HashMap<String,String> details;
    SessionManager session;
    FragmentManager manager;
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState)
   {

      // session=new SessionManager(getApplicationContext());
       View view=inflator.inflate(R.layout.fragment_login,container,false);
       final EditText email= (EditText) view.findViewById(R.id.enterEmail);
       final EditText password= (EditText) view.findViewById(R.id.enterPassword);
       session=new SessionManager(getActivity());
       manager=getFragmentManager();
       Button login= (Button) view.findViewById(R.id.Login);
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String getemail=email.getText().toString();
               String getpassword=password.getText().toString();

               if(getemail.trim().length() > 0 && getpassword.trim().length() > 0){
                   // For testing puspose username, password is checked with sample data
                   // username = test
                   // password = test
                   if(getemail.equals("test@gmail.com") && getpassword.equals("test")){
                       session.createLoginSession("test@gmail.com", "test");
                       Intent i = new Intent(getActivity(), MainActivity.class);
                       FragmentTransaction transaction=manager.beginTransaction();
                       transaction.remove(manager.findFragmentByTag("Login"));
                       transaction.commit();
                       startActivity(i);

           }
                   else
                   {
                       Toast.makeText(getActivity(),"Please enter email=test@gmail.com and password=test",Toast.LENGTH_LONG).show();
                   }
       }
           else
               {
                   Toast.makeText(getActivity(),"Please enter email and password",Toast.LENGTH_SHORT).show();
               }
           }});
       return view;
   }



}
