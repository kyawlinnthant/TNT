package mdy.klt.myatmyat.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class HistoryDaoTest {

    private lateinit var database: HistoryDatabase
    private lateinit var dao: HistoryDao

    @Before
    fun setup() {
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                HistoryDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()

        dao = database.dao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert_data_successfully() = runTest {
        val payoff = PayOff(
            id = 1,
            timeStamp = 12345,
            netBalance = 10f,
            tnt = 10f,
            each = 10f,
            isMorning = false
        )
        dao.insertPayOff(payoff)
        val payoffs = dao.getAllHistory().firstOrNull()
        assertThat(payoffs).contains(payoff)
    }

    @Test
    fun delete_data_successfully() = runTest {
        val payoff = PayOff(
            id = 1,
            timeStamp = 12345,
            netBalance = 10f,
            tnt = 10f,
            each = 10f,
            isMorning = false
        )
        dao.insertPayOff(payoff)
        dao.deleteHistory(payoff)
        val payoffs = dao.getAllHistory().firstOrNull()
        assertThat(payoffs).doesNotContain(payoff)
    }

    @Test
    fun all_history_descending_by_timestamp_output_correctly() = runTest {
        val payoff1 = PayOff(
            id = 1,
            timeStamp = 1,
            netBalance = 10f,
            tnt = 10f,
            each = 10f,
            isMorning = false
        )
        val payoff2 = PayOff(
            id = 2,
            timeStamp = 2,
            netBalance = 10f,
            tnt = 10f,
            each = 20f,
            isMorning = false
        )
        val payoff3 = PayOff(
            id = 3,
            timeStamp = 3,
            netBalance = 10f,
            tnt = 10f,
            each = 30f,
            isMorning = false
        )
        dao.insertPayOff(payoff1)
        dao.insertPayOff(payoff2)
        dao.insertPayOff(payoff3)
        val payoffs = dao.getAllHistory().firstOrNull()
        assertThat(payoffs?.first()?.each).isGreaterThan(payoffs?.get(1)?.each)
    }

    @Test
    fun total_net_balance_sum_correctly() = runTest {
        val payoff1 = PayOff(
            id = 1,
            timeStamp = 1,
            netBalance = 10f,
            tnt = 10f,
            each = 10f,
            isMorning = false
        )
        val payoff2 = PayOff(
            id = 2,
            timeStamp = 2,
            netBalance = 10f,
            tnt = 10f,
            each = 20f,
            isMorning = false
        )
        val payoff3 = PayOff(
            id = 3,
            timeStamp = 3,
            netBalance = 10f,
            tnt = 10f,
            each = 30f,
            isMorning = false
        )
        dao.insertPayOff(payoff1)
        dao.insertPayOff(payoff2)
        dao.insertPayOff(payoff3)
        val totalSum = dao.getTotalNetBalance().firstOrNull()
        assertThat(totalSum).isEqualTo(payoff1.netBalance + payoff2.netBalance + payoff3.netBalance)
    }
}