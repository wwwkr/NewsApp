package com.wwwkr.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.glide.GlideImage
import com.wwwkr.domain.model.UiState
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.newsapp.MainViewModel
import com.wwwkr.newsapp.R
import com.wwwkr.newsapp.common.Const
import com.wwwkr.newsapp.model.BottomNavItem
import com.wwwkr.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val viewModel = viewModel<MainViewModel>()

    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem.News,
        BottomNavItem.Scrap
    )

    Scaffold(
        bottomBar = { BottomNavigation(items = items, navController = navController) }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun NewsScreen(viewModel: MainViewModel, onItemClickListener: () -> Unit) {

    LaunchedEffect(Unit) {
        viewModel.getNews("kr")
    }

    val datas by viewModel.newsStateFlow.collectAsStateWithLifecycle()

    val itemList = if (datas is UiState.Success) {
        (datas as UiState.Success<List<ArticleModel>>).data
    } else  {
        listOf()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        items(itemList) { item ->
            NewsItem(item = item, viewModel = viewModel) {
                onItemClickListener()
            }
        }
    }
}

@Composable
fun ScrapScreen(viewModel: MainViewModel, onItemClickListener: () -> Unit) {

    LaunchedEffect(Unit) {
        viewModel.getScrapNews()
    }

    val datas by viewModel.scrapNewsStateFlow.collectAsStateWithLifecycle()

    val itemList = if (datas is UiState.Success) {
        (datas as UiState.Success<List<ArticleModel>>).data
    } else  {
        listOf()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        items(itemList) { item ->
            NewsItem(item = item, viewModel = viewModel) {
                onItemClickListener()
            }
        }

    }

}


@Composable
fun NewsItem(item: ArticleModel, viewModel: MainViewModel, onItemClickListener : () -> Unit) {

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
                    .weight(2f)
                    .clickable {
                        viewModel.imageUrl = item.url ?: ""
                        onItemClickListener()
                    },

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
                                    false -> deleteNews(item = item)
                                }
                            }

                        },
                    painter = if (item.isScraped)
                        painterResource(id = R.drawable.ic_full_hart)
                    else
                        painterResource(id = R.drawable.ic_empty_hart),
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
fun NavigationGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = BottomNavItem.News.screenRoute) {
        composable(BottomNavItem.News.screenRoute) {
            NewsScreen(viewModel = viewModel) {
                navController.navigate(Const.ROUTE_WEBVIEW)
            }
        }
        composable(BottomNavItem.Scrap.screenRoute) {
            ScrapScreen(viewModel = viewModel) {
                navController.navigate(Const.ROUTE_WEBVIEW)
            }
        }
        composable(Const.ROUTE_WEBVIEW) {
            WebViewComponent(url = viewModel.imageUrl)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    NewsAppTheme {
        MainScreen()
    }
}