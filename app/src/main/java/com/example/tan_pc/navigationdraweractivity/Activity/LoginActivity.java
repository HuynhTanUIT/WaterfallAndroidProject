package com.example.tan_pc.navigationdraweractivity.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tan_pc.navigationdraweractivity.R;

import java.util.List;

import ClientSocket.GetDataService;
import ClientSocket.RetrofitClientInstance;
import ClientSocket.account;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private EditText mEmailView;
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
//        mEmailSignInButton.setOnClickListener(btnClickListener);


        mEmailView = (EditText) findViewById(R.id.input_name);
//        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.input_password);

        mLoginFormView = findViewById(R.id.login_form);
        mEnButton.setOnClickListener(onEngLanguageCLick());
        mVnButton.setOnClickListener(onVnLanguageCLick());
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


//        mProgressView = findViewById(R.id.login_progress);

//        setContentView(R.layout.waterfallsplash);
//        new LoadViewTask().execute();

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
//    private View.OnClickListener onToggleLanguageCLick() {
//
//        Toast.makeText(getApplicationContext(),"getCurrentLanguage", Toast.LENGTH_SHORT).show();
//        if (getCurrentLanguage().equals("en")) {
//            return view -> setLanguage("vn");
//        } else {
//            return view -> setLanguage("en");
//        }
//    }
// Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();

    /*
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(Login.this,"Please wait",
                    "Loading Data...", false, false);
        }

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Intent mainIntent = new Intent(Login.this,MainActivity.class);
                Login.this.startActivity(mainIntent);

                //Get the current thread's token
                synchronized (this)
                {
                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while(counter <= 1)
                    {
                        //Wait 850 milliseconds
                        this.wait(250);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
                        publishProgress(counter*25);
                    }
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            //set the current progress of the progress dialog
//            progressDialog.setProgress(values[0]);
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result)
        {

            finish();
            progressDialog.dismiss();

            //close the progress dialog

        }
    }
    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            SetEditTextFocus();
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            switch (view.getId()) {
                case R.id.btn_login:
//                            setContentView(R.layout.waterfallsplash);
                            new LoadViewTask().execute();
//                    edtHeightHome.setFocusable(true);
//                    edtHeightHome.setFocusableInTouchMode(true);
//                    edtHeightHome.requestFocus();
//                    edtHeightHome.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
                    break;
//                case R.id.HomeLinearLayout:
////                    edtIP.setError(null);
//                    ClearEditTextFocus();
//
//                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
//
//                    LoadThresholdValves();
//                    break;
            }
        }
    };
*/
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
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String user = mEmailView.getText().toString().trim();
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
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
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

            mAuthTask = new UserLoginTask(user, password);
            mAuthTask.execute((Void) null);
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

        UserLoginTask(String email, String password) {
            mUser = email;
            mPassword = password;
            switch (mUser){
                case "admin" : {
                    typeOfUser = 0;
                    break;
                }
                case "customer" : {
                    typeOfUser = 1;
                    break;
                }
                case "user" : {
                    typeOfUser = 2;
                    break;
                }
                default: {
                    typeOfUser = 99;
                    break;
                }
            }
        }
        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(LoginActivity.this,"Please wait",
                    "Loading Data...", false, false);
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
//                    return false;
                } else
                {
                    Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
//                    return true;
                }
                //Get the current thread's token
                synchronized (this)
                {

                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while(counter <= 1)
                    {

                        //Wait 850 milliseconds
                        this.wait(250);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
//                        publishProgress(counter*25);
                    }
                }
                if (typeOfUser == 99){
                    return false;
                } else
                {
//                    Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
//                    LoginActivity.this.startActivity(mainIntent);
                    return true;
                }
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
