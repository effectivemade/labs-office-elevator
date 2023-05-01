package band.effective.office.tv.screen.duolingo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.autoplay.AutoplayableViewModel
import band.effective.office.tv.network.use_cases.DuolingoManager
import band.effective.office.tv.screen.photo.BestPhotoEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuolingoScreenViewModel @Inject constructor(
    private val duolingo: DuolingoManager
): ViewModel(), AutoplayableViewModel {

    private val mutableState = MutableStateFlow(DuolingoUIState())
    override val state = mutableState.asStateFlow()

    private val mutableEffect = MutableSharedFlow<BestPhotoEffect>()
    val effect = mutableEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            mutableState.update {
                it.copy(isLoading = true)
            }
           getUsers()
        }
    }

    private suspend fun getUsers() {
        duolingo.getDuolingoUserinfo().collect{ users ->
            when(users) {
                is Either.Success -> {
                    val usersTop =
                        when(mutableState.value.keySort) {
                            KeySortDuolingoUser.Xp -> {
                                users.data.sortedByDescending {it.totalXp}
                            }
                            KeySortDuolingoUser.Streak -> {
                                users.data.sortedByDescending { it.streakDay }
                            }
                        }
                    mutableState.update {
                        it.copy(users = usersTop, isLoading = false, isData = true)
                    }
                }
                is Either.Failure -> {
                    mutableState.update {
                        it.copy(isError = true, isData = false, isLoading = false, error = users.error)
                    }
                    Log.d("DUOLINGO_ERROR", users.error)
                }
            }
        }
    }
    override fun switchToFirstItem() {
        TODO("Not yet implemented")
    }

    override fun switchToLastItem() {
        TODO("Not yet implemented")
    }
}