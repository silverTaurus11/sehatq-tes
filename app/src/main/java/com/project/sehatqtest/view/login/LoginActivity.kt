package com.project.sehatqtest.view.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.sehatqtest.R
import com.project.sehatqtest.data.source.local.LOGIN_VIA_FACEBOOK
import com.project.sehatqtest.data.source.local.LOGIN_VIA_GOOGLE
import com.project.sehatqtest.data.source.local.LOGIN_VIA_NORMAL
import com.project.sehatqtest.data.source.remote.model.ProfileItem
import com.project.sehatqtest.databinding.ActivityLoginBinding
import com.project.sehatqtest.helper.IntentHelper
import com.project.sehatqtest.helper.Utils
import com.project.sehatqtest.view.frontview.FrontViewActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(this, gso)
    }

    //Facebook
    private val callbackManager by lazy { CallbackManager.Factory.create() }
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initHasBeenLoginFlagObserver()
        initSignInButton(binding)
        initFormValidationObserver(binding)
        initFacebookButton(binding)
        initGoogleButton(binding)
    }

    private fun initHasBeenLoginFlagObserver(){
        loginViewModel.isUserAlreadyLogin.observe(this){
            if(it){
                launchHomeActivity()
            } else{
                loginViewModel.clearCache()
            }
        }
    }

    private fun initFormValidationObserver(binding: ActivityLoginBinding){
        loginViewModel.formValidation.observe(this){
            when(it){
                LoginViewModel.Validation.USERNAME_INVALID ->
                    binding.usernameEditText.error = getString(R.string.username_is_required)
                LoginViewModel.Validation.PASSWORD_INVALID ->
                    binding.passwordEditText.error = getString(R.string.password_is_required)
                else -> return@observe
            }
        }
    }

    private fun initSignInButton(binding: ActivityLoginBinding){
        binding.signInButton.setOnClickListener {
            val userName = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val isRemember = binding.rememberCheckBox.isChecked

            loginViewModel.doLogin(ProfileItem(userName, password, LOGIN_VIA_NORMAL), isRemember).observe(
                this
            ){
                if(it){
                    launchHomeActivity()
                }
            }
            Utils.hideSoftKeyboard(this)
        }
    }

    private fun launchHomeActivity(){
        startActivity(Intent(this, FrontViewActivity::class.java))
        finish()
    }

    private fun initFacebookButton(binding: ActivityLoginBinding){
        binding.facebookButton.setPermissions("email")
        binding.facebookButton.loginBehavior = LoginBehavior.WEB_VIEW_ONLY
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    val accessToken = AccessToken.getCurrentAccessToken()
                    val isLoggedIn = accessToken != null && !accessToken.isExpired
                    if (isLoggedIn) {
                        getFacebookData(accessToken) { email, _ ->
                            loginViewModel.doLoginWithThirdParty(
                                ProfileItem(
                                    email, "",
                                    LOGIN_VIA_FACEBOOK, accessToken.token
                                )
                            )
                        }
                    }
                }

                override fun onCancel() {
                    Log.i("FacebookLogin", "Login Canceled")
                }

                override fun onError(error: FacebookException?) {
                    Toast.makeText(
                        this@LoginActivity,
                        error?.message ?: "Facebook Login Failed", Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    private fun getFacebookData(
        accessToken: AccessToken,
        nextAction: (email: String, name: String) -> Unit
    ){
        val graphRequest = GraphRequest.newMeRequest(
            accessToken
        ) { _, response ->
            val json = response.jsonObject
            try {
                if (json != null) {
                    val email = json.getString("email")
                    val name = json.getString("name")
                    nextAction(email, name)
                    Log.e("Facebook NAME", name)
                    Log.e("Facebook EMAIL", email)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "name,email")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()
    }

    private fun initGoogleButton(binding: ActivityLoginBinding){
        binding.googleButton.setOnClickListener {
            IntentHelper.googleSignInIntent(this, googleSignInClient)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IntentHelper.GOOGLE_SIGN_IN_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account?.idToken)
                } catch (e: ApiException) {
                    Log.e("Google Sign In", e.message?:"Google Login Failed")
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        if(idToken.isNullOrEmpty()) return
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if(user != null){
                        loginViewModel.doLoginWithThirdParty(ProfileItem(user.email, "",
                            LOGIN_VIA_GOOGLE, idToken))
                    }
                } else {
                    Toast.makeText(this, "Google Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}