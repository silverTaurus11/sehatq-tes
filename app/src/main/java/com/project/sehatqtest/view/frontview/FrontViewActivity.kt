package com.project.sehatqtest.view.frontview

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.sehatqtest.R
import com.project.sehatqtest.data.source.local.LOGIN_VIA_FACEBOOK
import com.project.sehatqtest.data.source.local.LOGIN_VIA_GOOGLE
import com.project.sehatqtest.data.source.local.LOGIN_VIA_NORMAL
import com.project.sehatqtest.databinding.ActivityHomeBinding
import com.project.sehatqtest.view.adapter.MainViewPagerAdapter
import com.project.sehatqtest.view.frontview.menu.home.HomeFragment
import com.project.sehatqtest.view.logout.LogoutDialog
import com.project.sehatqtest.view.purchase.PurchaseHistoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FrontViewActivity: AppCompatActivity() {

    //Google
    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(this, gso)
    }

    private val viewPagerAdapter by lazy { MainViewPagerAdapter(this) }
    private val frontViewModel: FrontViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.homeViewPager.adapter = viewPagerAdapter
        binding.homeViewPager.offscreenPageLimit = viewPagerAdapter.itemCount
        binding.homeViewPager.isUserInputEnabled = false
        binding.navigation.setOnNavigationItemSelectedListener(setBottomNavigationListener(binding))
    }

    private fun setBottomNavigationListener(binding: ActivityHomeBinding) =
            BottomNavigationView.OnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.home_navigation -> {
                        binding.homeViewPager.setCurrentItem(viewPagerAdapter.getFragmentPosition<HomeFragment>(), true)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.profile_navigation -> {
                        startActivity(Intent(this, PurchaseHistoryActivity::class.java))
                    }
                    R.id.cart_navigation -> {
                        logout()
                    }
                }
                false
            }

    private fun logout(){
        when(frontViewModel.getLoginStatus()){
            LOGIN_VIA_NORMAL -> showLogoutDialog()
            LOGIN_VIA_FACEBOOK -> {
                showLogoutDialog {
                    AccessToken.setCurrentAccessToken(null)
                    LoginManager.getInstance().logOut()
                }
            }
            LOGIN_VIA_GOOGLE -> {
                showLogoutDialog {
                    googleSignInClient.signOut().addOnCompleteListener {
                        googleSignInClient.revokeAccess()
                    }
                }
            }
        }
    }

    private fun showLogoutDialog(thirdPartyLogoutAction:()-> Unit={}){
        if(supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) == null){
            LogoutDialog().apply {
                isCancelable = false
                this.thirdPartyLogoutAction = thirdPartyLogoutAction
            }.show(supportFragmentManager, LogoutDialog.TAG)
        }
    }

}