package com.example.praktam2_2417051048

import model.classroom
import model.classroomsource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051048.ui.theme.PrakTAM2_2417051048Theme
import androidx.compose.material3.Scaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM2_2417051048Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ClassroomScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ClassroomScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(classroomsource.classroom) { classroom ->
            DetailCard(
                classroom = classroom,
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}

@Composable
fun DetailCard(
    classroom: classroom,
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
        Box {
            Image(
                painter = painterResource(classroom.imageRes),
                contentDescription = classroom.namaRuang,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)

            )
            IconButton(
                onClick = { isFavorite = !isFavorite },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite Icon",
                    tint = if (isFavorite) Color.Red else Color.White,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = classroom.namaRuang,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Status: ${classroom.status}", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Kapasitas: ${classroom.kapasitas}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Book Now")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClassroomPreview() {
    PrakTAM2_2417051048Theme {
        ClassroomScreen()
    }
}
