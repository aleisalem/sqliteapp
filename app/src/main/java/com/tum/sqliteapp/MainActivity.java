package com.tum.sqliteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseConnector dbHandler;
    final String databasePath = "/data/data/com.tum.sqliteapp/databases/sqliteapp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DatabaseConnector(this);
    }

    protected void loginUser(View view){
        // Decrypt database for usage (if it exists)
        File database = new File(databasePath+"_enc");
        if(database.exists()) {
            Log.d("loginUser()", "Decrypting database (before SELECT)");
            BasicCrypto.decryptFile(databasePath + "_enc", databasePath, "you_like_that_!!", "AES");
        }
        // Attempt to login user
        String username = ((EditText) findViewById(R.id.EditText_main_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.EditText_main_password)).getText().toString();
        // Call the "getRecord" function of the DatabaseConnector to retrieve user
        try {
            List<String> user = dbHandler.getRecord(username, BasicCrypto.hashMessage(password, "MD5"));
            if (user.isEmpty())
                // Could not retrieve a user with such credentials
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show();
            else {
                // Retrieve and display data
                Toast.makeText(this, "Welcome back \"" + ((String) user.get(0)) + " " + ((String) user.get(1)) + "\"\n" + "Email : " + ((String) user.get(2)), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show();
        }
        // Temporarily encrypt database
        Log.d("loginUser()", "Encrypting database (after SELECT)");
        BasicCrypto.encryptFile(databasePath, databasePath+"_enc", "you_like_that_!!", "AES");

    }

    protected void toRegisterScreen(View view){
        // Switch to the register activity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
