package com.example.nida.mychatapp.Holder;

import android.content.Intent;
import android.util.SparseArray;

import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by nida on 4/29/18.
*/

import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class QBusersHolder
{
    private static QBusersHolder instance;
    private SparseArray<QBUser> qbUserSparseArray;
    public static synchronized QBusersHolder getInstance()
    {
        if(instance == null)
             instance=new QBusersHolder();
         return instance;
    }

    private QBusersHolder()
    {
         qbUserSparseArray=new SparseArray<>();
    }

    public void putUsers(List<QBUser> users)
    {
        for(QBUser user:users)
            putUser(user);
    }

    private void putUser(QBUser user)
    {
        qbUserSparseArray.put(user.getId(),user);

    }

    public QBUser getUserById(int id)
    {
        return qbUserSparseArray.get(id);
    }

    public List<QBUser> getUsersByIds(List<Integer> ids)
    {
        List<QBUser> qbUser = new ArrayList<>();
        for(Integer id:ids )
        {
            QBUser user=getUserById(id);
            if(user!=null)
                qbUser.add(user);

        }
        return qbUser;
    }
}
