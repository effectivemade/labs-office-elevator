package office.effective.features.calendar.converters

import model.RecurrenceDTO
import office.effective.model.RecurrenceModel

object RecurrenceConverter {
    fun modelToDto(model: RecurrenceModel): RecurrenceDTO {
        return RecurrenceDTO(
            model.interval,
            model.freq,
            model.count,
            model.until,
            model.byDay,
            model.byMonth,
            model.byYearDay,
            model.byHour
        )
    }

    fun dtoToModel(dto: RecurrenceDTO): RecurrenceModel {
        return RecurrenceModel(
            dto.interval, dto.freq, dto.count, dto.until, dto.byDay, dto.byMonth, dto.byYearDay, dto.byHour
        )
    }
}