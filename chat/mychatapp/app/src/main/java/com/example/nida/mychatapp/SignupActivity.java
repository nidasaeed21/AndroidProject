package com.example.nida.mychatapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.List;

public class SignupActivity extends AppCompatActivity
{
    Button btnSignup,btnCancel;
    EditText edtUser,edtPassword,edtfullName;

    @Override


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        registerSession();

        btnSignup=(Button)findViewById(R.id.btn_signup);
        btnCancel=(Button)findViewById(R.id.btn_cancel);

        edtPassword=(EditText)findViewById(R.id.edit_Sign_passwd);
        edtUser=(EditText)findViewById(R.id.edit_Sign_user);

        edtfullName=(EditText)findViewById(R.id.sign_fullname);

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String user=edtUser.getText().toString();
                String password=edtPassword.getText().toString();

                QBUser qbUser=new QBUser(user,password);

                qbUser.setFullName(edtfullName.getText().toString());

                QBUsers.signUp(qbUser).performAsync(new QBEntityCallback<QBUser>()
                {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle)
                    {
                        Toast.makeText(getBaseContext(),"Sign up successfully ",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {

                       Toast.makeText(getBaseContext(),"Sign in failed",Toast.LENGTH_SHORT).show();
                        // error
                        Log.e("Error",e.getMessage());

                    }
                   });

            }
        });

    }


        private void registerSession ()
        {
            final  QBUser qbUser=new QBUser("user","password");
            QBUsers.signUp(qbUser).performAsync(new QBEntityCallback<QBUser>() {
            @Override

            public void onSuccess(QBUser user, Bundle args) {
                // success
            }

            @Override
            public void onError(QBResponseException error )
            {
                // error
                Log.e("Error",error.getMessage());
            }
        });
    }
 /*  private void registerSession()
    {
        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>()
        {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle)
            {

            }

            @Override
            public void onError(QBResponseException e)
            {
                Log.e("Error",e.getMessage());
            }
        });
    }
*/

}
