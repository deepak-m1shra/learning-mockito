package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test003CustomValuesTest {

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
                .thenReturn(List.of(new Room("1A", 10)));

        // or can also use this
        //        when(roomService.getAvailableRooms())
        //        .thenReturn(Collections.singletonList(new Room("1A", 20)));

        int expected = 10;

        // when
        int actual = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(expected, actual);

    }

    @Test
    void count_available_places_multiple_rooms() {
        // given
        List<Room> rooms = List.of(new Room("1A", 10), new Room("1B", 20));
        when(roomService.getAvailableRooms())
                .thenReturn(rooms);
        int expected = 30;

        // when
        int actual = bookingService.getAvailablePlaceCount();


        // then
        assertEquals(expected, actual);
    }
}
