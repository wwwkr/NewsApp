package com.wwwkr.newsapp.model

import com.wwwkr.newsapp.R
import com.wwwkr.domain.common.Const

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
) {
    object News : BottomNavItem(R.string.txt_news, R.drawable.ic_news, Const.NEWS)
    object Scrap : BottomNavItem(R.string.txt_scrap, R.drawable.ic_scrap, Const.SCRAP)

}
