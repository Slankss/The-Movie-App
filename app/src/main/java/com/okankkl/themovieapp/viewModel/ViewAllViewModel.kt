package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.repository.RepositoryImp
import com.okankkl.themovieapp.paging.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAllViewModel
    @Inject
    constructor(
            val repository : RepositoryImp,
            val getMoviesUseCase: GetMoviesUseCase
        ) : ViewModel()
{
    private var _contentList = MutableStateFlow<Resources>(Resources.Loading)
    var contentList = _contentList.asStateFlow()

    private val _displayState : MutableStateFlow<PagingData<Any>> = MutableStateFlow(PagingData.empty())
    val displayState get() = _displayState
    
    fun LoadDisplay(){
        viewModelScope.launch {
            getMoviesUseCase.execute(
                categories = Categories.Popular,
                input = Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _displayState.value = it
                }
        }
    }

    /*
    fun getContent(dataType: String,category : String,page: Int){
            viewModelScope.launch {
                if(dataType == DataType.Movie().name){
                    _contentList.value = when(category){
                        Categories.Popular.title -> repository.getMovieList(Categories.Popular,page)
                        Categories.TopRated.title -> repository.getMovieList(Categories.TopRated,page)
                        Categories.Trending.title -> repository.getMovieList(Categories.Trending,page)
                        Categories.NowPlaying.title -> repository.getMovieList(Categories.NowPlaying,page)
                        else -> Resources.Failed(errorMsg = "The movies could not loaded")
                    }
                }
                else{
                    _contentList.value = when(category){
                        Categories.Popular.title -> repository.getTvSeriesList(Categories.Popular,page)
                        Categories.TopRated.title -> repository.getTvSeriesList(Categories.TopRated,page)
                        Categories.Trending.title -> repository.getTvSeriesList(Categories.Trending,page)
                        Categories.OnTheAir.title -> repository.getTvSeriesList(Categories.OnTheAir,page)
                        else -> Resources.Failed(errorMsg = "The tv series could not loaded")
                    }
                }
            }

        }



     */
}