package com.example.carekernal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carekernal.R
import com.example.carekernal.api.Communicator

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)


    }

//    override fun passData(mail: String) {
//        val bundle=Bundle()
//        bundle.putString("mail",mail)
//        val transaction=this.supportFragmentManager.beginTransaction()
//        VerifyFragment().arguments=bundle
//        transaction.replace(R.id.fragmentContainerView,VerifyFragment()).commit()
//
//    }


} 