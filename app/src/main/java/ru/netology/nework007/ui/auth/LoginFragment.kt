package ru.netology.nework007.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nework007.R
import ru.netology.nework007.databinding.FragmentLoginBinding
ru.netology.nework007.error.UnknownError
ru.netology.nework007.viewmodel.AuthViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private var pressBtn = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(layoutInflater)

        fun showBar(txt: String) {
            Snackbar.make(
                binding.root,
                txt,
                Snackbar.LENGTH_LONG
            ).show()
        }

        with(binding) {

            viewModel.authState.value?.let {
                enterLogin.editText?.setText(viewModel.authState.value!!.login)
                enterPassword.editText?.setText(viewModel.authState.value!!.pass)
            }
           loginButton.setOnClickListener {
                if (
                    enterLogin.editText?.text?.isEmpty() == true ||
                    enterPassword.editText?.text?.isEmpty() == true
                ) {
                    showBar("Все поля должны быть заполнены!")
                } else {
                    pressBtn = true
                    val login = enterLogin.editText?.text.toString()
                    val pass = enterPassword.editText?.text.toString()
                    viewModel.getAuthFromServer(login, pass)
                }
            }


        }

        viewModel.authState.observe(viewLifecycleOwner) { auth ->
            if (pressBtn) {
                if (auth.login != null && auth.pass != null) {
                    findNavController().popBackStack()
                } else {
                    AuthViewModel.userAuth = false
                    showBar("Такого пользователя нет!")
                }

            }
        }

        viewModel.dataState.observe(viewLifecycleOwner) {
            if (AuthViewModel.userAuth) {
//                viewModel.
                showBar("Выполнен вход в аккаунт")
                //findNavController().popBackStack()
                findNavController().navigateUp()
            }
            if (it.error400) showBar("Неправильный пароль!")
            if (it.error404) showBar("Пользователь не зарегистрирован!")
            if (it.error) showBar("Проверьте ваше подключение к сети!")
            binding.statusAuth.isVisible = it.loading
//            println("it = $it")
        }
        return binding.root
    }
    private var curFrag: CurrentShowFragment? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            curFrag = context as CurrentShowFragment
        } catch (e: ClassCastException) {
            throw UnknownError
        }
    }

    override fun onDetach() {
        super.onDetach()
        curFrag?.getCurFragmentDetach()
        curFrag = null
    }

    override fun onStart() {
        super.onStart()
        curFrag?.getCurFragmentAttach(getString(R.string.sign_in))

    }

}