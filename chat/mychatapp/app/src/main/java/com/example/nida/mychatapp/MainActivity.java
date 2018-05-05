package com.example.nida.mychatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class MainActivity extends AppCompatActivity
{

    static final String APP_ID="69338";
    static final String AUTH_Key="tAc3AQHmDHH7Oj5";
    static final String AUTH_secret="SuJGWpH6TrJSLgR";
    static final String ACCOUNT_KEY="tau4qvg8s27UxwN7y-Eo";

    Button btn_login,btn_singup;
    EditText edt_user,edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFramework();


        //login and signup button
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_singup=(Button)findViewById(R.id.btn_main_signup);

        //password for login and sign up

        edt_password=(EditText)findViewById(R.id.edit_passwd);
        edt_user=(EditText)findViewById(R.id.edit_user);


        btn_singup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,SignupActivity.class));

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                final String user=edt_user.getText().toString();
                final String password=edt_password.getText().toString();

                QBUser qbUser=new QBUser(user,password);
                QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>()
                {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        Toast.makeText(getBaseContext(),"Login successfully",Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(MainActivity.this,ChatDialoguActivity.class);
                        intent.putExtra("User",user);
                        intent.putExtra("Password",password);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(QBResponseException e) {

                        Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });
    }

    private void initializeFramework()
    {
        QBSettings.getInstance().init(getApplicationContext(),APP_ID,AUTH_Key,AUTH_secret);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);

    }
}

