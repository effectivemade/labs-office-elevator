package office.effective.features.booking.converters

import model.RecurrenceDTO
import office.effective.model.RecurrenceModel

//TODO: all converters should be classes
/**
 * Converts between [RecurrenceDTO] and [RecurrenceModel] objects
 */
object RecurrenceConverter {

    /**
     * Converts [RecurrenceModel] to [RecurrenceDTO]
     *
     * @param model [RecurrenceModel] to be converted
     * @return The resulting [RecurrenceDTO] object
     * @author Max Mishenko
     */
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

    /**
     * Converts [RecurrenceDTO] to [RecurrenceModel]
     *
     * @param dto [RecurrenceDTO] to be converted
     * @return The resulting [RecurrenceModel] object
     * @author Max Mishenko
     */
    fun dtoToModel(dto: RecurrenceDTO): RecurrenceModel {
        return RecurrenceModel(
            dto.interval, dto.freq, dto.count, dto.until, dto.byDay, dto.byMonth, dto.byYearDay, dto.byHour
        )
    }
}