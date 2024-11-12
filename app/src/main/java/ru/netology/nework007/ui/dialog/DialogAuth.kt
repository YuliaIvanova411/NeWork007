package ru.netology.nework007.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import ru.netology.nework007.R
import ru.netology.nework007.error.UnknownError
import ru.netology.nework007.databinding.FragmentDialogBinding
import ru.netology.nework007.viewmodel.AuthViewModel.Companion.DIALOG_IN
import ru.netology.nework007.viewmodel.AuthViewModel.Companion.DIALOG_OUT
import ru.netology.nework007.viewmodel.AuthViewModel.Companion.DIALOG_REG


class DialogAuth : DialogFragment() {
    private var sel = 0
    private var message = ""

    companion object {
        private const val SEL_DIALOG = "SEL_DIALOG"
        private const val SEL_MESSAGE = "SEL_MESSAGE"
        val args = Bundle()
        fun newInstance(select: Int, txt: String): DialogAuth {
            args.putInt(SEL_DIALOG, select)
            args.putString(SEL_MESSAGE, txt)
            return DialogAuth()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDialogBinding.inflate(layoutInflater)
        sel = args.getInt(SEL_DIALOG)
        message = args.getString(SEL_MESSAGE).toString()

        with(binding) {
            textDialog.text = message
            when (sel) {
                DIALOG_IN -> {
                    //textDialog.text = "Для установки лайков нужна авторизация, выполнить вход?"
                    buttonLogin.setOnClickListener {
                        //findNavController().navigate(R.id.authFragment)
                        backValue?.returnDialogValue(DIALOG_IN)
                        dismiss()
                    }
                    buttonRegister.setOnClickListener {
                        backValue?.returnDialogValue(DIALOG_REG)
                        dismiss()
                    }
                }

                DIALOG_OUT -> {
//                    textDialog.text = "Вы хотите удалить регистрацию?"
                    buttonLogin.text = getString(R.string.yes)
                    buttonRegister.text = getString(R.string.no)
                    buttonLogin.setOnClickListener {
                        backValue?.returnDialogValue(DIALOG_OUT)
                        dismiss()
                    }
                    buttonLogin.setOnClickListener {
                        dismiss()
                    }
                }

                DIALOG_REG -> {
//                    textDialog.text = "Регистрация пользователя"
                    buttonLogin.setOnClickListener {
                        backValue?.returnDialogValue(DIALOG_REG)
                        dismiss()
                    }

                }
            }

//            btnNo.setOnClickListener {
//                dismiss()
//            }


        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private var backValue: ReturnSelection? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            backValue = context as ReturnSelection
        } catch (e: ClassCastException) {
            throw UnknownError
        }
    }

    override fun onDetach() {
        super.onDetach()
        backValue = null
    }


    interface ReturnSelection {
        fun returnDialogValue(select: Int)
    }
}