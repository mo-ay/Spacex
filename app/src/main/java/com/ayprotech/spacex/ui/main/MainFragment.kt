package com.ayprotech.spacex.ui.main

import android.os.Bundle
import android.util.Log
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
import com.ayprotech.spacex.data.network.Resource
import com.ayprotech.spacex.data.network.responses.launche.Launches
import com.ayprotech.spacex.data.network.responses.launche.LaunchesItem
import com.ayprotech.spacex.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment() {


    private var launchDb: List<LaunchesItem>? = null
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
            launchDb = it

        }
        viewModel.launches.observe(viewLifecycleOwner) {
            if (it is Resource.Success && viewModel.didFilter.value == 0) {
                filterAndSave(it.value)
            }
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

    private fun filterAndSave(apiLaunches: Launches) {
        val launchesToSave = Launches()
        for (launch in apiLaunches) {
            if (launch.upcoming)
                launchesToSave.add(launch)
            else if (launch.success) {
                val dateLaunch = toDate(launch.date_utc)
                dateLaunch?.let {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.YEAR, -3)
                    if (it.time >= calendar.timeInMillis) {
                        launchesToSave.add(launch)
                    }
                }
            }
        }

        val newLaunchesIds = launchesToSave.map { it.date_unix }
        val removeExpired =
            launchDb?.filter { !newLaunchesIds.contains(it.date_unix) }?.map { it.date_unix }
        Log.d("filterAndSave: ", "and the $removeExpired")
        removeExpired?.let { viewModel.deleteListLaunches(it) }
        viewModel.saveLaunches(launchesToSave)
        viewModel._didFilter.value = 1

    }

    private fun toDate(s: String): Date? {
        try {
            val formatterr =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val date = formatterr.parse(s)
            return date
        } catch (e: ParseException) {
            return null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}