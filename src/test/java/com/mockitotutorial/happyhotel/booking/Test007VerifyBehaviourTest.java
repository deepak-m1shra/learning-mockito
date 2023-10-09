package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class Test007VerifyBehaviourTest {

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
        this.bookingDAO = mock(BookingDAO.class);
        this.mailSender = mock(MailSender.class);
        bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);
    }

    @Test
    void should_not_invoke_payment_when_not_prepaid() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 6),
                2,
                true);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentService, times(1)).pay(bookingRequest,500.0);
        verifyNoMoreInteractions(paymentService);

    }

    @Test
    void should_not_invoke_payment_when_not_paid() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 6),
                2,
                false);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentService, never()).pay(any(), anyDouble());
        verifyNoInteractions(paymentService);
//        verifyNoInteractions(mailSender);
    }
}