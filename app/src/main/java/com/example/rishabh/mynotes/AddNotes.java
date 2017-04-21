package com.example.rishabh.mynotes;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddNotes extends android.app.Fragment {
    private EditText notes,heading;
    private Button submit;
    private MyDatabase myDatabase;
    private FragmentManager manager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_notes, container, false);
        manager=getFragmentManager();
        myDatabase=new MyDatabase(getActivity());
        notes= (EditText) view.findViewById(R.id.notes);
        submit= (Button) view.findViewById(R.id.SubmitNotes);
        heading=(EditText) view.findViewById(R.id.heading);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getHeading=heading.getText().toString();
                String getNotes=notes.getText().toString();
                if(!getHeading.isEmpty()&&!getNotes.isEmpty()) {
                    Boolean b = myDatabase.insertNotes(getHeading, getNotes);
                    if (b) {
                        Toast.makeText(getActivity(), "Notes added succesfully", Toast.LENGTH_SHORT).show();
                        manager.popBackStack();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Please enter the fields..",Toast.LENGTH_SHORT).show();
                }
               }
        });
        return view;
    }


}
