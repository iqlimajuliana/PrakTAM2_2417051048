package com.example.praktam2_2417051048

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.praktam2_2417051048.ui.theme.praktam2_2417051048Theme
import com.example.praktam2_2417051048.ui.theme.Red
import com.example.praktam2_2417051048.ui.theme.Green
import com.example.praktam2_2417051048.data.model.classroom
import com.example.praktam2_2417051048.data.api.RetrofitClient
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.example.praktam2_2417051048.data.repository.ClassroomRepository




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
    var classroomList by remember { mutableStateOf<List<classroom>>(emptyList()) }

    NavHost(
        navController = navController as NavHostController,
        startDestination = "home"
    ) {
        composable("home") {
            ClassroomScreen(navController = navController) { fetchedClassrooms ->
                classroomList = fetchedClassrooms
            }
        }

        composable("detail/{nama}") { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama")
            val classroom = classroomList.find { it.namaRuang == nama }
            
            if (classroom != null) {
                DetailScreen(classroom = classroom, navController = navController)
            }
        }
    }
}

@Composable
fun ClassroomScreen(
    navController: NavController,
    onClassroomsLoaded: (List<classroom>) -> Unit = {}
) {
    var classrooms by remember { mutableStateOf<List<classroom>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var retryKey by remember { mutableStateOf(0) }

    val repository = remember { ClassroomRepository() }

    LaunchedEffect(retryKey) {
        isLoading = true
        try {
            val result = repository.getClassrooms()
            classrooms = result
            onClassroomsLoaded(result)
            isError = result.isEmpty()
        } catch (e: Exception) {
            isError = true
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        isError || classrooms.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Gagal memuat data")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { retryKey++ }) {
                        Text("Coba lagi")
                    }
                }
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {

                item {
                    Text(
                        text = "Kelas Kosong",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(classrooms) { classroom ->
                            ClassRowItem(
                                classroom = classroom,
                                navController = navController
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Daftar Kelas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(classrooms) { classroom ->
                    DetailCard(
                        classroom = classroom,
                        navController = navController
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
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
                AsyncImage(
                    model= classroom.imageUrl,
                    contentDescription = classroom.namaRuang,
                    placeholder = painterResource(id = R.drawable.img_3),
                    error = painterResource(id = R.drawable.img_4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp)),
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
    Scaffold(
        bottomBar = {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Kembali")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            DetailCard(classroom = classroom, isFullScreen = true)
        }
    }
}
