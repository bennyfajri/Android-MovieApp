package com.drsync.core.data.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.drsync.core.utils.DataDummy
import com.drsync.core.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieDao
    private val sampleMovie = DataDummy.generateDummyMovie()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.movieDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertMovie_Success() = runBlocking {
        dao.insertMovie(sampleMovie)
        val actualMovie = dao.getAllMovie().asLiveData().getOrAwaitValue()
        Assert.assertEquals(sampleMovie, actualMovie)
    }
}