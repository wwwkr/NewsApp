package com.wwwkr.newsapp.ui

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.compose.currentBackStackEntryAsState
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

    val navController = rememberNavController()
    val viewModel = viewModel<MainViewModel>()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val textToSpeech = TextToSpeech()

    val items = listOf(
        BottomNavItem.News,
        BottomNavItem.Scrap
    )

    Scaffold(
        bottomBar = {
            if (currentDestination?.route !in listOf(Const.ROUTE_WEBVIEW, Const.ROUTE_TTS)) {
                BottomNavigation(items = items, navController = navController)
            }

        }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(
                navController = navController,
                viewModel = viewModel,
                textToSpeech = textToSpeech
            )
        }
    }
}

@Composable
fun NewsScreen(viewModel: MainViewModel, onItemClick: () -> Unit, onTextToSpeechClick: () -> Unit) {

    LaunchedEffect(Unit) {
        viewModel.getNews("kr")
    }

    val datas by viewModel.newsStateFlow.collectAsStateWithLifecycle()

    val itemList by if (datas is UiState.Success) {
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
            NewsItem(
                item = item,
                viewModel = viewModel,
                isScrapView = false,
                onItemClick = {
                    onItemClick()
                },
                onTextToSpeechClick = {
                    onTextToSpeechClick()
                })
        }
    }
}

@Composable
fun ScrapScreen(
    viewModel: MainViewModel,
    onItemClick: () -> Unit,
    onTextToSpeechClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getScrapNews()
    }

    val datas by viewModel.scrapNewsStateFlow.collectAsStateWithLifecycle()

    val itemList = if (datas is UiState.Success) {
        (datas as UiState.Success<List<ArticleModel>>).data
    } else {
        listOf()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        items(itemList) { item ->
            NewsItem(
                item = item,
                viewModel = viewModel,
                isScrapView = true,
                onItemClick = {
                    onItemClick()
                },
                onTextToSpeechClick = {
                    onTextToSpeechClick()
                }
            )
        }

    }

}

@Composable
fun TextToSpeechScreen(
    text: String,
    onLaunched: () -> Unit,
    onBackPressed: () -> Unit
) {

    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(key1 = onBackPressedDispatcher) {
        onBackPressedDispatcher?.addCallback(onBackPressedCallback)
        onDispose {
            onBackPressedCallback.remove()
        }
    }

    LaunchedEffect(Unit) {
        onLaunched()
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // 텍스트 내부에 패딩을 추가하여 읽기 쉽게 합니다.
        text = text,
        style = MaterialTheme.typography.bodyMedium // 텍스트 스타일을 설정할 수 있습니다.
    )

}

@Composable
fun NewsItem(
    item: ArticleModel,
    viewModel: MainViewModel,
    isScrapView: Boolean,
    onItemClick: () -> Unit,
    onTextToSpeechClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            // 세로 높이를 높이기 위해 aspectRatio를 조정합니다. 예를 들어 3f / 2f로 설정하여 더 높은 비율을 제공합니다.
            .aspectRatio(1f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    viewModel.selectItem = item
                    onItemClick()
                },
        ) {
            // 이미지의 비율을 2로 설정하여 전체 공간의 2/3를 차지하게 합니다.

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            )
            {
                GlideImage(
                    imageModel = item.urlToImage ?: "",
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Image(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .clickable {

                                item.isScraped = !item.isScraped

                                viewModel.apply {
                                    when (item.isScraped) {
                                        true -> insertNews(item = item)
                                        false -> deleteNews(item = item, isScrapView = isScrapView)
                                    }
                                }

                            },
                        painter = if (item.isScraped)
                            painterResource(id = R.drawable.ic_full_hart)
                        else
                            painterResource(id = R.drawable.ic_empty_hart),
                        contentDescription = "스크랩"
                    )

                    Image(
                        modifier = Modifier
                            .absolutePadding(top = 10.dp, right = 10.dp, left = 5.dp)
                            .clickable {
                                onTextToSpeechClick()
                            },
                        painter = painterResource(id = R.drawable.ic_sound),
                        contentDescription = "TTS"

                    )

                }

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
fun NavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    textToSpeech: TextToSpeech
) {
    NavHost(navController = navController, startDestination = BottomNavItem.News.screenRoute) {
        composable(BottomNavItem.News.screenRoute) {
            NewsScreen(
                viewModel = viewModel,
                onItemClick = {
                    navController.navigate(Const.ROUTE_WEBVIEW)
                },
                onTextToSpeechClick = {
                    navController.navigate(Const.ROUTE_TTS)
                }
            )
        }
        composable(BottomNavItem.Scrap.screenRoute) {
            ScrapScreen(
                viewModel = viewModel,
                onItemClick = {
                    navController.navigate(Const.ROUTE_WEBVIEW)
                },
                onTextToSpeechClick = {
                    navController.navigate(Const.ROUTE_TTS)
                }
            )
        }
        composable(Const.ROUTE_WEBVIEW) {
            WebViewComponent(url = viewModel.selectItem?.url.toString())
        }

        composable(Const.ROUTE_TTS) {

            val text = viewModel.selectItem?.description ?: ""

            TextToSpeechScreen(
                text = text,
                onLaunched = {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, Bundle(), text)

                },
                onBackPressed = {

                    textToSpeech.stop()
                    navController.popBackStack()

                }
            )
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