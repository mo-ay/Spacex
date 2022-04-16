package com.ayprotech.spacex.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.ayprotech.spacex.data.network.Resource
import com.ayprotech.spacex.data.network.responses.launche.Launches
import com.ayprotech.spacex.data.network.responses.launche.LaunchesItem
import com.ayprotech.spacex.data.repositories.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: LaunchRepository
) : ViewModel() {
    private val _launches: MutableLiveData<Resource<Launches>> = MutableLiveData()
    val launches: LiveData<Resource<Launches>> get() = _launches


    val _didFilter = MutableLiveData(0)
    val didFilter get() = _didFilter as LiveData<Int>


    private val _navigateToRocketScreen = MutableLiveData<String?>()
    val navigateToRocketScreen
        get() = _navigateToRocketScreen



    fun getLaunchesDb() = repository.getLaunchesDb()


     fun saveLaunches(launches: Launches) = viewModelScope.launch {
        repository.saveLaunches(launches)
    }
    suspend fun deleteAllLaunches() = repository.deleteLaunchesDb()
    fun deleteListLaunches(list: List<Int>) = viewModelScope.launch {
            repository.deleteListLaunchesDb(list)
    }

        init {

            getLaunchApi()
        }

        fun getLaunchApi() = viewModelScope.launch {
            _launches.value = Resource.Loading
            _launches.value = repository.getLaunchesCall()
//        if (launches.value is Resource.Success) {
//            filterAndSave((launches.value as Resource.Success<Launches>).value)
//
//        }
        }

//        private suspend fun filterAndSave(launches: Launches) {
//            val launchesToSave = Launches()
//            for (launch in launches) {
//                if (launch.upcoming)
//                    launchesToSave.add(launch)
//                else if (launch.success) {
//                    val dateLaunch = toDate(launch.date_utc)
//                    dateLaunch?.let {
//                        val calendar = Calendar.getInstance()
//                        calendar.add(Calendar.YEAR, -3)
//                        if (it.time >= calendar.timeInMillis) {
//                            launchesToSave.add(launch)
//                        }
//                    }
//                }
//            }
//
//            val newLaunchesIds = launchesToSave.map { it.date_unix }
//            val removeExpired = getLaunchesDb().value?.filter { newLaunchesIds.contains(it.date_unix) }
//            val m = getLaunchesDb().value
//            if (removeExpired != null) {
//                val finalList:Launches= launchesToSave.minus(removeExpired) as Launches
//                saveLaunches(finalList)
//            }
//
//
//
//        }
//
//        private fun toDate(s: String): Date? {
//            try {
//                val formatterr =
//                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
//                val date = formatterr.parse(s)
//                return date
//            } catch (e: ParseException) {
//                return null
//            }
//        }


        fun rocketClicked(id: String) {
            _navigateToRocketScreen.value = id
        }

        fun onDoneNavigateToRocket() {
            _navigateToRocketScreen.value = null
        }
    }


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: LaunchRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}