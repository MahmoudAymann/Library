package com.nagwa.library

import android.content.res.AssetManager
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HomeTest {
    private fun AssetManager.readAssetsFile(fileName : String): String = open(fileName).bufferedReader().use{it.readText()}

    @Test
    fun testingJsonFileRes(){
//        val loader = this.javaClass.classLoader?.getResource("getListOfFilesResponse.json")
//        val gson = Gson()
//        val todoItem: LibraryResponse = gson.fromJson(loader.readAssetsFile("versus.json"), TodoItem::class.java)
//        assertNotNull(loader)
    }

}