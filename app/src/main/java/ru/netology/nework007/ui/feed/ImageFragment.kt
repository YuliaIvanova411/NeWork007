package ru.netology.nework007.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework007.MainActivity.Companion.textArg
import ru.netology.nework007.databinding.FragmentImageBinding
import ru.netology.nework007.utils.load

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ImageFragment: Fragment() {


        lateinit var binding: FragmentImageBinding
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentImageBinding.inflate(inflater, container, false)

            val urlImages = "${arguments?.textArg}"
            binding.fullscreenImage.load(urlImages)

            return binding.root
        }
    }