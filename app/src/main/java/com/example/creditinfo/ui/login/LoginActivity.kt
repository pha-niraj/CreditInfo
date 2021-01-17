package com.example.creditinfo.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

import com.example.creditinfo.R
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private val TAG = "CREDITINFO"
    private var smsVerificationId = ""

    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var sharedPreferences: SharedPreferences

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
            loading.visibility = View.GONE

            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("", "onVerificationFailed", e)
            loading.visibility = View.GONE

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }

            // Show a message and update the UI
            // ...
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("", "onCodeSent:$verificationId")
            loading.visibility = View.GONE

            otp.isEnabled = true
            otp.visibility = View.VISIBLE
            login.text = "SIGN IN"

            // Save verification ID and resending token so we can use them later
            smsVerificationId = verificationId

            // ...
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = getSharedPreferences("Login_info",MODE_PRIVATE)

        if (!sharedPreferences.getBoolean("isLoginSuccessful",false)){
            login.setOnClickListener {
                onSubmitAction()
            }
        }else{
            this.startActivity(Intent(this,MainScreenActivity::class.java))
        }
    }



    private fun onSubmitAction(){

        if (!otp.isEnabled)
        if (mobile_number.text.toString().length!=10){
            Toast.makeText(this,"Enter Valid Mobile Number",Toast.LENGTH_SHORT).show()
        }
        else
        {
           sharedPreferences.edit().putString("MobileNumber",mobile_number.text.toString()).apply()
            sendVerificationCode(mobile_number.text.toString(),firebaseAuth)
            loading.visibility = View.VISIBLE
        }
        else{
            if (otp.length()==0){
                Toast.makeText(this,"Enter Valid OTP",Toast.LENGTH_SHORT).show()
            }
            else{
                verifyPhoneNumberWithCode(smsVerificationId,otp.text.toString())
                loading.visibility = View.VISIBLE
            }
        }
    }

    private fun sendVerificationCode(
        text: String,
        firebaseAuth: FirebaseAuth
    ) {

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+91$text")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("", "signInWithCredential:success")
                    loading.visibility = View.GONE

                    val user = task.result?.user

                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()

                   sharedPreferences.edit().putBoolean("isLoginSuccessful",true).apply()

                    this.startActivity(Intent(this,MainScreenActivity::class.java))
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("", "signInWithCredential:failure", task.exception)
                    loading.visibility = View.GONE
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    sharedPreferences.edit().putBoolean("isLoginSuccessful",false).apply()
                    Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
                }
            }

    }

}
