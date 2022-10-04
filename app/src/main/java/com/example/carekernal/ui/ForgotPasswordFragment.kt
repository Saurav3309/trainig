package com.example.carekernal.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.carekernal.api.ApiService
import com.example.carekernal.api.Resource
import com.example.carekernal.databinding.FragmentForgotPasswordBinding
import com.example.carekernal.repository.AuthRepository
import com.example.carekernal.ui.base.BaseFragment
import com.example.carekernal.viewmodel.AuthViewModel
import org.json.JSONException
import org.json.JSONObject
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.example.carekernal.R
import com.example.carekernal.api.Communicator


@Suppress("DEPRECATION")
class ForgotPasswordFragment :
    BaseFragment<AuthViewModel, FragmentForgotPasswordBinding, AuthRepository>() {
//    private lateinit var communicator: Communicator


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.forgotResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Sucess -> {
                    Toast.makeText(
                        requireContext(), "please check your email",
                        Toast.LENGTH_SHORT
                    ).show()
                    val action =
                        ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerifyFragment()
                    findNavController(
                        requireActivity(),
                        R.id.fragmentContainerView
                    ).navigate(action)

                }
                is Resource.Failure -> {
                    if (it.isNetworkError) {
                        Log.d("forgotResponse", "NetworkError: ${it.isNetworkError} ")
                        Toast.makeText(
                            requireContext(),
                            "NetworkError: ${it.isNetworkError} ",
                            Toast.LENGTH_SHORT
                        ).show()


                    } else {
                        val error = it.errorBody?.charStream()?.readText()
                            ?.let { it1 -> JSONObject(it1) }
                        Log.d("forgotResponse", "ErrorCode: ${it.errorCode} Error: $error ")
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
            }
        })
//        communicator = activity as Communicator
        binding.btnRecover.setOnClickListener {
            validation()

        }

    }


    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentForgotPasswordBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(loginDataSource.buildApi(ApiService::class.java))

    fun validation() {
        var right = true
        val email = binding.recoveryEmail.text.toString().trim()
        if (email.isNullOrEmpty()) {
            binding.recoveryEmail.setError("Email can't be empty")
            right = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.recoveryEmail.setError("Invalid email address")
            right = false
        }
        if (right) {
//            binding.recoveryEmail.setText("")
            viewModel.forgotPassword(email)

        }
    }
}
