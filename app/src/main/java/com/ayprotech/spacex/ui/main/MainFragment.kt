package com.ayprotech.spacex.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ayprotech.spacex.R
import com.ayprotech.spacex.adapter.LaunchClick
import com.ayprotech.spacex.adapter.LaunchesAdapter
import com.ayprotech.spacex.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {


    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var launchesAdapter: LaunchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = MainFragmentBinding.inflate(inflater, container, false)
        launchesAdapter = LaunchesAdapter(LaunchClick {
            viewModel.rocketClicked(it)
        })
        binding.root.findViewById<RecyclerView>(R.id.launches_res).apply {
            adapter = launchesAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLaunchesDb().observe(viewLifecycleOwner) {
            launchesAdapter.launchItems = it
        }

        viewModel.navigateToRocketScreen.observe(viewLifecycleOwner) {
            it?.let {
                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToRocketFragment(it)
                )
                viewModel.onDoneNavigateToRocket()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}