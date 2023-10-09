package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test005ThrowingExceptions {

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
    void throw_exception_when_room_not_available() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 6),
                2,
                false);



        // findAvailableRoomId is called as the first step in bookingService.makeBooking
        when(roomService.findAvailableRoomId(bookingRequest))
                .thenThrow(BusinessException.class);


        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);


        // then
        assertThrows(BusinessException.class, executable);
    }
}