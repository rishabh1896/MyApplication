package com.example.rishabh.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Edit extends android.app.Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_edit, container, false);
        final EditText editHeading= (EditText) view.findViewById(R.id.editHeading);
        final EditText editNotes= (EditText) view.findViewById(R.id.editNotes);
        Button edit= (Button) view.findViewById(R.id.edit);
        String heading = getArguments().getString("Heading");
        String notes=getArguments().getString("Notes");
        final int position=getArguments().getInt("position");
        editHeading.setText(heading);
        editNotes.setText(notes);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newH=editHeading.getText().toString();
                String newN=editNotes.getText().toString();
                MyDatabase db=new MyDatabase(getActivity());
                db.updateNotes(position,newH,newN);
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

}
