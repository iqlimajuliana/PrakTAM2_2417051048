package com.example.praktam2_2417051048

import model.classroom
import model.classroomsource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051048.ui.theme.praktam2_2417051048Theme
import com.example.praktam2_2417051048.ui.theme.Red
import com.example.praktam2_2417051048.ui.theme.Green
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            praktam2_2417051048Theme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = "classroom"
    ) {
        composable("classroom") {
            ClassroomScreen(navController = navController)
        }

        composable("detail/{namaRuang}") { backStackEntry ->
            val namaRuang = backStackEntry.arguments?.getString("namaRuang")
            val classroom = classroomsource.classroom.find { it.namaRuang == namaRuang }
            
            if (classroom != null) {
                DetailScreen(classroom = classroom, navController = navController)
            }
        }
    }
}

@Composable
fun ClassroomScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        ) {
            item {
                Text(
                    text = "Kelas Kosong",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(classroomsource.classroom) { classroom ->
                        ClassRowItem(classroom = classroom, navController = navController)
                    }
                }

                Spacer(modifier = Modifier.height(45.dp))

                Text(
                    text = "Daftar Kelas",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(classroomsource.classroom) { classroom ->
                DetailCard(classroom = classroom, navController = navController)
            }
        }
    }
}

@Composable
fun DetailCard(
    classroom: classroom,
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    isFullScreen: Boolean = false
) {
    var isFavorite by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(classroom.imageRes),
                    contentDescription = classroom.namaRuang,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isFullScreen) 300.dp else 200.dp),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) Color.Red else Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = classroom.namaRuang,
                    style = if (isFullScreen) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Status: ${classroom.status}",
                    color = if (classroom.status == "Penuh") Red else Green,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Kapasitas: ${classroom.kapasitas}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                if (!isFullScreen) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            navController?.navigate("detail/${classroom.namaRuang}")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Book Now")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(classroom: classroom, navController: NavController) {
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            isLoading = true
                            delay(2000)
                            snackbarHostState.showSnackbar("Booking ${classroom.namaRuang} berhasil!")
                            isLoading = false
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Memproses...")
                    } else {
                        Text("Konfirmasi Booking")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Kembali")
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            DetailCard(classroom = classroom, isFullScreen = true)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClassroomPreview() {
    val navController = rememberNavController()
    praktam2_2417051048Theme {
        ClassroomScreen(navController = navController)
    }
}
