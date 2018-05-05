package com.example.nida.mychatapp.Common;

import com.example.nida.mychatapp.Holder.QBusersHolder;
import com.quickblox.users.model.QBUser;

import java.util.List;

/**
 * Created by nida on 4/29/18.

*/
public class Common
{
        public static  String createChatDialogName(List<Integer> qbUsers)
        {
            List<QBUser>qbUsers1= QBusersHolder.getInstance().getUsersByIds(qbUsers);

            StringBuilder name=new StringBuilder();

            for(QBUser user:qbUsers1)

                name.append(user.getFullName()).append(" ");

            if(name.length()>30)

                name=name.replace(30,name.length()-1,".....");

            return name.toString();
        }
}
