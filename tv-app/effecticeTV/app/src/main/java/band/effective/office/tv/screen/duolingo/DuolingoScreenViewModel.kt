package band.effective.office.tv.screen.duolingo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.network.use_cases.DuolingoManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuolingoScreenViewModel @Inject constructor(
    private val duolingo: DuolingoManager
): ViewModel() {
    init {
        viewModelScope.launch {
            duolingo.getDuolingoUserinfo().collect{
                when(it) {
                    is Either.Success -> {
                        Log.d("DUOLINGO", "${it.data}")
                    }
                    is Either.Failure -> {
                        Log.d("DUOLINGO_ERROR", it.error)
                    }
                }
            }
        }
    }
}