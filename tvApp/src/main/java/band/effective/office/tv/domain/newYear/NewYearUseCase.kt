package band.effective.office.tv.domain.newYear

import band.effective.office.tv.screen.eventStory.models.NewYearCounter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import java.util.GregorianCalendar

class NewYearUseCase {
    suspend fun getCounter(): Flow<NewYearCounter> = flow {
        while (true) {
            emit(counter())
            delay(1000) // TODO
        }
    }

    fun counter(): NewYearCounter {
        val today = GregorianCalendar()
        val newYear = GregorianCalendar().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.DAY_OF_YEAR, 0)
            set(Calendar.MONTH, 0)
            add(Calendar.YEAR, 1)
        }
        var duration = newYear.time.time - today.time.time
        val mils = (duration % 1000).apply { duration /= 1000 }
        val sec = (duration % 60).apply { duration /= 60 }
        val min = (duration % 60).apply { duration /= 60 }
        val hour = (duration % 24).apply { duration /= 24 }
        val day = duration
        return NewYearCounter(
            day = day.toInt(),
            hour = hour.toInt(),
            min = min.toInt(),
            sec = sec.toInt(),
            mil = mils.toInt()
        )
    }
}