package com.project.sehatqtest.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.project.sehatqtest.R
import com.project.sehatqtest.view.detail.ProductDetailActivity


object IntentHelper {

    fun launchProductDetail(productId: Int?, context: Context){
        context.startActivity(Intent(context, ProductDetailActivity::class.java).apply {
            putExtra(ProductDetailActivity.PRODUCT_ID_KEY, productId)
        })
    }

    fun shareContent(subject: String, body: String, context: Context){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, body)
        context.startActivity(Intent.createChooser(intent,context.resources.getString(R.string.share_to)))
    }

    const val GOOGLE_SIGN_IN_REQUEST_CODE = 2001

    fun googleSignInIntent(activity: Activity, googleSignInClient: GoogleSignInClient){
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }
}