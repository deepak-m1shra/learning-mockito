package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;


/**
 * Invocation syntax for mock, spy
 * mock: when(mock.method()).thenReturn()
 * spy: doReturn().when(spy).method()
 */

public class Test008MockingSpiesTest {

    // declare mock services to be used

    PaymentService paymentService;
    RoomService roomService;
    BookingService bookingService;
    BookingDAO bookingDAO;
    MailSender mailSender;

    @BeforeEach
    void setUp() {
        this.paymentService = mock(PaymentService.class);
        this.roomService = mock(RoomService.class);
        // The following is a spy, uses the logic available, whereas
        // mocks are dummy, that is no logic is used
        this.bookingDAO = spy(BookingDAO.class);
        this.mailSender = mock(MailSender.class);
        bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);
    }

    @Test
    void should_make_booking_when_input_ok() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 6),
                2,
                false);

        // when
        String bookingId = bookingService.makeBooking(bookingRequest);

        // then
        verify(bookingDAO, times(1)).save(bookingRequest);
        System.out.println(bookingId);

    }

    @Test
    void should_cancel_booking_when_input_ok() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 6),
                2,
                false);

        String bookingId = "123";
        bookingRequest.setRoomId("1A");
        doReturn(bookingRequest).when(bookingDAO).get(bookingId);

        // when
        bookingService.cancelBooking(bookingId);

    }
}