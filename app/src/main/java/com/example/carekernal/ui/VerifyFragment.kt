package com.example.carekernal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.carekernal.R
import com.example.carekernal.api.ApiService
import com.example.carekernal.api.Resource
import com.example.carekernal.databinding.FragmentForgotPasswordBinding
import com.example.carekernal.databinding.FragmentOtpBinding
import com.example.carekernal.repository.AuthRepository
import com.example.carekernal.ui.base.BaseFragment
import com.example.carekernal.viewmodel.AuthViewModel
import org.json.JSONException
import org.json.JSONObject
import androidx.navigation.findNavController

class VerifyFragment : BaseFragment<AuthViewModel, FragmentOtpBinding, AuthRepository>() {
//   var output: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.verifyResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Sucess -> {
                    Toast.makeText(requireContext(), "Otp Verified", Toast.LENGTH_SHORT).show()

                }
                is Resource.Failure -> {
                    val error = it.errorBody?.charStream()?.readText()
                        ?.let { it1 -> JSONObject(it1) }

                    error.let {
                        try {
                            Toast.makeText(
                                requireContext(),
                                " ${error?.getString("message")}",
                                Toast.LENGTH_SHORT
                            ).show()

                        } catch (e: JSONException) {
                        }
                    }
                }
            }

        })
        binding.otpSend.setOnClickListener {
            val code = binding.otpView.text.toString()
//            output = binding.txt.text.toString()
//            output = arguments?.getString("mail").toString()
//            email=output
//            if (email != null) {
//            viewModel.verifyOtp(code, output)
//            }
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val action = VerifyFragmentDirections.actionVerifyFragmentToLoginFragment()
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(action)
                }
            })
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOtpBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(loginDataSource.buildApi(ApiService::class.java))
}