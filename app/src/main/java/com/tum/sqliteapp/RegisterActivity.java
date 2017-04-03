package com.tum.sqliteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class RegisterActivity extends AppCompatActivity {

    DatabaseConnector dbHandler;
    String databasePath = "/data/data/com.tum.sqliteapp/databases/sqliteapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHandler = new DatabaseConnector(this);
    }

    protected void addUser(View view){
        // Adds a new user to the database
        // Retrieve user attributes from the activity
        String firstname = ((EditText) findViewById(R.id.EditText_register_firstname)).getText().toString();
        String lastname = ((EditText) findViewById(R.id.EditText_register_lastname)).getText().toString();
        String email = ((EditText) findViewById(R.id.EditText_register_email)).getText().toString();
        String phone = ((EditText) findViewById(R.id.EditText_register_phone)).getText().toString();
        String username = ((EditText) findViewById(R.id.EditText_register_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.EditText_register_password)).getText().toString();
        // Decrypt database for usage (if it exists)
        File database = new File(databasePath+"_enc");
        if(database.exists()) {
            Log.d("addUser()", "Decrypting database (Before INSERT)");
            BasicCrypto.decryptFile(databasePath+"_enc", databasePath, "you_like_that_!!", "AES");
        }
        // Call the addRecord function in the DatabaseConnector class
        dbHandler.addRecord(firstname, lastname, email, phone, username, BasicCrypto.hashMessage(password, "MD5"));
        Toast.makeText(this, "Record Added!", Toast.LENGTH_LONG).show();
        // Temporarily encrypt database
        Log.d("addUser()", "Encrypting database (after INSERT)");
        BasicCrypto.encryptFile(databasePath, databasePath+"_enc", "you_like_that_!!", "AES");
    }
}
