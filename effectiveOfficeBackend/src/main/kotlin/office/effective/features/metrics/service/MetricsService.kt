package office.effective.features.metrics.service

import office.effective.common.constants.BookingConstants
import office.effective.features.booking.service.BookingService
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.features.workspace.service.WorkspaceService
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class MetricsService(
    val workspaceService: WorkspaceService,
    val workspaceRepository: WorkspaceRepository,
    val bookingService: BookingService,
    constants: BookingConstants
) {
    private val defaultTimeZone = constants.DEFAULT_TIME_ZONE

    /**
     * regular
     * meeting
     */

    /**
     * Какой процент переговорок(и рабочих мест) был свободен весь указанный промежуток времени.
     * На коротких промежутках времени подходит как датчик занятости офиса в на момент середины отрезка
     * */
    fun calculateOfficeOccupancy(startTime: Instant, endTime: Instant): Map<String, Double> {
        val res: MutableMap<String, Double> = mutableMapOf();

        val freeRegularWorkspaces = workspaceService.findAllFreeByPeriod("regular", startTime, endTime).count()
        val totalRegularWorkspaces = workspaceRepository.findAllByTag("regular").count()
        val partOfFreeRegular: Double = (freeRegularWorkspaces.toDouble()) / (totalRegularWorkspaces.toDouble())
        res["regular"] = partOfFreeRegular
        val freeMeetingWorkspaces = workspaceService.findAllFreeByPeriod("meeting", startTime, endTime).count()
        val totalMeetingWorkspaces = workspaceRepository.findAllByTag("meeting").count()
        val partOfFreeMeeting: Double = (freeMeetingWorkspaces.toDouble()) / (totalMeetingWorkspaces.toDouble())
        res["meeting"] = partOfFreeMeeting
        return res;
    }

    //todo стул-часы, какая доля место-часов занята за промежуток времени. Учитывать, что люди в офисе не могут быть 24 часа

    /**
     * Получить: промежуток внутри дня, когда офис мог быть использован.
     * Получить: анализируемый промежуток времени.
     * Вычислить: количество дней, которые есть в промежутке времени, количество часов суммарное
     * Вычислить: Для каждой переговорки: получить за этот промежуток времени, для каждого из дней, для промежутка рабочего времени: список броней
     * Вычислить: Для каждой переговорки: получить за этот промежуток времени, для каждого из дней, для промежутка рабочего времени: получить суммарное время списка броней, получить время простоя
     * Вычислить: добавить к суммарному времени простоя за промежуток времени
     * */
    fun calcPlaceHours(startDay: Int, endDay: Int, startTime: Instant, endTime: Instant) :MutableMap<String, Double>{
        var res: MutableMap<String, Double> = mutableMapOf();
        val numberOfTheDays = calcNumberOfTheDays(startTime, endTime)
        val meetingWorkspaces = workspaceRepository.findAllByTag("meeting")
        var dtStart: LocalDateTime =
            LocalDateTime.ofEpochSecond(
                startTime.toEpochMilli(),
                0,
                ZoneId.of(defaultTimeZone).rules.getOffset(startTime)
            ).toLocalDate()
                .atStartOfDay()

        for (workspace in meetingWorkspaces) {
            var globalWorkspaceOccupationTime = 0L;
            var globalWorkspaceFreeTime = 0L
            for (i in 0..numberOfTheDays) {
                val dailyGapStart = dtStart.plusDays(i.toLong()).plusHours(endDay.toLong())
                    .toInstant(ZoneId.of(defaultTimeZone).rules.getOffset(startTime))
                val dailyGapEnd = dtStart.plusDays(i.toLong()).plusHours(endDay.toLong())
                    .toInstant(ZoneId.of(defaultTimeZone).rules.getOffset(endTime))

                var occupationTime = 0L;
                val freeTime =
                    dailyGapEnd.toEpochMilli() - dailyGapStart.toEpochMilli(); // Кол-во свободного времени в дне

                globalWorkspaceFreeTime += freeTime;
                bookingService.findAll(
                    bookingRangeFrom = dailyGapStart.toEpochMilli(),
                    bookingRangeTo = dailyGapEnd.toEpochMilli(),
                    workspaceId = workspace.id
                ).map { booking ->
                    occupationTime += getSingleBookingOccupancy(
                        startBooking = booking.beginBooking,
                        endBooking = booking.endBooking,
                        gapStart = dailyGapStart,
                        gapEnd = dailyGapEnd
                    )
                }
                occupationTime = if (occupationTime > freeTime) freeTime else occupationTime
                globalWorkspaceOccupationTime += occupationTime;
            }
            res[workspace.name] = (globalWorkspaceOccupationTime.toDouble()) / (globalWorkspaceFreeTime.toDouble())

        }
        return res
    }

    /**
     * Сколько миллисекунд внутри выбранного промежутка занимает бронь
     * */
    fun getSingleBookingOccupancy(
        startBooking: Instant,
        endBooking: Instant,
        gapStart: Instant,
        gapEnd: Instant
    ): Long {
        var start = startBooking;
        var end = endBooking;
        if (startBooking.isBefore(gapStart)) {
            start = gapStart
        }
        if (endBooking.isAfter(gapEnd)) {
            end = gapEnd
        }
        return end.toEpochMilli() - start.toEpochMilli()
    }

    fun calcNumberOfTheDays(startTime: Instant, endTime: Instant): Int {

        val startdate =
            LocalDateTime.ofEpochSecond(
                startTime.toEpochMilli(),
                0,
                ZoneId.of(defaultTimeZone).rules.getOffset(startTime)
            ).toLocalDate()
        val endDate =
            LocalDateTime.ofEpochSecond(endTime.toEpochMilli(), 0, ZoneId.of(defaultTimeZone).rules.getOffset(endTime))
                .toLocalDate()
        return (ChronoUnit.DAYS.between(startdate, endDate)/1000).toInt()
    }

    fun startOfTheDay(dt: Instant): Instant {
        var date: LocalDateTime =
            LocalDateTime.ofEpochSecond(dt.epochSecond, 0, ZoneId.of(defaultTimeZone).rules.getOffset(dt))
        return date
            .toLocalDate()
            .atStartOfDay()
            .toInstant(ZoneOffset.of(defaultTimeZone))
    }


}