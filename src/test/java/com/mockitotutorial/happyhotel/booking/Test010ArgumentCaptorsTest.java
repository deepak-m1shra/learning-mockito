package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class Test010ArgumentCaptorsTest {


    PaymentService paymentService;
    RoomService roomService;
    BookingService bookingService;
    BookingDAO bookingDAO;
    MailSender mailSender;

    ArgumentCaptor<Double> argumentCaptor;

    @BeforeEach
    void setUp() {
        this.paymentService = mock(PaymentService.class);
        this.roomService = mock(RoomService.class);
        this.bookingDAO = mock(BookingDAO.class);
        this.mailSender = mock(MailSender.class);
        bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);
        argumentCaptor = ArgumentCaptor.forClass(Double.class);
    }

    @Test
    void argument_captor_single() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 6),
                2,
                true);


        Double expected = 500.0;

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentService, times(1)).pay(eq(bookingRequest), argumentCaptor.capture());

        Double argument = argumentCaptor.getValue();

        assertEquals(expected, argument);

    }

    @Test
    void argument_captor_multiple() {
        // given
        BookingRequest bookingRequest5days = new BookingRequest(
                "1",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 6),
                2,
                true);

        BookingRequest bookingRequest1day = new BookingRequest(
                "1",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 2),
                2,
                true);


        List<Double> expected = List.of(500.0, 100.0);

        // when
        bookingService.makeBooking(bookingRequest5days);
        bookingService.makeBooking(bookingRequest1day);

        // then
        verify(paymentService, times(2)).pay(any(),
                argumentCaptor.capture());

        assertEquals(expected, argumentCaptor.getAllValues());


    }
}