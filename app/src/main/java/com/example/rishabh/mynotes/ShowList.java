package com.example.rishabh.mynotes;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowList extends android.app.Fragment {
    ListView listView;
    MyDatabase db;
    SimpleCursorAdapter cursorAdapter;
    Cursor cursor;
    View view;
android.app.FragmentManager manager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_show_list, container, false);
        setHasOptionsMenu(true);
        manager=getFragmentManager();
        db = new MyDatabase(getActivity());
        cursor = db.getAllNotes();

        String [] columns = new String[] {
                MyDatabase.PERSON_COLUMN_ID,
                MyDatabase.Notes_COLUMN_Heading,
                MyDatabase.Notes_COLUMN_Notes
        };
        int [] widgets = new int[] {
                R.id.notesID,
                R.id.notesHeading,
                R.id.notesDetails
        };
         cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.row_notes,
                cursor, columns, widgets, 0);
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) ShowList.this.listView.getItemAtPosition(position);
                final int personID = itemCursor.getInt(itemCursor.getColumnIndex(MyDatabase.PERSON_COLUMN_ID));
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Choose action..");
                alertDialog.setMessage("What do you wanna do??");
                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     db.deleteNotes(personID);
                        cursorAdapter.changeCursor(cursor);
                        cursorAdapter.notifyDataSetChanged();
                    }
                });
                alertDialog.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor c=db.getNotes(personID);
                        c.moveToFirst();
                        String heading=c.getString(c.getColumnIndex(MyDatabase.Notes_COLUMN_Heading));
                        String notes=c.getString(c.getColumnIndex(MyDatabase.Notes_COLUMN_Notes));
                        Fragment b=new Edit();
                        Bundle args=new Bundle();
                        args.putString("Heading",heading);
                        args.putString("Notes",notes);
                        args.putInt("position",personID);
                        b.setArguments(args);
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.activity_login,b,"Edit");
                        transaction.addToBackStack("showList");
                        transaction.commit();
                    }
                });
                   alertDialog.create();
                alertDialog.show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        System.out.println("i am called");
        cursor=db.getAllNotes();
        cursorAdapter.notifyDataSetChanged();
        super.onResume();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            FragmentTransaction transaction=manager.beginTransaction();
            AddNotes fragment=new AddNotes();
            transaction.replace(R.id.activity_login,fragment,"AddNotes");
            transaction.addToBackStack("showList");
            transaction.commit();
        }
        if (item.getItemId() == R.id.signMeOut) {
            new SessionManager(getActivity()).logoutUser(manager);
        }
        return super.onOptionsItemSelected(item);
    }
}
