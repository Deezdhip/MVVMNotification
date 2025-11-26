package com.example.mvvmnotification

import android.content.Context
import org.json.JSONArray

class MahasiswaRepository(private val context: Context) {

    fun loadMahasiswaData(): List<Mahasiswa> {
        return try {
            val jsonString = context.assets.open("mahasiswa.json")
                .bufferedReader()
                .use { it.readText() }

            val jsonArray = JSONArray(jsonString)
            val mahasiswaList = mutableListOf<Mahasiswa>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                mahasiswaList.add(
                    Mahasiswa(
                        nama = jsonObject.getString("nama"),
                        nim = jsonObject.getString("nim"),
                        jurusan = jsonObject.getString("jurusan")
                    )
                )
            }

            mahasiswaList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}