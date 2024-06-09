package com.example.androidapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.openapi.resolverApp.api.DefaultApi;
import com.openapi.resolverApp.model.LoginRequest;

public class UserLogin extends AppCompatActivity {


    public interface RegistrationCallback {
        void onRegistrationSuccess(String sessionId);
        void onRegistrationError(int errorCode, String errorMessage);
    }

    CardView login_button;
    CheckBox show_password;
    EditText user_name_textView;
    EditTextWithDrawableClick password_textView;
    String sessionId;
    SharedPreferences loginPreference;
    Context context;
    boolean isChecked = false;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        {
            login_button = findViewById(R.id.login_button_CardView);
            user_name_textView = findViewById(R.id.user_name_editText);
            password_textView = findViewById(R.id.password_editText);
            password_textView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            loginPreference = getSharedPreferences(AppConstants.PREF_LOGIN,MODE_PRIVATE);
            context = this;

        }

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = loginPreference.edit();
                validateUser(UserLogin.this, new RegistrationCallback() {
                    @Override
                    public void onRegistrationSuccess(String sessionId) {
                        editor.putBoolean(AppConstants.KEY_LOGIN_FLAG, true);
                        editor.putString(AppConstants.KEY_SESSION_ID, sessionId);
                        editor.putString(AppConstants.KEY_PASSWORD, password_textView.getText().toString().trim());
                        editor.putString(AppConstants.KEY_USER_NAME, user_name_textView.getText().toString().trim());
                        editor.apply();
                    }

                    @Override
                    public void onRegistrationError(int errorCode, String errorMessage) {
                        if(errorCode==0)
                            Toast.makeText(UserLogin.this, "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                        else{
                            if(errorMessage!=null){
                                if(errorMessage.contains("Password")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserLogin.this);
                                    builder.setTitle("Wrong password");
                                    builder.setMessage("Please Enter correct password");
                                    builder.setNegativeButton("Cancel", (dialog, i) -> dialog.dismiss());
                                    builder.show();
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserLogin.this);
                                    builder.setTitle("User Not Found");
                                    builder.setMessage("You haven't registered, please create your account first");
                                    builder.setNegativeButton("Cancel", (dialog, i) -> dialog.dismiss());
                                    builder.show();
                                }
                            }
                        }
                        editor.putBoolean(AppConstants.KEY_LOGIN_FLAG, false);
                        editor.apply();
                    }
                });
            }
        });

        password_textView.setOnDrawableClickListener(new EditTextWithDrawableClick.OnDrawableClickListener() {
            @Override
            public void onClick(EditText editText) {
                togglePassword();
            }
        });


    }
    private void togglePassword() {
        isChecked = ! isChecked;
        if (isChecked) {
            password_textView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            password_textView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }



    public void validateUser(Activity activity, RegistrationCallback callback) {
        String user_name = user_name_textView.getText().toString().trim();
        String password = password_textView.getText().toString().trim();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(user_name);
        loginRequest.setPassword(password);

        if (user_name.length() >= 4) {
            if (password.length() >= 8) {
                sendRequest(loginRequest, activity, callback);
            }
            else {
                if (password.length() == 0) {
                    password_textView.setError("Please Enter Valid Password");
                } else {
                    password_textView.setError("Password can't be less than 6 characters");
                }
            }
        }
        else {
            user_name_textView.setError("Username can't be less than 4 characters");
        }
    }

    public static void sendRequest(LoginRequest loginRequest, Activity activity ,RegistrationCallback callback){
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        DefaultApi api = new DefaultApi();
        Log.d("myTag", "api "+api.getBasePath());
        loadingDialog.startLoadingDialog();
        api.login(loginRequest, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onRegistrationSuccess(response.trim().substring(1, 41));
                loadingDialog.dismissDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    int statusCode = error.networkResponse.statusCode;
                    String data = new String(error.networkResponse.data);
                    callback.onRegistrationError(statusCode, data.trim().substring(1,41));;
                    loadingDialog.dismissDialog();
                } catch (Exception e) {
                    callback.onRegistrationError(0, null);
                    loadingDialog.dismissDialog();
                }
            }
        });
    }
}
