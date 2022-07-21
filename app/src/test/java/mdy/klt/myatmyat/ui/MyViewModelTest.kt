package mdy.klt.myatmyat.ui

import mdy.klt.myatmyat.repository.FakeHistoryRepository
import org.junit.After
import org.junit.Before

class MyViewModelTest{
    private lateinit var vm : MyViewModel

    @Before
    fun setup() {
        vm = MyViewModel(repo = FakeHistoryRepository())
    }

    @After
    fun teardown() {

    }

}