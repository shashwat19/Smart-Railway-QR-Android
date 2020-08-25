package com.simplicitydev.smartrailwayqr;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

public class GenerateQR extends AppCompatActivity {

    EditText name_o_p,doj,adh_no,mob_no;
    Spinner st,ct,tr_name;
    TextView sr,dest,tr_no;
    int d,m,y;

    String name,trainname,trainno,state,city,source,destination,date,aadhaar,mobile;
    Button sc_fg;

    ArrayList<String> states=new ArrayList<>();
    ArrayList<String> cityname;
    ArrayList<String> tname;
    ArrayList<String> tno=new ArrayList<>();
    Passenger passenger;
    RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_generate_qr);


        sc_fg=findViewById(R.id.fingerp);
        st=findViewById(R.id.st_name);
        ct=findViewById(R.id.city_name);
        sr=findViewById(R.id.from_id);
        dest=findViewById(R.id.to_id);
        tr_name=findViewById(R.id.train_name);
        tr_no=findViewById(R.id.train_no);
        mRequestQueue=Volley.newRequestQueue(this);


        states.add("Select State");
        states.add("Delhi");
        states.add("Rajasthan");
        tr_no.setText("");


        ArrayAdapter<String> adapter_st=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,states);
        st.setAdapter(adapter_st);

        st.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityname=new ArrayList<>();
                cityname.add("Select City");
                if(position==1){
                    cityname.add("New Delhi");
                }
                else if(position==2){
                    cityname.add("Jaipur");
                }

                ArrayAdapter<String> adapter_ct=new ArrayAdapter<String>(GenerateQR.this,android.R.layout.simple_list_item_1,cityname);
                ct.setAdapter(adapter_ct);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         ct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 tname=new ArrayList<>();
                 tname.add("Select train");
                 tname.add("DEE PORBNDR EXP");
                 if(ct.getSelectedItem().toString().equals("New Delhi")){
                     sr.setText("NDLS, Delhi");
                     dest.setText("JP, Jaipur");

                 }

                 else if(ct.getSelectedItem().toString().equals("Jaipur")){
                     dest.setText("NDLS, Delhi");
                     sr.setText("JP, Jaipur");
                 }
                 ArrayAdapter<String> adapter_ct=new ArrayAdapter<String>(GenerateQR.this,android.R.layout.simple_list_item_1,tname);
                 tr_name.setAdapter(adapter_ct);
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

         tr_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    tr_no.setText("19264");
                }

                else{
                    tr_no.setText("");
                }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });


        name_o_p=findViewById(R.id.name);
        doj=findViewById(R.id.journey_date);
        adh_no=findViewById(R.id.aadhaar);
        mob_no=findViewById(R.id.mobile);

        Calendar calendar=Calendar.getInstance();
        d= calendar.get(Calendar.DAY_OF_MONTH);
        m=calendar.get(Calendar.MONTH);
        y=calendar.get(Calendar.YEAR);

        doj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(GenerateQR.this,listener,y,m,d).show();
            }
        });

        sc_fg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=name_o_p.getText().toString();
                trainname=tr_name.getSelectedItem().toString();
                trainno=tr_no.getText().toString();
                state=st.getSelectedItem().toString();
                city=ct.getSelectedItem().toString();
                source=sr.getText().toString();
                destination=dest.getText().toString();
                date=doj.getText().toString();
                aadhaar=adh_no.getText().toString();
                mobile=mob_no.getText().toString();

                if(name.isEmpty()||trainname.isEmpty()||trainno.isEmpty()||state.isEmpty()||city.isEmpty()
                        ||source.isEmpty()||destination.isEmpty()||date.isEmpty()||aadhaar.isEmpty()||mobile.isEmpty()){
                    Toast.makeText(GenerateQR.this, "Please fill all the Details!", Toast.LENGTH_SHORT).show();
                }
                else if(aadhaar.length()!=12){
                    Toast.makeText(GenerateQR.this, "Aadhar no. should be 12 digits", Toast.LENGTH_LONG).show();
                }
                else if(mob_no.length()!=10){
                    Toast.makeText(GenerateQR.this, "Mobile number should be 10 digits", Toast.LENGTH_LONG).show();
                }
                else if(state.equals("Select State")||city.equals(("Select City"))){
                    Toast.makeText(GenerateQR.this, "Enter all details", Toast.LENGTH_SHORT).show();
                }
                else {
                     passenger = new Passenger();

                    int random = new Random().nextInt(100000000) + 999999999;
                    String pnr = String.valueOf(random).trim();
                    passenger.setPnr(pnr);
                    passenger.setName(name);
                    passenger.setTrainname(trainname);
                    passenger.setTrainno(trainno);
                    passenger.setState(state);
                    passenger.setCity(city);
                    passenger.setSource(source);
                    passenger.setDestination(destination);
                    passenger.setDate(date);
                    passenger.setAadhaar(aadhaar);
                    passenger.setMobile(mobile);

                    final AlertDialog.Builder ab=new AlertDialog.Builder(GenerateQR.this);
                    ab.setMessage("Ticket Amount: ₹ 590/-");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            /*Intent it = new Intent(GenerateQR.this, ScanFingerprint.class);
                            it.putExtra("passenger", passenger);
                            startActivity(it);
                            finish();*/
                            sc_fg.setEnabled(false);
                            launchPaymentFlow();
                        }
                    });
                    ab.show();

                }

            }
        });



    }

    DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            doj.setText(i2+"/"+(i1+1)+"/"+i);
            d=i2;
            m=i1;
            y=i;
        }
    };

    private void launchPaymentFlow() {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.setPayUmoneyActivityTitle("Buy Ticket");
        payUmoneyConfig.setDoneButtonText("Pay ₹ 590");

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount("590")
                .setTxnId(System.currentTimeMillis() + "")
                .setPhone(mobile)
                .setProductName("Train_Ticket")
                .setFirstName(name)
                .setEmail("deepak1961997@gmail.com")
                .setsUrl(com.simplicitydev.smartrailwayqr.Constants.SURL)
                .setfUrl(com.simplicitydev.smartrailwayqr.Constants.FURL)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setIsDebug(com.simplicitydev.smartrailwayqr.Constants.DEBUG)
                .setKey(com.simplicitydev.smartrailwayqr.Constants.MERCHANT_KEY)
                .setMerchantId(com.simplicitydev.smartrailwayqr.Constants.MERCHANT_ID);


        try {
            PayUmoneySdkInitializer.PaymentParam mPaymentParams = builder.build();
            calculateHashInServer(mPaymentParams);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            sc_fg.setEnabled(true);
        }
    }

    private void calculateHashInServer(final PayUmoneySdkInitializer.PaymentParam mPaymentParams) {
        com.simplicitydev.smartrailwayqr.ProgressUtils.showLoadingDialog(this);
        String url = com.simplicitydev.smartrailwayqr.Constants.MONEY_HASH;
        StringRequest request = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        sc_fg.setEnabled(true);
                        String merchantHash = "";

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            merchantHash = jsonObject.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        com.simplicitydev.smartrailwayqr.ProgressUtils.cancelLoading();

                        if (merchantHash.isEmpty() || merchantHash.equals("")) {
                            Toast.makeText(GenerateQR.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
                        } else {
                            mPaymentParams.setMerchantHash(merchantHash);
                            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, GenerateQR.this, R.style.PayUMoney, true);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sc_fg.setEnabled(true);
                        Toast.makeText(GenerateQR.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        com.simplicitydev.smartrailwayqr.ProgressUtils.cancelLoading();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return mPaymentParams.getParams();
            }
        };
        mRequestQueue.add(request);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        sc_fg.setEnabled(true);

        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {

            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    showAlert("Payment Successful");
                } else if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.CANCELLED)) {
                    showAlert("Payment Cancelled");
                } else if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.FAILED)) {
                    showAlert("Payment Failed");
                }

            } else if (resultModel != null && resultModel.getError() != null) {
                Toast.makeText(this, "Error check log", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Both objects are null", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_CANCELED) {
            showAlert("Payment Cancelled");
        }
    }

    private Double convertStringToDouble(String str) {
        return Double.parseDouble(str);
    }

    private void showAlert(String msg){
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 Intent it = new Intent(GenerateQR.this, ScanFingerprint.class);
                            it.putExtra("passenger", passenger);
                            startActivity(it);
                            finish();
            }
        });
        alertDialog.show();
    }
}
