package com.example.nida.mychatapp;
import android.app.ProgressDialog;
import android.content.ComponentCallbacks2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.nida.mychatapp.Adapter.ListUserAdapter;
import com.example.nida.mychatapp.Common.Common;
import com.example.nida.mychatapp.Common.Common;
import com.example.nida.mychatapp.Holder.QBusersHolder;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import java.util.ArrayList;
public class ListUserActivity extends AppCompatActivity
{
    ListView FirstUsers;
    Button btnCreateChat;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
    
        retrieveallUSers();

        FirstUsers=(ListView)findViewById(R.id.FirstUser);

        FirstUsers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        btnCreateChat =(Button)findViewById(R.id.btn_create_chat);

        btnCreateChat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                int countChoice = FirstUsers.getCount();

                if (FirstUsers.getCheckedItemPositions().size() == 1)
                    createPrivateChat(FirstUsers.getCheckedItemPositions());

                else if (FirstUsers.getCheckedItemPositions().size() > 1)
                    createGroupChat(FirstUsers.getCheckedItemPositions());

                else
                    Toast.makeText(ListUserActivity.this, "Please select friend to chat ", Toast.LENGTH_SHORT).show();}});

    }

    private void createPrivateChat(SparseBooleanArray checkedItemPosition) {
        final ProgressDialog mdialogue = new ProgressDialog(ListUserActivity.this);
        mdialogue.setMessage("Waiting..");
        mdialogue.setCanceledOnTouchOutside(false);
        mdialogue.show();

        int countChoice = FirstUsers.getCount();

        for (int i = 0; i < countChoice; i++)
        {
            if (checkedItemPosition.get(i))
            {
                QBUser user = (QBUser) FirstUsers.getItemAtPosition(i);
                QBChatDialog dialog = DialogUtils.buildPrivateDialog(user.getId());

                QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                        mdialogue.dismiss();
                        Toast.makeText(getBaseContext(), "Private Chat box created successfully ", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.e("Error", e.getMessage());
                    }
                });
            }
        }
    }

    private void retrieveallUSers()
    {
        QBUsers.getUsers(null).performAsync(new QBEntityCallback<ArrayList<QBUser>>()
        {

            @Override
            public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle)
            {

                //Add to Cache
                QBusersHolder.getInstance().putUsers(qbUsers);

                ArrayList<QBUser> qbUserwithoutcurrent = new ArrayList<QBUser>();

                for(QBUser user:qbUsers)
                {
                    if(!user.getLogin().equals(QBChatService.getInstance().getUser().getLogin()))
                    qbUserwithoutcurrent.add(user);
                }

                ListUserAdapter adapter= new ListUserAdapter(getBaseContext(),qbUserwithoutcurrent);

                FirstUsers.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(QBResponseException e)
            {
                Log.e("Error",e.getMessage());
            }
        });

    }

    private void createGroupChat(SparseBooleanArray checkedItemPosition)
    {
        final ProgressDialog mdialogue=new ProgressDialog(ListUserActivity.this);
        mdialogue.setMessage("Waiting..");
        mdialogue.setCanceledOnTouchOutside(false);
        mdialogue.show();

        int countChoice=FirstUsers.getCount();
        ArrayList <Integer> occupantIdsList=new ArrayList<>();

        for(int i=0; i<countChoice; i++)
        {
            if(checkedItemPosition.get(i))

            {
                QBUser user=(QBUser)FirstUsers.getItemAtPosition(i);
                occupantIdsList.add(user.getId());
            }
        }

        //create chat dialogue

        QBChatDialog dialog =new QBChatDialog();
        dialog.setName(Common.createChatDialogName(occupantIdsList));
        dialog.setType(QBDialogType.GROUP);
        dialog.setOccupantsIds(occupantIdsList);

        QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>()
        {
            @Override
            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle)
            {
                mdialogue.dismiss();
                Toast.makeText(getBaseContext(),"Chat box created successfully ",Toast.LENGTH_SHORT).show();
                finish();}

            @Override
            public void onError(QBResponseException e)
            {
                Toast.makeText(getBaseContext(),"Chat box  not created ",Toast.LENGTH_SHORT).show();

                Log.e("Error",e.getMessage());

            }
        });
    }
}


