package com.ormediagroup.xproject.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ormediagroup.xproject.Adapters.QuestionnaireAdapter;
import com.ormediagroup.xproject.JsonRespon;
import com.ormediagroup.xproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by YQ04 on 2018/4/25.
 */

public class QuestionnaireFragment extends Fragment{
    private final String packagename = "QuestionnaireFragment - ";
    private RecyclerView ques_content;
    private QuestionnaireAdapter adapter;
    private String QuesURL = "https://thhealthmgt.com/app/app-questionnaire/";
//    private View ques_item;
    String TownHealthDomain;
    public JSONArray info_JsonArray,family_JsonArray,personal_JsonArray,tooth_JsonArray;
    public ArrayList<JSONArray> itemJsonArray;
    private Calendar myCalendar;
    public String[] testChoice;
    public SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questionnaire,container,false);
        ques_content = view.findViewById(R.id.rv_ques_content);
//        ques_item = view.findViewById(R.id.ques_itemlist);

        initView();
        return view;
    }

    private void initView() {
        TownHealthDomain = getString(R.string.Townhealth_domain);
        String URL =  TownHealthDomain+"app/app-questionnaire/";

       /* new JsonRespon(getActivity(), URL, new JsonRespon.onComplete() {
            @Override
            public void onComplete(JSONObject json) {
                try {
                    itemJsonArray = new ArrayList<JSONArray>();
                    info_JsonArray = new JSONArray();
                    family_JsonArray = new JSONArray();
                    personal_JaonArray = new JSONArray();

                    info_JsonArray = json.getJSONArray("basic");
                    family_JsonArray = json.getJSONArray("family");
                    personal_JaonArray = json.getJSONArray("personal");
                    Log.d("ORM","Personal JSon"+personal_JaonArray.length());
//                  ques_JsonArray = json.getJSONArray("personal");
                    for (int i = 0; i < info_JsonArray.length(); i++) {
                        itemJsonArray.add(info_JsonArray.getJSONArray(i));
                        Log.d("ORM",packagename+"info_JsonArray data:"+info_JsonArray.getJSONArray(i).get(1).toString());
                    }
                    for (int j = 0;j < family_JsonArray.length();j++){
                        itemJsonArray.add(family_JsonArray.getJSONArray(j));
                    }

                    Log.i("ORM",packagename+"itemJsonArray data:"+itemJsonArray.toString());
                    ques_content.setLayoutManager(new LinearLayoutManager(getActivity()));
//                    ques_content.setAdapter(new ContentAdapter(itemJsonArray));

                } catch (Exception e) {
//                    e.printStackTrace();
                    Log.e("ORM",  packagename+"Get json error: " +e.toString());
                }
            }

        });*/
        ques_content.setLayoutManager(new LinearLayoutManager(getActivity()));
        ques_content.setAdapter(new ContentAdapter());

    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {
        /*private ArrayList<JSONArray> list;
        public ContentAdapter(ArrayList<JSONArray> list){
            this.list = list;

        }*/
        @Override
        public QuestionnaireFragment.ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new QuestionnaireFragment.ContentAdapter.ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_personalinfo_list, parent, false));
        }

        @Override
        public void onBindViewHolder(final QuestionnaireFragment.ContentAdapter.ContentHolder holder, int position) {

            new JsonRespon(getActivity(), QuesURL, new JsonRespon.onComplete() {
                @Override
                public void onComplete(JSONObject json) {
                    try {
                        itemJsonArray = new ArrayList<JSONArray>();
                        info_JsonArray = new JSONArray();
                        family_JsonArray = new JSONArray();
                        personal_JsonArray = new JSONArray();
                        info_JsonArray = json.getJSONArray("basic");
                        family_JsonArray = json.getJSONArray("family");
                        personal_JsonArray = json.getJSONArray("personal");

                        Log.d("ORM","family_JsonArray length"+personal_JsonArray.getJSONArray(0).get(1).toString());
                        Log.d("ORM","personal_JaonArray string"+personal_JsonArray.getJSONArray(76).get(0).toString());
                        /**
                         * Personal information
                         */
                        for (int i = 0; i < info_JsonArray.length(); i++) {
                            Log.d("ORM","debug ss"+info_JsonArray.length());
                            Log.i("ORM","json Array"+info_JsonArray.getJSONArray(1).toString());
                            Log.i("ORM","json Array item "+info_JsonArray.getJSONArray(0).get(1).toString());

                            switch (info_JsonArray.getJSONArray(i).get(1).toString()){
                                case "text":
                                    TextView tv=new TextView(getActivity());
                                    tv.setText(info_JsonArray.getJSONArray(i).get(0).toString());
                                    EditText et=new EditText(getActivity());
                                    TextView tv1 = new TextView(getActivity());
//                            et.setWidth(800);
                                    holder.layout_personal_info.addView(tv);
                                    holder.layout_personal_info.addView(et);
                                    break;
                                case "radio":
                                    RadioGroup group = new RadioGroup(getActivity());
                                    for(int j=0;j<info_JsonArray.getJSONArray(i).getJSONArray(4).length();j++){
                                        RadioButton rb = new RadioButton(getActivity());
                                        rb.setText(info_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        group.addView(rb);
                                        if (info_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                            EditText et2=new EditText(getActivity());
                                            et2.setWidth(1000);
                                            et2.setId(R.id.register_manager);
                                            group.addView(et2);
                                        }

                                    }
                                    TextView tv_radio=new TextView(getActivity());
                                    tv_radio.setText(info_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_personal_info.addView(tv_radio);
                                    holder.layout_personal_info.addView(group);
                                    break;
                                case "date":
                                    TextView tv_date=new TextView(getActivity());
                                    tv_date.setText(info_JsonArray.getJSONArray(i).get(0).toString());
                                    EditText et_date=new EditText(getActivity());
                                    holder.layout_personal_info.addView(tv_date);
                                    holder.layout_personal_info.addView(et_date);
                                    et_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {
                                            Toast.makeText(getActivity().getApplicationContext(),"填写日期", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                default:
                                    break;
                            }
                        }

                        /**
                         * family history
                         */
                        for (int i = 0; i < family_JsonArray.length(); i++) {
                            TextView tv_multiple = new TextView(getActivity());
                            tv_multiple.setText(family_JsonArray.getJSONArray(i).get(0).toString());
                            holder.layout_family_history.addView(tv_multiple);
                            for(int j=0;j<family_JsonArray.getJSONArray(i).getJSONArray(4).length();j++) {
                                CheckBox Cb = new CheckBox(getActivity());
                                Cb.setText(family_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                holder.layout_family_history.addView(Cb);
                                if (family_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                    EditText et2=new EditText(getActivity());
                                    et2.setWidth(1000);
                                    holder.layout_family_history.addView(et2);
                                }
                            }

                        }
                        /**
                         *  personal history
                         */
                        for (int i = 0; i < 10; i++) {
                            Log.i("ORM","switch value:  "+personal_JsonArray.getJSONArray(i).get(1).toString());
                            switch (personal_JsonArray.getJSONArray(i).get(1).toString()){
                                case "text":
                                    TextView tv=new TextView(getActivity());
                                    tv.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    EditText et=new EditText(getActivity());
//                                  et.setWidth(800);
                                    holder.layout_personal_history.addView(tv);
                                    holder.layout_personal_history.addView(et);
                                    break;
                                case "radio":
                                    TextView tv_radio=new TextView(getActivity());
                                    tv_radio.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    final int x =i;
//                                    final TextView tv_radio_value=new TextView(getActivity());
                                    final EditText et_radio=new EditText(getActivity());
                                    et_radio.setWidth(800);
                                    et_radio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {
                                            try {
                                                testChoice = new String[personal_JsonArray.getJSONArray(x).getJSONArray(4).length()];
                                                for(int j=0;j<personal_JsonArray.getJSONArray(x).getJSONArray(4).length();j++){
                                                    testChoice[j] = personal_JsonArray.getJSONArray(x).getJSONArray(4).get(j).toString();
                                                }
                                                new AlertDialog.Builder(getActivity()).setTitle(personal_JsonArray.getJSONArray(x).get(0).toString())
                                                        .setSingleChoiceItems(testChoice, 0,
                                                                new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which
                                                                    ) {
                                                                        // If the user checked the item, add it to the selected items
                                                                        et_radio.setText(testChoice[which]);
                                                                    }
                                                                })
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                            }
                                                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //  tv_radio_value.setText("任何面積");
                                                    }
                                                }).show();
                                            } catch (JSONException e) {
                                                Log.e("ORM",packagename+"AlertDialog Error :"+e.toString());
                                            }
                                        }
                                    });
                                    holder.layout_personal_history.addView(tv_radio);
                                    holder.layout_personal_history.addView(et_radio);
                                    break;
                                case "multiple":
                                    TextView tv_multiple=new TextView(getActivity());
                                    tv_multiple.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_personal_history.addView(tv_multiple);
                                    for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++) {
                                        CheckBox Cb = new CheckBox(getActivity());
                                        Cb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        holder.layout_personal_history.addView(Cb);
                                        if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                            EditText et2=new EditText(getActivity());
                                            et2.setWidth(1000);
                                            holder.layout_personal_history.addView(et2);
                                        }
                                    }

                                    break;
                                default:
                                    break;

                            }
                        }
                        /**
                         * part 4 last_six_months_disease
                         */
                        for (int i =12 ; i<20;i++){
                            switch (personal_JsonArray.getJSONArray(i).get(1).toString()){
                                case "text":
                                    TextView tv=new TextView(getActivity());
                                    tv.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    EditText et=new EditText(getActivity());
//                                  et.setWidth(800);
                                    holder.layout_last_six_months_disease.addView(tv);
                                    holder.layout_last_six_months_disease.addView(et);
                                    break;
                                case "radio":
                                    RadioGroup group = new RadioGroup(getActivity());
                                    for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++){
                                        RadioButton rb = new RadioButton(getActivity());
                                        rb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        group.addView(rb);
                                        if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                            EditText et2=new EditText(getActivity());
                                            et2.setWidth(1000);
                                            group.addView(et2);
                                        }
                                    }
                                    TextView tv_radio=new TextView(getActivity());
                                    tv_radio.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_last_six_months_disease.addView(tv_radio);
                                    holder.layout_last_six_months_disease.addView(group);
                                    break;
                                case "multiple":
                                    TextView tv_multiple=new TextView(getActivity());
                                    tv_multiple.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_last_six_months_disease.addView(tv_multiple);
                                    for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++) {
                                        CheckBox Cb = new CheckBox(getActivity());
                                        Cb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        holder.layout_last_six_months_disease.addView(Cb);
                                        if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                            EditText et2=new EditText(getActivity());
                                            et2.setWidth(1000);
                                            holder.layout_last_six_months_disease.addView(et2);
                                        }
                                    }
                                    break;
                            }
                        }

                        /**
                         * part 5 tooth_history
                         */
                        for (int i =21 ; i<24;i++){
                            switch (personal_JsonArray.getJSONArray(i).get(1).toString()){
                                case "text":
                                    TextView tv=new TextView(getActivity());
                                    tv.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    EditText et=new EditText(getActivity());
//                                  et.setWidth(800);
                                    holder.layout_tooth_history.addView(tv);
                                    holder.layout_tooth_history.addView(et);
                                    break;
                                case "radio":
                                    RadioGroup group = new RadioGroup(getActivity());
                                    for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++){
                                        RadioButton rb = new RadioButton(getActivity());
                                        rb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        group.addView(rb);
                                        if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                            EditText et2=new EditText(getActivity());
                                            et2.setWidth(1000);
                                            group.addView(et2);
                                        }
                                    }
                                    TextView tv_radio=new TextView(getActivity());
                                    tv_radio.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_tooth_history.addView(tv_radio);
                                    holder.layout_tooth_history.addView(group);
                                    break;
                                case "multiple":
                                    TextView tv_multiple=new TextView(getActivity());
                                    tv_multiple.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_tooth_history.addView(tv_multiple);
                                    for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++) {
                                        CheckBox Cb = new CheckBox(getActivity());
                                        Cb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        holder.layout_tooth_history.addView(Cb);
                                        if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                            EditText et2=new EditText(getActivity());
                                            et2.setWidth(1000);
                                            holder.layout_tooth_history.addView(et2);
                                        }
                                    }
                                    break;
                            }
                        }

                        /**
                         * part 6 last_six_months_custom
                         */
                        for (int i =25 ; i<57;i++){
                            switch (personal_JsonArray.getJSONArray(i).get(1).toString()){
                                case "text":
                                    TextView tv=new TextView(getActivity());
                                    tv.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    EditText et=new EditText(getActivity());
//                                  et.setWidth(800);
                                    holder.layout_last_six_months_custom.addView(tv);
                                    holder.layout_last_six_months_custom.addView(et);
                                    break;
                                case "radio":

                                    RadioGroup group = new RadioGroup(getActivity());
                                    if(personal_JsonArray.getJSONArray(i).getJSONArray(4).length()>22){
                                        /*for(int j=0;j<30;j++){
                                            RadioButton rb = new RadioButton(getActivity());
                                            rb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                            group.addView(rb);
                                            if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                                EditText et2=new EditText(getActivity());
                                                et2.setWidth(1000);
                                                group.addView(et2);
                                            }
                                        }*/
                                        final int x =i;
//                                    final TextView tv_radio_value=new TextView(getActivity());
                                        final EditText et_radio=new EditText(getActivity());
                                        et_radio.setWidth(800);
                                        et_radio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                            @Override
                                            public void onFocusChange(View v, boolean hasFocus) {
                                                try {
                                                    testChoice = new String[30];
                                                    for(int j=0;j<30;j++){
                                                        testChoice[j] = personal_JsonArray.getJSONArray(x).getJSONArray(4).get(j).toString();
                                                    }
                                                    new AlertDialog.Builder(getActivity()).setTitle(personal_JsonArray.getJSONArray(x).get(0).toString())
                                                            .setSingleChoiceItems(testChoice, 0,
                                                                    new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which
                                                                        ) {
                                                                            // If the user checked the item, add it to the selected items
                                                                            et_radio.setText(testChoice[which]);
                                                                        }
                                                                    })
                                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                }
                                                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    }).show();
                                                } catch (JSONException e) {
                                                    Log.e("ORM",packagename+"AlertDialog Error :"+e.toString());
                                                }
                                            }
                                        });
                                        TextView tv_radio=new TextView(getActivity());
                                        tv_radio.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                        holder.layout_last_six_months_custom.addView(tv_radio);
                                        holder.layout_last_six_months_custom.addView(et_radio);

                                    }else{
                                        for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++){
                                            RadioButton rb = new RadioButton(getActivity());
                                            rb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                            group.addView(rb);
                                            if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                                EditText et2=new EditText(getActivity());
                                                et2.setWidth(1000);
                                                group.addView(et2);
                                            }
                                        }
                                        TextView tv_radio=new TextView(getActivity());
                                        tv_radio.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                        holder.layout_last_six_months_custom.addView(tv_radio);
                                        holder.layout_last_six_months_custom.addView(group);
                                    }

                                    break;
                                case "multiple":
                                    TextView tv_multiple=new TextView(getActivity());
                                    tv_multiple.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_last_six_months_custom.addView(tv_multiple);
                                    for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++) {
                                        CheckBox Cb = new CheckBox(getActivity());
                                        Cb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        holder.layout_last_six_months_custom.addView(Cb);
                                        if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                            EditText et2=new EditText(getActivity());
                                            et2.setWidth(1000);
                                            holder.layout_last_six_months_custom.addView(et2);
                                        }
                                    }
                                    break;
                            }
                        }

                        /**
                         * part 6 last_six_months_custom
                         */
                        for (int i =58 ; i<61;i++){
                            switch (personal_JsonArray.getJSONArray(i).get(1).toString()){
                                case "radio":
                                    RadioGroup group = new RadioGroup(getActivity());
                                    for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++){
                                        RadioButton rb = new RadioButton(getActivity());
                                        rb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        group.addView(rb);
                                        if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                            EditText et2=new EditText(getActivity());
                                            et2.setWidth(1000);
                                            group.addView(et2);
                                        }
                                    }
                                    TextView tv_radio=new TextView(getActivity());
                                    tv_radio.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_skin_estimate.addView(tv_radio);
                                    holder.layout_skin_estimate.addView(group);
                                    break;
                                default:
                                    break;
                            }
                        }

                        /**
                         * part 7 drinking test
                         */
                        for (int i =62 ; i<72;i++){
                            switch (personal_JsonArray.getJSONArray(i).get(1).toString()){
                                case "radio":
                                    RadioGroup group = new RadioGroup(getActivity());
                                    for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++){
                                        RadioButton rb = new RadioButton(getActivity());
                                        rb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        group.addView(rb);
                                        if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                            EditText et2=new EditText(getActivity());
                                            et2.setWidth(1000);
                                            group.addView(et2);
                                        }
                                    }
                                    TextView tv_radio=new TextView(getActivity());
                                    tv_radio.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_drinking_test.addView(tv_radio);
                                    holder.layout_drinking_test.addView(group);
                                    break;
                                default:
                                    break;
                            }
                        }

                        /**
                         * part 8 Hormone test
                         */
                        for (int i =73 ; i<79;i++){
                            CheckBox Cb = new CheckBox(getActivity());
                            Cb.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                            holder.layout_hormone_test.addView(Cb);
                            if (personal_JsonArray.getJSONArray(i).get(0).toString().equals("其他")){
                                EditText et2=new EditText(getActivity());
                                et2.setWidth(1000);
                                holder.layout_hormone_test.addView(et2);
                            }
                        }

                        /**
                         * part9 stress_test
                         */
                        for (int i =80 ; i<98;i++){
                            switch (personal_JsonArray.getJSONArray(i).get(1).toString()){
                                case "radio":
                                    RadioGroup group = new RadioGroup(getActivity());
                                    for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++){
                                        RadioButton rb = new RadioButton(getActivity());
                                        rb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        group.addView(rb);
                                    }
                                    TextView tv_radio=new TextView(getActivity());
                                    tv_radio.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_stress_test.addView(tv_radio);
                                    holder.layout_stress_test.addView(group);
                                    break;
                                default:
                                    break;
                            }

                        }

                        /**
                         * part9 emotion test
                         */
                        for (int i =100 ; i<118;i++){
                            switch (personal_JsonArray.getJSONArray(i).get(1).toString()){
                                case "radio":
                                    RadioGroup group = new RadioGroup(getActivity());
                                    for(int j=0;j<personal_JsonArray.getJSONArray(i).getJSONArray(4).length();j++){
                                        RadioButton rb = new RadioButton(getActivity());
                                        rb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                        group.addView(rb);
                                    }
                                    TextView tv_radio=new TextView(getActivity());
                                    tv_radio.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                                    holder.layout_emotion_test.addView(tv_radio);
                                    holder.layout_emotion_test.addView(group);
                                    break;
                                default:
                                    break;
                            }
                        }

                        for (int i =119 ; i<personal_JsonArray.length();i++){
                            CheckBox Cb = new CheckBox(getActivity());
                            Cb.setText(personal_JsonArray.getJSONArray(i).get(0).toString());
                            holder.layout_accept_statement.addView(Cb);
                        }


                    }
                    catch (Exception e){
                        Log.e("ORM",  packagename+"question loading Error: " +e.toString());

                    }

                }
            });
//            tv.setText("123456");

            /*try {
                Log.d("ORM","debug abc");


            }catch (Exception e){
                Log.e("ORM",  packagename+"question loading Error: " +e.toString());
            }*/


        }


        @Override
        public int getItemCount() {
            return 1;
        }

        class ContentHolder extends RecyclerView.ViewHolder {

            private TextView item_ques;
            private LinearLayout layout_personal_info;
            private LinearLayout layout_family_history;
            private LinearLayout layout_personal_history;
            private LinearLayout layout_last_six_months_disease;
            private LinearLayout layout_tooth_history;
            private LinearLayout layout_last_six_months_custom;
            private LinearLayout layout_skin_estimate;
            private LinearLayout layout_drinking_test;
            private LinearLayout layout_hormone_test;
            private LinearLayout layout_stress_test;
            private LinearLayout layout_emotion_test;
            private LinearLayout layout_accept_statement;

//            private LinearLayout layout_skin_estimate;

            private TableRow row_personal_info;

            public ContentHolder(View itemView) {
                super(itemView);

                layout_personal_info = itemView.findViewById(R.id.layout_personal_info);
                layout_family_history = itemView.findViewById(R.id.layout_family_history);
                layout_personal_history = itemView.findViewById(R.id.layout_personal_history);
                layout_last_six_months_disease = itemView.findViewById(R.id.layout_last_six_months_disease);
                layout_tooth_history = itemView.findViewById(R.id.layout_tooth_history);
                layout_last_six_months_custom = itemView.findViewById(R.id.layout_last_six_months_custom);
                layout_skin_estimate = itemView.findViewById(R.id.layout_skin_estimate);
                layout_drinking_test = itemView.findViewById(R.id.layout_drinking_test);
                layout_hormone_test = itemView.findViewById(R.id.layout_hormone_test);
                layout_stress_test = itemView.findViewById(R.id.layout_stress_test);
                layout_emotion_test = itemView.findViewById(R.id.layout_emotion_test);
                layout_accept_statement = itemView.findViewById(R.id.layout_accept_statement);

//                row_personal_info = itemView.findViewById(R.id.tr_personalinfo);


            }
        }
    }
}
