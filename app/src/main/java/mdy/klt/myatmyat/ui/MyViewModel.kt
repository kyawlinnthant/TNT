package mdy.klt.myatmyat.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mdy.klt.myatmyat.repository.HistoryRepository
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repo : HistoryRepository
) : ViewModel() {

}