package mdy.klt.myatmyat.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import mdy.klt.myatmyat.data.PayOff
import mdy.klt.myatmyat.repository.HistoryRepository
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repo : HistoryRepository
) : ViewModel() {

 suspend fun saveToDb(list: PayOff) {
     repo.addItem(payOff = list)
 }

    suspend fun getFromDb(): Flow<List<PayOff>> {
        return repo.getItems()
    }

}