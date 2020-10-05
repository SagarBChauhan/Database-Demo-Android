package com.example.a201606100110041.databasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtContactNo;
    Button btnAdd, btnUpdate;
    ListView lstContacts;
    DatabaseHelper dbHelper;
    ArrayAdapter adapter;
    int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ControlsInitialization();
        ButtonClicks();
    }

    private void ControlsInitialization() {
        txtName = (EditText)findViewById(R.id.txtName);
        txtContactNo = (EditText)findViewById(R.id.txtContactNo);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        lstContacts = (ListView)findViewById(R.id.lstContact);

        dbHelper = new DatabaseHelper(this);
        BindData();
    }

    private void ButtonClicks()
    {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.AddContacts(new Contact(txtName.getText().toString(),txtContactNo.getText().toString()));
                Toast.makeText(getApplicationContext(),"Contacts Added Successfully",Toast.LENGTH_SHORT).show();
                ClearData();
                BindData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.UpdateContact(new Contact(ID,txtName.getText().toString(),txtContactNo.getText().toString()));
                Toast.makeText(getApplicationContext(),"Contacts Updated Successfully",Toast.LENGTH_SHORT).show();
                ClearData();
                BindData();
            }
        });
    }

    private void BindData()
    {
        ArrayList<String> ContactList = dbHelper.GetAllContacts();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,ContactList);
        lstContacts.setAdapter(adapter);
        registerForContextMenu(lstContacts);

    }

    private void ClearData()
    {
        txtName.setText("");
        txtContactNo.setText("");

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Option");
        menu.add(0,v.getId(),0,"Update");
        menu.add(0,v.getId(),0,"Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle() == "Update") {
            AdapterView.AdapterContextMenuInfo menuzinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int listPosition = menuzinfo.position;
            String SelectedItem = adapter.getItem(listPosition).toString();
            String[] SeperatedColumn = SelectedItem.split(",");
            ID = Integer.parseInt(SeperatedColumn[0]);
            Contact contact = dbHelper.getContactDetails(ID);
            txtName.setText(contact.getName());
            txtContactNo.setText(contact.getContactNo());
        }else if(item.getTitle() == "Delete")
        {
            AdapterView.AdapterContextMenuInfo menuzinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int listPosition = menuzinfo.position;
            String SelectedItem = adapter.getItem(listPosition).toString();
            String[] SeperatedColumn = SelectedItem.split(",");
            ID = Integer.parseInt(SeperatedColumn[0]);
            dbHelper.DeleteContact(new Contact(ID));
            Toast.makeText(getApplicationContext(),"Contacts Deleted Successfully",Toast.LENGTH_SHORT).show();
            BindData();
        }
        return true;

    }
}
