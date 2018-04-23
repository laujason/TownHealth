package com.ormediagroup.xproject.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ormediagroup.xproject.JsonRespon;
import com.ormediagroup.xproject.R;

import org.json.JSONObject;

/**
 * Created by YQ04 on 2018/4/13.
 */

public class LoginFragment extends Fragment {
    public static int userid = 0;
    private EditText user_phone;
    private EditText user_psw;
    private TextView txt_error;
//    private boolean debug = true;
    private CheckBox cb_accept;
    String TownHealthDomain ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);

        user_phone = view.findViewById(R.id.et_mobile);
        user_psw = view.findViewById(R.id.et_psw);
        cb_accept = view.findViewById(R.id.accept_statement);
        txt_error = view.findViewById(R.id.txt_error);
        Button btn_login = view.findViewById(R.id.btn_userlogin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_phone = user_phone.getText().toString();
                Log.d("ORM", "user phone:"+str_phone);
                String str_psw = user_psw.getText().toString();


                if (cb_accept.isChecked()){
                    User_login(str_phone, str_psw);

                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "請先同意免責聲明", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

    private void User_login(String str_phone, String str_psw) {
        TownHealthDomain = getString(R.string.Townhealth_domain);
        String URL = TownHealthDomain + "app/app-login/?action=log&lphone="+str_phone+"&lpwd="+str_psw;
        new JsonRespon(getActivity(),URL, new JsonRespon.onComplete() {
            @Override
            public void onComplete(JSONObject json) {
                try{
//                    txt_error.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity().getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Log.e("ORM", "login Error : " + e.toString());
                }
            }
        });

    }
}
