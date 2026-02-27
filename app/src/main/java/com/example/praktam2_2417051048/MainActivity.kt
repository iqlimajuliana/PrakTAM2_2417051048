package com.example.praktam2_2417051048

import model.foodsource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val mieayam = foodsource.dummyFood[0]

    Column(modifier = Modifier.fillMaxSize().padding(all = 30.dp)) {
        Image(
            painter = painterResource(id = mieayam.imageRes),
            contentDescription = mieayam.nama,
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop


        )
    Text(text = "Nama : ${mieayam.nama}")
    Text(text = "Deskripsi : ${mieayam.deskripsi}")
    Text(text = "Harga : ${mieayam.harga}")
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrakTAM2_2417051048Theme {
        Greeting()
    }
}
