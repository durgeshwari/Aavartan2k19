package com.technocracy.app.aavartan.ui.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.technocracy.app.aavartan.R;
import com.technocracy.app.aavartan.api.APIServices;
import com.technocracy.app.aavartan.api.AppClient;
import com.technocracy.app.aavartan.api.data_models.LoginData;
import com.technocracy.app.aavartan.api.data_models.SignupData;
import com.technocracy.app.aavartan.ui.activities.OTPVerifyActivity;
import com.technocracy.app.aavartan.utils.SessionManager;
import com.technocracy.app.aavartan.utils.ValidationManager;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class SignupFragment extends Fragment {

    private Button buSignup;
    private TextInputEditText etFullName, etEmail, etMobileNumber, etPassword, etConfirmPassword, etCollege, etBranch, etCourse, etSemester, etCity;
    private Dialog progressDialog;
    private RelativeLayout layout;

    private boolean isValidFullName = false, isValidEmail = false, isValidMobileNumber = false, isValidPassword = false, passwordsMatch = false,
            isValidCollege = false, isValidBranch = false, isValidCourse = false, isValidSemester = false, isValidCity = false;
    private int semester;
    private String password, name, email, mobileNumber, college, branch, course, city;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        initView(view);
        setListeners();
        return view;

    }

    private void initView(View view) {

        layout = view.findViewById(R.id.layout);
        etFullName = view.findViewById(R.id.etFullName);
        etEmail = view.findViewById(R.id.etEmail);
        etMobileNumber = view.findViewById(R.id.etMobileNumber);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        etCollege = view.findViewById(R.id.etCollege);
        etBranch = view.findViewById(R.id.etBranch);
        etCourse = view.findViewById(R.id.etCourse);
        etSemester = view.findViewById(R.id.etSemester);
        etCity = view.findViewById(R.id.etCity);
        buSignup = view.findViewById(R.id.buSignup);

    }

    private void setListeners() {

        etFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ValidationManager.isFieldEmpty(Objects.requireNonNull(etFullName.getText()).toString())) {
                    etFullName.setError("Field Cannot be Empty");
                    isValidFullName = false;
                } else isValidFullName = true;
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isValidEmail = false;
                if (ValidationManager.isFieldEmpty(Objects.requireNonNull(etEmail.getText()).toString())) {
                    etEmail.setError("Field Cannot be Empty");
                } else if (ValidationManager.isEmailValid(etEmail.getText().toString())) {
                    etEmail.setError("Enter Valid Email");
                } else isValidEmail = true;
            }
        });

        etMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isValidMobileNumber = false;
                if (ValidationManager.isFieldEmpty(Objects.requireNonNull(etMobileNumber.getText()).toString())) {
                    etMobileNumber.setError("Field Cannot be Empty");
                } else if (ValidationManager.isValidMobileNumber(etMobileNumber.getText().toString())) {
                    etMobileNumber.setError("Enter Valid Mobile Number");
                } else isValidMobileNumber = true;
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isValidPassword = false;
                if (ValidationManager.isFieldEmpty(Objects.requireNonNull(etPassword.getText()).toString())) {
                    etPassword.setError("Field Cannot be Empty");
                } else if (ValidationManager.isValidPassword(etPassword.getText().toString())) {
                    etPassword.setError("Password must contain a letter and a number with length between 8-16 characters");
                } else isValidPassword = true;
            }
        });

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                passwordsMatch = false;
                if (!Objects.requireNonNull(etConfirmPassword.getText()).toString().equals(Objects.requireNonNull(etPassword.getText()).toString())) {
                    etConfirmPassword.setError("Passwords do not match");
                } else passwordsMatch = true;
            }
        });

        etCollege.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isValidCollege = false;
                if (ValidationManager.isFieldEmpty(Objects.requireNonNull(etCollege.getText()).toString())) {
                    etCollege.setError("Field Cannot be Empty");
                } else isValidCollege = true;
            }
        });

        etBranch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isValidBranch = false;
                if (ValidationManager.isFieldEmpty(Objects.requireNonNull(etBranch.getText()).toString())) {
                    etBranch.setError("Field Cannot be Empty");
                } else isValidBranch = true;
            }
        });

        etCourse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isValidCourse = false;
                if (ValidationManager.isFieldEmpty(Objects.requireNonNull(etCourse.getText()).toString())) {
                    etCourse.setError("Field Cannot be Empty");
                } else isValidCourse = true;
            }
        });

        etSemester.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isValidSemester = false;
                if (ValidationManager.isFieldEmpty(Objects.requireNonNull(etSemester.getText()).toString())) {
                    etSemester.setError("Field Cannot be Empty");
                } else if (ValidationManager.isValidSemester(etSemester.getText().toString())) {
                    etSemester.setError("Semester must be a number between 1 - 10");
                } else isValidSemester = true;
            }
        });

        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isValidCity = false;
                if (ValidationManager.isFieldEmpty(Objects.requireNonNull(etCity.getText()).toString())) {
                    etCity.setError("Field Cannot be Empty");
                } else isValidCity = true;
            }
        });

        buSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidFullName && isValidEmail && isValidMobileNumber && isValidPassword && passwordsMatch &&
                        isValidCollege && isValidBranch && isValidCourse && isValidSemester && isValidCity) {
                    password = Objects.requireNonNull(etPassword.getText()).toString();
                    email = Objects.requireNonNull(etEmail.getText()).toString();
                    name = Objects.requireNonNull(etFullName.getText()).toString();
                    mobileNumber = Objects.requireNonNull(etMobileNumber.getText()).toString();
                    college = Objects.requireNonNull(etCollege.getText()).toString();
                    branch = Objects.requireNonNull(etBranch.getText()).toString();
                    course = Objects.requireNonNull(etCourse.getText()).toString();
                    semester = Integer.valueOf(Objects.requireNonNull(etSemester.getText()).toString());
                    city = Objects.requireNonNull(etCity.getText()).toString();
                    setProgressDialog();
                    apiCallSignup();
                } else {
                    Toasty.error(Objects.requireNonNull(getContext()), "One or More Fields are Incorrect", Toasty.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void apiCallSignup() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<SignupData> callCreateUser = apiServices.createUser(password, name, email, mobileNumber, college, branch, course, semester, city);

        callCreateUser.enqueue(new Callback<SignupData>() {
            @Override
            public void onResponse(@NonNull Call<SignupData> call, @NonNull Response<SignupData> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("LOG Signup ", response.body().toString());
                    SessionManager.setUserName(mobileNumber);
                    apiCallLogin();
                } else {
                    Toasty.error(Objects.requireNonNull(getActivity()), "User Already Exists", Toasty.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignupData> call, @NonNull Throwable t) {
//                Log.d("LOG Signup Fail ", t.toString());
                Snackbar.make(layout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        apiCallSignup();
                    }
                }).show();
            }
        });

    }

    private void apiCallLogin() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<LoginData> call = apiServices.getLoginToken(mobileNumber, email, password);

        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(@NonNull Call<LoginData> call, @NonNull Response<LoginData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getUserToken().equals("")) {
//                        Log.d("LOG Login Key", response.body().getUserToken());
                        SessionManager.setIsLoggedIn(true);
                        SessionManager.setUserToken(response.body().getUserToken());
                        Intent intent = new Intent(getActivity(), OTPVerifyActivity.class);
                        Toasty.success(Objects.requireNonNull(getActivity()), "Signing Up", Toasty.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        progressDialog.dismiss();
                        Toasty.error(Objects.requireNonNull(getActivity()), "Enter Valid Credentials", Toasty.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginData> call, @NonNull Throwable t) {
//                Log.d("LOG Login Fail", t.toString());
                progressDialog.dismiss();
                Snackbar.make(layout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        apiCallSignup();
                    }
                }).show();
            }
        });

    }

    private void setProgressDialog() {
        progressDialog = new Dialog(Objects.requireNonNull(getContext()));
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        TextView tvProgressMessage = progressDialog.findViewById(R.id.tvProgressMessage);
        tvProgressMessage.setText(getString(R.string.signing_in_please_wait));
        progressDialog.show();
    }

}