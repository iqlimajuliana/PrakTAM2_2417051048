package com.example.praktam2_2417051048.data.repository
import com.example.praktam2_2417051048.data.api.RetrofitClient
import com.example.praktam2_2417051048.data.model.classroom
import android.util.Log



class ClassroomRepository {
    suspend fun getClassrooms(): List<classroom> {
        return try {
            RetrofitClient.instance.getClassrooms()
        } catch (e: Exception) {
            Log.e("API_ERROR", e.toString())
            emptyList()
        }
    }

}
