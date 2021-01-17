package com.example.ex6_fb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String stock_symbol;
    Button submit;
    EditText input_symbol;

    private static final String SERVER_ADDRESS = "http://192.168.1.55:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Receiving the stock symbol from the user
                input_symbol = (EditText) findViewById(R.id.stock_symbol);
                stock_symbol = input_symbol.getText().toString();
                Toast.makeText(v.getContext(),  "Request was sent to server with stock symbol " + stock_symbol, Toast.LENGTH_LONG).show();
                sendToServer(stock_symbol);
            }
        });
    }

    //Getting the stock current price by sending the stock symbol and token to the server
    public void getStockCurrentPrice (String stock_symbol, String token) {
        JSONObject reqObject = new JSONObject();
        try {
            reqObject.put("token", token);
        } catch (JSONException e) {
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, SERVER_ADDRESS +
                "stock_symbol/" + stock_symbol, reqObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueueFetcher.getInstance(this).getQueue().add(request);
    }

    //Send the stock symbol to the js server
    private void sendToServer(String stock_symbol){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                getStockCurrentPrice(stock_symbol, token);
            }
        });
    }
}