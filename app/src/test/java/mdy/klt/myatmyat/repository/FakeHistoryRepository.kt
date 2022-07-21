package mdy.klt.myatmyat.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mdy.klt.myatmyat.data.PayOff

class FakeHistoryRepository : HistoryRepository {

    private val histories = mutableListOf<PayOff>()

    override suspend fun addItem(payOff: PayOff) {
        histories.add(payOff)
    }

    override suspend fun deleteItem(payOff: PayOff) {
        histories.remove(payOff)
    }

    override suspend fun getItems(): Flow<List<PayOff>> {
        return flow {
            emit(histories)
        }
    }

    override suspend fun getNetBalance(): Flow<Float> {
        val netBalance = histories.asSequence().map { it.netBalance }.sum()
        val netBalance2 = histories.sumOf { it.netBalance.toDouble() }.toFloat()
        return flow {
            emit(netBalance)
        }
    }

}