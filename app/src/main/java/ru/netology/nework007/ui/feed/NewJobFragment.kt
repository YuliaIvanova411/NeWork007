package ru.netology.nework007.ui.feed

import android.annotation.SuppressLint
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework007.databinding.NewJobBinding
import ru.netology.nework007.date.DateJob
import ru.netology.nework007.dialog.DialogSelectRemoveJob
import ru.netology.nework007.dto.Job
import ru.netology.nework007.ui.users.UsersViewModel
import ru.netology.nework007.util.AndroidUtils.getTimeJob
import ru.netology.nework007.viewmodel.AuthViewModel.Companion.myID
import ru.netology.nework007.viewmodel.UsersViewModel


@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class NewJobFragment : Fragment() {
    private var startDate: String? = null
    private var finishDate: String? = null
    var binding: NewJobBinding? = null

    private val viewModelUser: UsersViewModel by viewModels()

    private fun showBar(txt: String) {
        Snackbar.make(
            binding!!.root,
            txt,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var lastStateLoading = false

        binding = NewJobBinding.inflate(layoutInflater)

        binding?.let {
            with(it) {
                dateJob.setOnClickListener {
                    DialogSelectRemoveJob.newInstance(SELECT_DATE, 0)
                        .show(childFragmentManager, "TAG")
                }

                buttonCreate.setOnClickListener {
                    if (
                        newCompanyName.editText?.text?.isEmpty() == true ||
                        newPosition.editText?.text?.isEmpty() == true
                    ) {
                        showBar("Поля имя и должность должны быть заполнены!")
                    } else {
                        val name = newCompanyName.editText?.text.toString()
                        val position = newPosition.editText?.text.toString()
                        val link = newLink.editText?.text.toString()
                        val job = Job(0, myID, name, position, startDate, finishDate, link)
                        viewModelUser.saveJob(job)
                    }
                }
            }
        }

        viewModelUser.dataState.observe(viewLifecycleOwner) {
            binding?.progressLoad?.isVisible = it.loadingJob
            if (it.error403) showBar("Ошибка авторизации,отказано в доступе!")
            if (it.error404) showBar("Запись не найдена!")
            if (it.error400) showBar("Не указана дата или некорректно введены данные!")
            if (it.error) showBar("Проверьте ваше подключение к сети!")
            if (!it.loadingJob && lastStateLoading) findNavController().navigateUp()
            lastStateLoading = it.loadingJob
        }

        return binding!!.root
    }

    @SuppressLint("SetTextI18n")
    fun getDateJob(date: DateJob) {
        startDate = date.dateStart
        finishDate = date.dateEnd
        binding?.dateJob?.text = "${getTimeJob(startDate)} - ${getTimeJob(finishDate)}"
    }
}