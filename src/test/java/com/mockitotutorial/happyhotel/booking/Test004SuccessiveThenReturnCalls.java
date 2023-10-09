package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test004SuccessiveThenReturnCalls {

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
    void count_available_places_one_room() {
        // given
        when(roomService.getAvailableRooms())
                .thenReturn(List.of(new Room("1A", 10)))
                .thenReturn(Collections.emptyList());


        int expectedFirst = 10;
        int expectedSecond = 0;

        // when
        int actualFirstCall = bookingService.getAvailablePlaceCount();
        int actualSecondCall = bookingService.getAvailablePlaceCount();


        // then
        assertAll(
                () -> assertEquals(expectedFirst, actualFirstCall),
                () -> assertEquals(expectedSecond, actualSecondCall)
        );

    }
}