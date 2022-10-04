package com.example.carekernal.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.carekernal.R
import com.example.carekernal.api.ApiService
import com.example.carekernal.api.Resource
import com.example.carekernal.databinding.FragmentLoginBinding
import com.example.carekernal.repository.AuthRepository
import com.example.carekernal.ui.base.BaseFragment
import com.example.carekernal.viewmodel.AuthViewModel
import org.json.JSONException
import org.json.JSONObject

@Suppress("DEPRECATION")
class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Sucess -> {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("loginResponse", it.toString())
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
                            Log.d("loginResponse", "Error: ${error?.getString("message")}")

                        } catch (e: JSONException) {
                        }
                    }
                }

            }
        })
        binding.loginBtn.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val password = binding.inputPassword.toString().trim()
            viewModel.login(email, password)
        }
        binding.signUp.setOnClickListener {
            val action=LoginFragmentDirections.actionLoginFragmentToSingUpFragment()
            it.findNavController().navigate(action)

        }
        binding.forgetPassword.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            it.findNavController().navigate(action)
        }

    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(loginDataSource.buildApi(ApiService::class.java))

}