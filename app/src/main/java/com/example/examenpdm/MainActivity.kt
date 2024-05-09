package com.example.examenpdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examenpdm.ui.theme.ExamenPDMTheme

class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabItems = listOf(
            TabItem(
                title = "Catalogo",
                unselectIcon = Icons.Outlined.Favorite,
                selectIcon = Icons.Filled.Favorite
            ),
            TabItem(
                title = "Favoritos",
                unselectIcon = Icons.Outlined.Search,
                selectIcon = Icons.Filled.Search
            )
        )
        setContent {
            ExamenPDMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectTabIndex by remember {
                        mutableIntStateOf(0)
                    }
                    val pagerState = rememberPagerState {
                        tabItems.size
                    }
                    LaunchedEffect(selectTabIndex){
                        pagerState.animateScrollToPage(selectTabIndex)
                    }
                    LaunchedEffect(pagerState.currentPage){
                        selectTabIndex = pagerState.currentPage
                    }
                    Column (
                        modifier = Modifier.fillMaxSize()
                    ){
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(35.dp)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center,

                        ){
                            Text(
                                text = "SUNFLOWER",
                                fontSize = 40.sp
                            )
                        }
                        TabRow(selectedTabIndex = selectTabIndex) {
                            tabItems.forEachIndexed{ index, item ->
                                Tab(
                                    selected = index == selectTabIndex,
                                    onClick = {
                                        selectTabIndex = index
                                    },
                                    text = {
                                        Text(text = item.title)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectTabIndex){
                                                item.selectIcon
                                            }else item.unselectIcon,
                                            contentDescription = item.title
                                        )
                                    }
                                )
                            }
                        }
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) { index ->
                            when (index) {
                                0 -> MediaList()
                                1 -> GardenList()
                                else -> MediaList()
                            }
                        }
                    }
                }
            }
        }
    }
}

data class TabItem(
    var title: String,
    val unselectIcon: ImageVector,
    val selectIcon : ImageVector
)