package com.sam.amman.rescue;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sam.amman.rescue.Actors.User;
import com.sam.amman.rescue.Adapters.DBHandler;
import com.sam.amman.rescue.Adapters.UserDBHandler;

import java.util.regex.Pattern;

public class RegActivity extends AppCompatActivity {


    UserDBHandler db;
    Spinner spinner;
    Button reg;
    EditText emailEdt,passwordEdt;
    String emailStr ;
    String passwordStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        emailEdt = (EditText) findViewById(R.id.emailReg);
        passwordEdt = (EditText) findViewById(R.id.passwordReg);
        reg = (Button) findViewById(R.id.BtnReg);
        db = new UserDBHandler(this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saving
                emailStr = emailEdt.getText().toString();
                passwordStr = passwordEdt.getText().toString();
                if("".equals(emailStr) || "".equals(passwordStr)){

                    Toast.makeText(RegActivity.this, "required fields should not be empty", Toast.LENGTH_SHORT).show();
                }else{

                    if(ValidEmail(emailStr)){
                        try {
                            db = new UserDBHandler(getApplication());
                            User user = new User();
                            user.setEmail(emailStr);
                            user.setPassword(passwordStr);
                            db.addUser(user);
                            db.close();
                            Toast.makeText(RegActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.w(" --- ", "onClick: ",e );
                            Toast.makeText(RegActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegActivity.this, "invalid email", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


//        spinner = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.country_codes, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean ValidEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
