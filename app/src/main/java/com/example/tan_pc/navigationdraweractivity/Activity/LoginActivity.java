package com.example.tan_pc.navigationdraweractivity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.example.tan_pc.navigationdraweractivity.R;
//import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ClientSocket.GetDataServiceAPI;
import ClientSocket.account;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.API_URL_BASE;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.ToastShow;

/**
 * Created by tan-pc on 8/9/2018.
 */

public class LoginActivity extends LocalizationActivity {
    ProgressDialog progressDialog;
    public static int typeOfUser =99;//0-admin ; 1-customer; 2-worker
    Button mEmailSignInButton;
    Button mEnButton;
    Button mVnButton;

    private UserLoginTask mAuthTask = null;
    private EditText mUserNameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        mEmailSignInButton = (Button)findViewById(R.id.btn_login);
        mEnButton = (Button)findViewById(R.id.btnEn);
        mVnButton = (Button)findViewById(R.id.btnVn);
        mUserNameView = (EditText) findViewById(R.id.input_name);
        mPasswordView = (EditText) findViewById(R.id.input_password);
        mLoginFormView = findViewById(R.id.login_form);
        mEnButton.setOnClickListener(onEngLanguageCLick());
        mVnButton.setOnClickListener(onVnLanguageCLick());
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL;
            }
        });

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
//                OkHttpClient client = new OkHttpClient();
////                client.interceptors().add(new Interceptor() {
////                    @Override
////                    public okhttp3.Response intercept(Chain chain) throws IOException {
////                        okhttp3.Response response = chain.proceed(chain.request());
////                        // Do anything with response here
////                        return response;
////                    }
////                });
//                response

//                    @Override
//                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
//
//                        ToastShow(getApplicationContext(),"On response"+response.toString());
////                        ToastShow(getApplicationContext(),"On response"+call.toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<JSONObject> call, Throwable t) {
//                        ToastShow(getApplicationContext(),"On failure"+t.toString());
//
//
//                    }
//                });

//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//                        Log.e("onfailure", t.toString());
//                        ToastShow(getApplicationContext(),"onfailure" +t.toString());
//                    }
//                });
            }
        });

    }

    private View.OnClickListener onVnLanguageCLick() {
         return view -> setLanguage("vn");
    }

    private View.OnClickListener onEngLanguageCLick() {
         return view -> setLanguage("en");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save x-position of horizontal scroll view.
//        outState.putInt(KEY_SCROLL_X, svLanguageChooser.getScrollX());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore x-position of horizontal scroll view.
//        svLanguageChooser.scrollTo(savedInstanceState.getInt(KEY_SCROLL_X), 0);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String user = mUserNameView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
            progressDialog = ProgressDialog.show(LoginActivity.this,"Please wait",
                    "Loading Data...", false, false);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GetDataServiceAPI service = retrofit.create(GetDataServiceAPI.class);
            Call<account> UserVerification = service.UserVerification(user,password);

            UserVerification.enqueue(new Callback<account>() {
                @Override
                public void onResponse(Call<account> call, Response<account> response) {

                    ToastShow(getApplicationContext(),"On response: "+response.body().getUserVerified().toString());
                    ToastShow(getApplicationContext(),"On response: "+response.raw().toString());
                    if (response.body().getUserVerified().toString() == "true") {
                         String role = response.body().getRole().toString();
                         typeOfUser = Integer.parseInt(role);
                        mAuthTask = new UserLoginTask(user, password);

                        mAuthTask.execute((Void) null);
//                        progressDialog.dismiss();
//                        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
//                        LoginActivity.this.startActivity(mainIntent);
                        finish();
                        } else{
//
//                        typeOfUser=99;
//                        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
//                        LoginActivity.this.startActivity(mainIntent);
//                        progressDialog.dismiss();
//                        finish();//
                        progressDialog.dismiss();

                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                    }
                }

                @Override
                public void onFailure(Call<account> call, Throwable t) {
                    progressDialog.dismiss();
                    ToastShow(getApplicationContext(),"On Failure: "+t.toString());
                }
            });



//            mAuthTask = new UserLoginTask(, password);
//            mAuthTask.execute((Void) null);
        }
    }

//    private int getUserType (String user) {
//
//        return
//
//    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUser;
        private final String mPassword;

        UserLoginTask(String user, String password) {
            mUser = user;
            mPassword = password;
//            switch (mUser){
//                case "admin" : {
//                    typeOfUser = 0;
//                    break;
//                }
//                case "customer" : {
//                    typeOfUser = 1;
//                    break;
//                }
//                case "user" : {
//                    typeOfUser = 2;
//                    break;
//                }
//                default: {
//                    typeOfUser = 99;
//                    break;
//                }
//            }
        }
        @Override
        protected void onPreExecute()
        {
//            progressDialog = ProgressDialog.show(LoginActivity.this,"Please wait",
//                    "Loading Data...", false, false);
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }

            try
            {
                if (typeOfUser == 99){
                    return false;
                } else {
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
//                    return true;

                    //Get the current thread's token
                    synchronized (this) {

                        //Initialize an integer (that will act as a counter) to zero
                        int counter = 0;
                        //While the counter is smaller than four
                        while (counter <= 1) {

                            //Wait 850 milliseconds
                            this.wait(250);
                            //Increment the counter
                            counter++;
                            //Set the current progress.
                            //This value is going to be passed to the onProgressUpdate() method.
//                        publishProgress(counter*25);
                        }
                    }
                }
//                return typeOfUser != 99;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
//            return null;
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }
//            if (typeOfUser == 99){
//                return false;
//            } else
//            {
//                Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
//                LoginActivity.this.startActivity(mainIntent);
//                return true;
//            }
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
//            showProgress(false);

            if (success) {


                finish();
                progressDialog.dismiss();

            } else {
                progressDialog.dismiss();
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
//            showProgress(false);
        }
    }

}
