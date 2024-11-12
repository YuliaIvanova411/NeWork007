package ru.netology.nework007.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework007.databinding.FragmentMyWallBinding

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyWallFragment : Fragment() {

    lateinit var binding: FragmentMyWallBinding

//    private val viewModel: PostViewModel by activityViewModels()
//    private val myWallViewModel: MyWallViewModel by activityViewModels()
//
//    @Inject
//    lateinit var appAuth: AppAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMyWallBinding.inflate(inflater, container, false)


        return binding.root
    }

}