package com.programmergabut.larikuy.ui.viewmodels


/* @ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup(){
        viewModel = MainViewModel(FakeMainRepository())
    }

    @Test
    fun `insert run with empty field, return error`(){

        val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
        val run = Run(
            img = bmp,
            timestamp = 99.toLong(),
            avgSpeedInKMH = 99.toFloat(),
            distanceInMater = 99,
            timeInMillis = 99.toLong(),
            caloriesBurned = 99
        )

        viewModel.insertRun(run)

        val value = viewModel.runs.getOrAwaitValueTest()
        assertThat(value).isEqualTo(run)
    }

    /* @Test
    fun `insert run with valid input, insert success`(){
        viewModel.insertRun()
    }

    @Test
    fun `delete run, delete success`(){
        viewModel.deleteRun()
    }

    @Test
    fun `sort run by date, run sorted by date`(){
        viewModel.sortRuns()
    } */

} */