package office.effective.booking

import junit.framework.TestCase.assertEquals
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.utils.DatabaseTransactionManager
import office.effective.common.utils.UuidValidator
import office.effective.features.booking.converters.BookingFacadeConverter
import office.effective.features.booking.dto.BookingDTO
import office.effective.features.booking.facade.BookingFacade
import office.effective.features.booking.service.BookingService
import office.effective.features.user.dto.UserDTO
import office.effective.features.workspace.dto.WorkspaceDTO
import office.effective.model.Booking
import office.effective.model.UserModel
import office.effective.model.Workspace
import org.junit.Before
import org.junit.Test
import org.ktorm.database.TransactionIsolation
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.Instant
import java.util.*
import kotlin.test.assertFailsWith

class BookingFacadeTest {
    @Mock
    private lateinit var service: BookingService
    @Mock
    private lateinit var uuidValidator: UuidValidator
    @Mock
    private lateinit var transactionManager: DatabaseTransactionManager
    @Mock
    private lateinit var bookingFacadeConverter: BookingFacadeConverter
    @Mock
    private lateinit var user: UserModel
    @Mock
    private lateinit var workspace: Workspace
    @Mock
    private lateinit var userDto: UserDTO
    @Mock
    private lateinit var workspaceDto: WorkspaceDTO
    @Mock
    private lateinit var bookingMockDto: BookingDTO
    @Mock
    private lateinit var bookingMock: Booking

    private lateinit var facade: BookingFacade

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        facade = BookingFacade(service, transactionManager, uuidValidator, bookingFacadeConverter)
    }

    private fun setUpMockConverter(booking: Booking, bookingDTO: BookingDTO) {
        whenever(bookingFacadeConverter.modelToDto(booking)).thenReturn(bookingDTO)
    }

    private fun setUpMockTransactionManager() {
        whenever(transactionManager.useTransaction<Booking?>(
            anyOrNull(),
            eq(TransactionIsolation.READ_COMMITTED)
        )
        ).thenAnswer { invocation: InvocationOnMock ->
            @Suppress("UNCHECKED_CAST")
            val lambda = invocation.arguments[0] as () -> Booking?
            lambda()
        }
    }

    @Test
    fun testFindById() {
        val bookingId = UUID.fromString("8896abc1-457b-41e4-b80b-2fe7cfb3dbaf")
        val instant = Instant.now()
        val existingBooking = Booking(
            user,
            emptyList(),
            workspace,
            bookingId,
            instant,
            instant)
        val expectedBookingDTO = BookingDTO(
            userDto, emptyList(),
            workspaceDto,
            bookingId.toString(),
            instant.toEpochMilli(),
            instant.toEpochMilli())

        setUpMockTransactionManager()
        whenever(service.findById(anyOrNull())).thenReturn(existingBooking)
        setUpMockConverter(existingBooking, expectedBookingDTO)

        val result = facade.findById(bookingId.toString())

        assertEquals(expectedBookingDTO, result)
    }

    @Test
    fun testFindByIdWithNonExistingBooking() {
        val bookingId = UUID.randomUUID()

        setUpMockTransactionManager()
        whenever(service.findById(anyOrNull())).thenReturn(null)

        assertFailsWith<InstanceNotFoundException> {
            facade.findById(bookingId.toString())
        }
    }

    @Test
    fun testFindAll() {
        val existingList = listOf(bookingMock, bookingMock)
        val expectedList = listOf(bookingMockDto, bookingMockDto)

        setUpMockTransactionManager()
        whenever(service.findAll(anyOrNull(), anyOrNull())).thenReturn(existingList)
        whenever(bookingFacadeConverter.modelToDto(anyOrNull())).thenReturn(expectedList[0], expectedList[1])

        val result = facade.findAll(
            "8896abc1-457b-41e4-b80b-2fe7cfb3dbaf",
            "8896abc2-457b-41e4-b80b-2fe7cfb3dbaf"
        )

        assertEquals(expectedList, result)
    }

    @Test
    fun testSave() {
        setUpMockTransactionManager()
        val resultMockDto = mock<BookingDTO>()
        val resultMock = mock<Booking>()
        whenever(bookingFacadeConverter.dtoToModel(bookingMockDto)).thenReturn(bookingMock)
        whenever(service.save(bookingMock)).thenReturn(resultMock)
        whenever(bookingFacadeConverter.modelToDto(resultMock)).thenReturn(resultMockDto)

        val result = facade.post(bookingMockDto)

        assertEquals(resultMockDto, result)
    }

    @Test
    fun testPut() {
        setUpMockTransactionManager()
        val resultMockDto = mock<BookingDTO>()
        val resultMock = mock<Booking>()
        whenever(bookingFacadeConverter.dtoToModel(bookingMockDto)).thenReturn(bookingMock)
        whenever(service.update(bookingMock)).thenReturn(resultMock)
        whenever(bookingFacadeConverter.modelToDto(resultMock)).thenReturn(resultMockDto)

        val result = facade.put(bookingMockDto)

        assertEquals(resultMockDto, result)
    }
}
