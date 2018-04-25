package com.ormediagroup.xproject.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ormediagroup.xproject.JsonRespon;
import com.ormediagroup.xproject.R;

import org.json.JSONArray;
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
//    private View ques_item;
    String TownHealthDomain;
    public JSONArray info_JsonArray,ques_JsonArray;
    public ArrayList<JSONArray> itemJsonArray;
    private Calendar myCalendar;

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

        new JsonRespon(getActivity(), URL, new JsonRespon.onComplete() {
            @Override
            public void onComplete(JSONObject json) {
                try {
                    itemJsonArray = new ArrayList<JSONArray>();
                    info_JsonArray = json.getJSONArray("basic");
                    ques_JsonArray = json.getJSONArray("family");
//                  ques_JsonArray = json.getJSONArray("personal");
                    for (int i = 0; i < info_JsonArray.length(); i++) {
                        itemJsonArray.add(info_JsonArray.getJSONArray(i));
                        Log.d("ORM",packagename+"info_JsonArray data:"+info_JsonArray.getJSONArray(i).get(1).toString());
                    }
                    /*for (int j = 0;j < ques_JsonArray.length();j++){
                        itemJsonArray.add(ques_JsonArray.getJSONArray(j));
                    }*/
//                    loadData();

                    ques_content.setLayoutManager(new LinearLayoutManager(getActivity()));
                    ques_content.setAdapter(new ContentAdapter(itemJsonArray));

                } catch (Exception e) {
//                    e.printStackTrace();
                    Log.e("ORM",  packagename+"Get json error: " +e.toString());
                }
            }

        });
//        Log.d("ORM","abckasjdlksajd"+itemJsonArray.size());

        /*ques_content.setLayoutManager(new LinearLayoutManager(getActivity()));
        ques_content.setAdapter(new ContentAdapter(itemJsonArray));*/
    }

    private void loadData() {


    }




    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {
        private ArrayList<JSONArray> list;
        public ContentAdapter(ArrayList<JSONArray> list){
            this.list = list;

        }
        @Override
        public QuestionnaireFragment.ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new QuestionnaireFragment.ContentAdapter.ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_personalinfo_list, parent, false));
        }

        @Override
        public void onBindViewHolder(QuestionnaireFragment.ContentAdapter.ContentHolder holder, int position) {


//            tv.setText("123456");
            try {
                Log.d("ORM","debug abc"+list.size());
                for (int i = 0; i < list.size(); i++) {
                    Log.d("ORM","i="+i);
                    Log.d("ORM","debug ss"+list.toString());
                    Log.d("ORM","debug gender"+list.get(2).getJSONArray(4).get(1).toString());

//                  holder.item_ques.setText(list.get(i).get(0).toString());

//                  holder.layout_personal_info.addView(tv);
                    switch (list.get(i).get(1).toString()){
                        case "text":
                            TextView tv=new TextView(getActivity());
                            tv.setText(list.get(i).get(0).toString());
                            EditText et=new EditText(getActivity());
//                            et.setWidth(800);
                            holder.layout_personal_info.addView(tv);
                            holder.layout_personal_info.addView(et);
                            break;
                        case "radio":
                            RadioGroup group = new RadioGroup(getActivity());
                            for(int j=0;j<list.get(i).getJSONArray(4).length();j++){
                                RadioButton rb = new RadioButton(getActivity());
                                rb.setText(list.get(i).getJSONArray(4).get(j).toString());
                                group.addView(rb);
                                if (list.get(i).getJSONArray(4).get(j).toString().equals("其他")){
                                    EditText et2=new EditText(getActivity());
                                    et2.setWidth(1000);
                                    et2.setId(R.id.register_manager);
                                    group.addView(et2);
                                }

                            }
                            TextView tv_radio=new TextView(getActivity());
                            tv_radio.setText(list.get(i).get(0).toString());
                            holder.layout_personal_info.addView(tv_radio);
                            holder.layout_personal_info.addView(group);
                            /*group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    switch (checkedId){

                                    }
                                }
                            });*/

                            break;
                        case "date":
                            TextView tv_date=new TextView(getActivity());
                            tv_date.setText(list.get(i).get(0).toString());
                            EditText et_date=new EditText(getActivity());
//                            DatePicker datePicker = new DatePicker(getActivity());
                            holder.layout_personal_info.addView(tv_date);
                            holder.layout_personal_info.addView(et_date);
//                            et_date.setInputType(InputType.TYPE_NULL);
//                            et_date.setId(R.id.register_manager);

                            et_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
//                                    Toast.makeText(getActivity().getApplicationContext(),"填写日期", Toast.LENGTH_SHORT).show();


                                }
                            });



                            /*et_date.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                                    return false;
                                }
                            });*/

                            break;
                        default:
                            break;
                    }

                }
            }catch (Exception e){
                Log.e("ORM",  packagename+"question loading Error: " +e.toString());
            }
            

        }


        @Override
        public int getItemCount() {
            return 1;
        }

        class ContentHolder extends RecyclerView.ViewHolder {

            private TextView item_ques;
            private LinearLayout layout_personal_info;
            private TableRow row_personal_info;

            public ContentHolder(View itemView) {
                super(itemView);
//                item_ques = itemView.findViewById(R.id.tv_personal_info);
                layout_personal_info = itemView.findViewById(R.id.layout_personal_info);

//                row_personal_info = itemView.findViewById(R.id.tr_personalinfo);


            }
        }
    }
}
