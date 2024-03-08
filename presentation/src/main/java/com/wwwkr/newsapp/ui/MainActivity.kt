package com.wwwkr.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.newsapp.MainViewModel
import com.wwwkr.newsapp.R
import com.wwwkr.newsapp.components.UiState
import com.wwwkr.newsapp.model.BottomNavItem
import com.wwwkr.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.News,
        BottomNavItem.Scrap
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF3F414E)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.LightGray,
                    indicatorColor = Color.White
                ),
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun NewsScreen(viewModel: MainViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getNews("kr")
    }

    val datas by viewModel.getNewsStateFlow.collectAsState()

    val itemList by if(datas is UiState.Success) {
        remember {
            mutableStateOf((datas as UiState.Success<List<ArticleModel>>).data)
        }
    } else remember {
        mutableStateOf(listOf())
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        items(itemList) { item ->
            val itemState by  remember {
                mutableStateOf(item)
            }
            NewsItem(item = itemState, viewModel = viewModel, isScrapView = false)
        }


    }
}


@Composable
fun NewsItem(item: ArticleModel, viewModel: MainViewModel, isScrapView: Boolean) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            // 세로 높이를 높이기 위해 aspectRatio를 조정합니다. 예를 들어 3f / 2f로 설정하여 더 높은 비율을 제공합니다.
            .aspectRatio(1f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // 이미지의 비율을 2로 설정하여 전체 공간의 2/3를 차지하게 합니다.

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
            )
            {
                GlideImage(
                    imageModel = item.urlToImage ?: "",
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

                Image(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .clickable {

                            item.isScraped = !item.isScraped
                            
                            viewModel.apply {
                                when (item.isScraped) {
                                    true -> insertNews(item = item)
                                    false -> deleteNews(item = item, isScrapView = isScrapView)
                                }
                            }

                        },
                    painter = if (item.isScraped) painterResource(id = R.drawable.ic_full_hart)
                                else painterResource(id = R.drawable.ic_empty_hart
                    ),
                    contentDescription = "스크랩"
                )
            }

            // 텍스트의 비율을 1로 설정하여 전체 공간의 1/3를 차지하게 합니다.
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp), // 텍스트 내부에 패딩을 추가하여 읽기 쉽게 합니다.
                text = item.description ?: "",
                style = MaterialTheme.typography.bodyMedium // 텍스트 스타일을 설정할 수 있습니다.
            )
        }
    }
}


@Composable
fun ScrapScreen(viewModel: MainViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getScrapNews()
    }

    val datas by viewModel.getScrapNewsStateFlow.collectAsState()

    val itemList by if(datas is UiState.Success) {
        remember {
            mutableStateOf((datas as UiState.Success<List<ArticleModel>>).data)
        }
    } else remember {
        mutableStateOf(listOf())
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        items(itemList) { item ->
            val itemState by  remember {
                mutableStateOf(item)
            }
            NewsItem(item = itemState, viewModel = viewModel, isScrapView = true)
        }


    }

}

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = BottomNavItem.News.screenRoute) {
        composable(BottomNavItem.News.screenRoute) {
            NewsScreen(viewModel = viewModel)
        }
        composable(BottomNavItem.Scrap.screenRoute) {
            ScrapScreen(viewModel = viewModel)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    NewsAppTheme {
//        MainScreen(viewModel = viewModel)
    }
}