package com.example.praktam2_2417051048

import model.classroom
import model.classroomsource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051048.ui.theme.PrakTAM2_2417051048Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM2_2417051048Theme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ClassroomList(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ClassroomList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(classroomsource.classroom) { classroom ->
            ClassroomCard(
                classroom = classroom,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ClassroomCard(classroom: classroom, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = classroom.imageRes),
            contentDescription = classroom.namaRuang,
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = "Nama Ruang : ${classroom.namaRuang}")
        Text(text = "Status : ${classroom.status}")
        Text(text = "Kapasitas : ${classroom.kapasitas}")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrakTAM2_2417051048Theme {
        ClassroomList()
    }
}
