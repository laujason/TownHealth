package com.ormediagroup.xproject.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ormediagroup.xproject.JsonRespon;
import com.ormediagroup.xproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by YQ04 on 2018/4/13.
 */

public class LoginFragment extends Fragment {
    private final String packagename = "LoginFragment - ";
    public static int userid = 0;
    private EditText user_phone;
    private EditText user_psw;
    private TextView txt_error;
//    private boolean debug = true;
    private CheckBox cb_accept_login,cb_accept_register;
    String TownHealthDomain ;
    String gender;

    private EditText register_phonenum,register_phonenum_check,register_nameCN,register_nameENG,register_email,register_psw,register_psw_check,register_referrer,register_referrer_phone;
    private RadioButton register_gender_male,register_gender_female;
    private RadioGroup register_gender;

    private Toast mToast;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);

        user_phone = view.findViewById(R.id.et_mobile);
        user_psw = view.findViewById(R.id.et_psw);
        cb_accept_login = view.findViewById(R.id.accept_statement);
        txt_error = view.findViewById(R.id.txt_error);
        Button btn_login = view.findViewById(R.id.btn_userlogin);

        register_phonenum = view.findViewById(R.id.et_register_phonenum);
        register_phonenum_check = view.findViewById(R.id.et_register_phonenum_check);
        register_psw = view.findViewById(R.id.et_register_psw);
        register_psw_check = view.findViewById(R.id.et_register_psw_check);
        register_email = view.findViewById(R.id.et_register_email);
        register_nameCN = view.findViewById(R.id.et_register_nameCN);
        register_nameENG = view.findViewById(R.id.et_register_nameENG);
        register_referrer = view.findViewById(R.id.et_register_referrer);
        register_referrer_phone = view.findViewById(R.id.et_register_referrer_phone);
        register_gender = view.findViewById(R.id.rg_register_gender);
        register_gender_male = view.findViewById(R.id.rb_register_gender1);
        register_gender_female = view.findViewById(R.id.rb_register_gender2);

        cb_accept_register = view.findViewById(R.id.accept_statement2);

        Button btn_register = view.findViewById(R.id.btn_user_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_phone = user_phone.getText().toString();
                Log.d("ORM", "user phone:"+str_phone);
                String str_psw = user_psw.getText().toString();

                if (TextUtils.isEmpty(str_phone)) {
                    user_phone.setCursorVisible(true);
                    user_phone.setError("手機號碼不能為空");
                    return;
                }
                if (TextUtils.isEmpty(str_psw)) {
                    user_psw.setCursorVisible(true);
                    user_psw.setError("密碼不能為空");
                    return;
                }

                if (cb_accept_login.isChecked()){
                    User_login(str_phone, str_psw);
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "請先同意免責聲明及使用條款", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         *  用户注册
         */
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_regitser_phone = register_phonenum.getText().toString();
                String str_regitser_phone_check = register_phonenum_check.getText().toString();
                String str_regitser_psw = register_psw.getText().toString();
                String str_regitser_psw_check = register_psw_check.getText().toString();

                String str_regitser_email= register_email.getText().toString();
                String str_regitser_nameCN = register_nameCN.getText().toString();
                String str_regitser_nameENG = register_nameENG.getText().toString();

                String str_regitser_referrer = register_referrer.getText().toString();
                String str_regitser_referrer_phone = register_referrer_phone.getText().toString();

//              String gender = "男";

                /**
                 * Error Check
                 */
                if(TextUtils.isEmpty(str_regitser_phone)||TextUtils.isEmpty(str_regitser_nameCN)||TextUtils.isEmpty(str_regitser_nameENG)||TextUtils.isEmpty(str_regitser_email)
                        ||TextUtils.isEmpty(str_regitser_psw)||TextUtils.isEmpty(gender) ) {
                    if (TextUtils.isEmpty(str_regitser_phone)) {
                        register_phonenum.setFocusable(true);
                        register_phonenum.setFocusableInTouchMode(true);
                        register_phonenum.requestFocus();
                        //当前editText显示光标
                        //register_phonenum.setCursorVisible(true);
                        //设置Error提示
                        register_phonenum.setError("手機號碼不能為空");
                        //showToast("手機號碼不能為空");
                        return;
                    }
                    if (TextUtils.isEmpty(str_regitser_nameCN)) {
                        register_nameCN.setCursorVisible(true);
                        register_nameCN.setError("中文姓名不能為空");

                        return;
                    }
                    if (TextUtils.isEmpty(str_regitser_nameENG)) {
                        register_nameENG.setCursorVisible(true);
                        register_nameENG.setError("英文姓名不能為空");
                        return;
                    }
                    if (TextUtils.isEmpty(str_regitser_email)) {
                        register_email.setCursorVisible(true);
                        register_email.setError("E-mail不能為空");
                        return;
                    }
                    if (TextUtils.isEmpty(str_regitser_psw)) {
                        register_psw.setCursorVisible(true);
                        register_psw.setError("密碼不能為空");
                        return;
                    }
                    if (!TextUtils.isEmpty(gender)) {
                        showToast("性别不能為空");
                        return;
                    }

                }

                if (!str_regitser_phone.equals(str_regitser_phone_check)) {
                    register_phonenum_check.setCursorVisible(true);
                    register_phonenum_check.setError("手機號碼不一致");
                    return;
                }
                if (!str_regitser_psw.equals(str_regitser_psw_check)) {
                    register_psw_check.setCursorVisible(true);
                    register_psw_check.setError("用戶密码不一致");
                    return;
                }


                Log.i("ORM", "register用戶性别："+gender);
                if (cb_accept_register.isChecked() && str_regitser_phone.equals(str_regitser_phone_check) && str_regitser_psw.equals(str_regitser_psw_check) && !TextUtils.isEmpty(gender) ){
                    User_register(str_regitser_phone,str_regitser_psw,str_regitser_email,str_regitser_nameCN,
                            str_regitser_nameENG, gender,str_regitser_referrer,str_regitser_referrer_phone);
                } else{
//                    Toast.makeText(getActivity().getApplicationContext(), "請先同意免責聲明及使用條款", Toast.LENGTH_SHORT).show();
                    showToast("請先同意免責聲明及使用條款");
                }
            }
        });

        register_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_register_gender1:
                        gender = register_gender_male.getText().toString();

                        break;
                    case R.id.rb_register_gender2:
                        gender = register_gender_female.getText().toString();
                        break;
                }
            }
        });

        return view;

    }

    /**
     * 显示Toast信息
     * @param msg
     */
    private void showToast(String msg) {
        if(mToast == null){
            mToast = Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(msg);
        }
        mToast.show();
    }

    private void User_register(String phonenum,String psw, String email, String nameCN, String nameENG, String gender, String referrer,String referrer_phone) {
        TownHealthDomain = getString(R.string.Townhealth_domain);
        String URL = TownHealthDomain + "app/app-login/?action=reg&phone="+phonenum+"&pwd="+psw+"&name_zh="+nameCN+"&name_en="+nameENG+"&email="+email+"&gender="+gender+"&referrer="+referrer+"&referrerphone="+referrer_phone;
        new JsonRespon(getActivity(),URL, new JsonRespon.onComplete() {
            @Override
            public void onComplete(JSONObject json) {
                try{
                    Log.i("ORM",packagename+"register json to string"+json.toString());
                    showToast("Register Success");
                }catch (Exception e){
                    Log.e("ORM", "login Error : " + e.toString());
                }
            }
        });
    }

    /**
     * 用户登陆
     * @param str_phone
     * @param str_psw
     */
    private void User_login(String str_phone, String str_psw) {
        TownHealthDomain = getString(R.string.Townhealth_domain);
        String URL = TownHealthDomain + "app/app-login/?action=log&lphone="+str_phone+"&lpwd="+str_psw+"&debug=1";
//        Log.i("ORM","URL:"+URL);

        new JsonRespon(getActivity(),URL, new JsonRespon.onComplete() {
            @Override
            public void onComplete(JSONObject json) {
                try{
//                  loginJsonObject = json.getJSONObject("data");
                    Log.i("ORM",packagename+"Login json tostring"+json.toString());
                    showToast("Login Success");
                }catch (Exception e){
                    txt_error.setVisibility(View.VISIBLE);
                    Log.e("ORM", "login Error : " + e.toString());
                }
            }
        });

    }
}
