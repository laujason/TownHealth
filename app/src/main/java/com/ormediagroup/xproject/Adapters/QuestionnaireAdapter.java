package com.ormediagroup.xproject.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ormediagroup.xproject.Beans.ProductBean;
import com.ormediagroup.xproject.Beans.QuestionnaireBean;
import com.ormediagroup.xproject.Fragment.QuestionnaireFragment;
import com.ormediagroup.xproject.JsonRespon;
import com.ormediagroup.xproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by YQ04 on 2018/4/25.
 */

public class QuestionnaireAdapter extends RecyclerView.Adapter <QuestionnaireAdapter.QuestionnaireHolder>{
    private final String packagename = "QuestionnaireAdapter - ";
    private Context context;
    private List<QuestionnaireBean> questionnaireBeanList;
    String QuesURL = "https://thhealthmgt.com/app/app-questionnaire/";
    public JSONArray info_JsonArray,family_JsonArray,personal_JsonArray;
    private String[] testChoice;
    private String[] value;

    public QuestionnaireAdapter(Context context, List<QuestionnaireBean> questionnaireBeanList) {
        this.context = context;
        this.questionnaireBeanList = questionnaireBeanList;
    }
    public QuestionnaireAdapter(Context context){
        this.context = context;
    }


    @Override
    public QuestionnaireHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_personalinfo_list, parent, false);

        QuestionnaireHolder holder = new QuestionnaireHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final QuestionnaireHolder holder, int position) {
        new JsonRespon(getActivity(), QuesURL, new JsonRespon.onComplete() {

            @Override
            public void onComplete(JSONObject json) {

                try {
                    info_JsonArray = new JSONArray();
                    family_JsonArray = new JSONArray();
                    personal_JsonArray = new JSONArray();
                    info_JsonArray = json.getJSONArray("basic");
                    family_JsonArray = json.getJSONArray("family");
                    personal_JsonArray = json.getJSONArray("personal");
                    for (int i = 0; i < info_JsonArray.length(); i++) {
                        Log.d("ORM", "debug ss" + info_JsonArray.length());
                        Log.i("ORM", "json Array" + info_JsonArray.getJSONArray(1).toString());
                        Log.i("ORM", "json Array item " + info_JsonArray.getJSONArray(0).get(1).toString());

                        switch (info_JsonArray.getJSONArray(i).get(1).toString()) {
                            case "text":
                                TextView tv = new TextView(getActivity());
                                tv.setText(info_JsonArray.getJSONArray(i).get(0).toString());
                                EditText et = new EditText(getActivity());

//                              TextView tv1 = new TextView(getActivity());

//                                int x=i;
//                                tv.setTag("tag"+x);
//                                holder.itemView.setTag(R.id.tag_text+i,tv);
                                holder.itemView.setTag(R.id.tag_type+i,et);

//                              holder.itemView.setTag(R.id.tag_text+x,et);
                                holder.layout_personal_info.addView(tv);
                                holder.layout_personal_info.addView(et);
                                Log.i("ORM", "test getTag: " + tv.getText().toString());

                                break;
                            case "radio":
                                RadioGroup group = new RadioGroup(getActivity());
                                for (int j = 0; j < info_JsonArray.getJSONArray(i).getJSONArray(4).length(); j++) {
                                    RadioButton rb = new RadioButton(getActivity());
                                    rb.setText(info_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                    group.addView(rb);
                                    if (info_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")) {
                                        EditText et2 = new EditText(getActivity());
                                        et2.setWidth(1000);
                                        et2.setId(R.id.register_manager);
                                        group.addView(et2);
                                    }

                                }
//                                holder.itemView.setTag(R.id.tag_type+i,group);
                                TextView tv_radio = new TextView(getActivity());
                                tv_radio.setText(info_JsonArray.getJSONArray(i).get(0).toString());
                                holder.layout_personal_info.addView(tv_radio);
                                holder.layout_personal_info.addView(group);
                                break;
                            case "date":
                                TextView tv_date = new TextView(getActivity());
                                tv_date.setText(info_JsonArray.getJSONArray(i).get(0).toString());
                                EditText et_date = new EditText(getActivity());
//                                holder.itemView.setTag(R.id.tag_type+i,et_date);
                                holder.layout_personal_info.addView(tv_date);
                                holder.layout_personal_info.addView(et_date);

                                et_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                    @Override
                                    public void onFocusChange(View v, boolean hasFocus) {
                                        Toast.makeText(getActivity().getApplicationContext(), "填写日期", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            default:
                                break;
                        }
//                        Log.d("ORM","test getTag data" +i+":"+value[i]);
                    }

                    /**
                     * family history
                     */
                        for (int i = 0; i < family_JsonArray.length(); i++) {
                            TextView tv_multiple = new TextView(getActivity());
                            tv_multiple.setText(family_JsonArray.getJSONArray(i).get(0).toString());
                            holder.layout_family_history.addView(tv_multiple);
                            for (int j = 0; j < family_JsonArray.getJSONArray(i).getJSONArray(4).length(); j++) {
                                CheckBox Cb = new CheckBox(getActivity());
                                Cb.setText(family_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                holder.layout_family_history.addView(Cb);
                                if (family_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")) {
                                    EditText et2 = new EditText(getActivity());
                                    et2.setWidth(1000);
                                    holder.layout_family_history.addView(et2);
                                }
                            }

                        }
                    /**
                     *
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
                     * part5
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
                                    for(int j=0;j<30;j++){
                                    RadioButton rb = new RadioButton(getActivity());
                                    rb.setText(personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString());
                                    group.addView(rb);
                                    if (personal_JsonArray.getJSONArray(i).getJSONArray(4).get(j).toString().equals("其他")){
                                        EditText et2=new EditText(getActivity());
                                        et2.setWidth(1000);
                                        group.addView(et2);
                                    }
                                }
                                final int x =i;
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
                     * part 6
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
                     * part7
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
                     * part8
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
                     * part9
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
                     * part10
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


                } catch (Exception e) {

                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return 1;
    }

    public Context getActivity() {
        return context;
    }


    public class QuestionnaireHolder extends RecyclerView.ViewHolder{

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
        private Button btn_save_personal_info;
        private SaveInfoOnClickListener SavePersonalinfoOnClickListener;


        public QuestionnaireHolder(View itemView) {
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
            btn_save_personal_info = itemView.findViewById(R.id.btn_save_personal_info);
            SavePersonalinfoOnClickListener = new SaveInfoOnClickListener(itemView);
            btn_save_personal_info.setOnClickListener(SavePersonalinfoOnClickListener);
//          row_personal_info = itemView.findViewById(R.id.tr_personalinfo);

        }
    }

    public class SaveInfoOnClickListener implements View.OnClickListener{
        View itemView;
        ViewGroup viewGroup;
        String[] info = new String[questionnaireBeanList.size()];
        String[] st ;
        public SaveInfoOnClickListener(View itemView) {
            this.itemView=itemView;
        }

        public void onClick(View v) {

            Log.i("ORM","size:"+questionnaireBeanList.size());
            for (int i =0 ;i<questionnaireBeanList.size();i++) {
                Log.i("ORM","type:"+questionnaireBeanList.get(i).type);
                switch (questionnaireBeanList.get(i).type) {
                    case "radio":
                        Log.i("ORM","Onclick return radio :none"+i);
                        break;
                    case "text":
                        String s = ((TextView) itemView.getTag(R.id.tag_type+i)).getText().toString();
                        info[i]=s;
//                        Log.d("ORM","Onclick return :"+s);
                        Log.i("ORM","Onclick return text:"+s);
                        break;
                    case "date":
                        /*
                        String s2 = ((EditText) itemView.getTag(R.id.tag_type+i)).getText().toString();
                        info[i]=s2;*/
                        Log.i("ORM","Onclick return datepicker :none"+i);
                        break;

                }
            }

            Toast.makeText(getActivity(),"dont get answer",Toast.LENGTH_SHORT).show();

        }

    }
}
