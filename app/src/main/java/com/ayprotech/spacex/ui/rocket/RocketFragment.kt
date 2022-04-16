package com.ayprotech.spacex.ui.rocket

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ayprotech.spacex.data.network.Resource
import com.ayprotech.spacex.databinding.RocketFragmentBinding
import com.ayprotech.spacex.util.handleApiError
import com.ayprotech.spacex.util.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RocketFragment : Fragment() {
    private var _binding: RocketFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<RocketViewModel>()
    private val arguments: RocketFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RocketFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var wikiLink :String? = null
        viewModel.getRocketAPI(arguments.rocketId)
        viewModel.rocket.observe(viewLifecycleOwner) {
            binding.progressBar3.visible(it is Resource.Loading)
            if (it is Resource.Success) {
                binding.rocket = it.value
                wikiLink = it.value.wikipedia
            } else if (it is Resource.Failure) {
                handleApiError(it) { viewModel.getRocketAPI(arguments.rocketId) }
            }
        }
        binding.close.setOnClickListener {
            this.findNavController().navigate(
                RocketFragmentDirections.actionRocketFragmentToMainFragment()
            )
        }
        binding.readMore.setOnClickListener {
            wikiLink?.let {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}