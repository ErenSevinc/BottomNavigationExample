package com.example.bottomnavigationexample.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bottomnavigationexample.data.model.PostModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

const val PAGINATION_COUNT = 6

class HomeViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean?>(null)
    val isLoading = _isLoading.asStateFlow()

    private val _viewPagingData = MutableStateFlow<MutableList<PostModel>?>(null)
    val viewData: StateFlow<MutableList<PostModel>?> = _viewPagingData

    private var pagingList: MutableList<PostModel> = emptyList<PostModel>().toMutableList()

    private var listSize = 0
    private var pagingListLastSize = PAGINATION_COUNT
    private var paddingLastIndex = PAGINATION_COUNT -1


    init {
        _isLoading.value = null
        _viewPagingData.value = null
    }

    fun updateList() {
        viewModelScope.launch {
            _isLoading.value = true

            /*** first list items add or new list check*/
            if (listSize == 0) {
                listSize = getAllPostDummy().size
                /*** first list items and new list size  < 6 ***/
                if (listSize < pagingListLastSize) {
                    pagingListLastSize = listSize
                }
                pagingList = getAllPostDummy().subList(0, pagingListLastSize)
                _viewPagingData.value = pagingList
            }
            else {
                /*** all list hasn't been added paging list ***/
                if (listSize - pagingListLastSize != 0 && listSize - pagingListLastSize > 0) {
                    val odd = listSize - pagingListLastSize
                    if (odd >= PAGINATION_COUNT) {
                        pagingListLastSize += PAGINATION_COUNT
                        pagingList += getAllPostDummy().subList(paddingLastIndex + 1, pagingListLastSize)
                        paddingLastIndex += PAGINATION_COUNT


                        _viewPagingData.value = pagingList
                    }
                    else {
                        pagingListLastSize += odd
                        pagingList += getAllPostDummy().subList(paddingLastIndex +1, pagingListLastSize)
                        _viewPagingData.value = pagingList
                    }
                }
            }

            delay(1000L)
            _isLoading.value = false
        }
    }

    fun getAllPostDummy(): MutableList<PostModel> =
        mutableListOf(
            PostModel("user1", "user1's post"),
            PostModel("user2", "user2's post"),
            PostModel("user3", "user3's post"),
            PostModel("user4", "user4's post"),
            PostModel("user5", "user5's post"),
            PostModel("user6", "user6's post"),
            PostModel("user7", "user7's post"),
            PostModel("user8", "user8's post"),
            PostModel("user9", "user9's post"),
            PostModel("user10", "user10's post"),
            PostModel("user11", "user11's post"),
            PostModel("user12", "user12's post"),
            PostModel("user13", "user13's post"),
            PostModel("user14", "user14's post"),
            PostModel("user15", "user15's post"),
            PostModel("user16", "user16's post"),
            PostModel("user17", "user17's post"),
            PostModel("user18", "user18's post"),
            PostModel("user19", "user19's post"),
            PostModel("user20", "user20's post"),
            PostModel("user21", "user21's post"),
            PostModel("user22", "user22's post"),
            PostModel("user23", "user23's post"),
            PostModel("user24", "user24's post"),
            PostModel("user25", "user25's post"),
            PostModel("user26", "user26's post"),
            PostModel("user27", "user27's post"),
            PostModel("user28", "user28's post"),
            PostModel("user29", "user29's post"),
            PostModel("user30", "user30's post"),
            PostModel("user31", "user31's post"),
            PostModel("user32", "user32's post"),
            PostModel("user33", "user33's post"),
            PostModel("user34", "user34's post"),
            PostModel("user35", "user35's post"),
            PostModel("user36", "user36's post"),
            PostModel("user37", "user37's post"),
        )
}