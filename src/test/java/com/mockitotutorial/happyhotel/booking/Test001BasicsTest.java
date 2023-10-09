package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class Test001BasicsTest {

    // declare mock services to be used

    PaymentService paymentService;
    RoomService roomService;
    BookingService bookingService;
    BookingDAO bookingDAO;
    MailSender mailSender;
    BookingRequest mockBookingRequest;

    @BeforeEach
    void setUp() {
        this.paymentService = mock(PaymentService.class);
        this.roomService = mock(RoomService.class);
        this.bookingDAO = mock(BookingDAO.class);
        this.mailSender = mock(MailSender.class);
        bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);

        // Nice mock default values
        System.out.println("defaults: Room Service::getAvailableRooms => " + roomService.getAvailableRooms());
        System.out.println("defaults: Room Service::findAvailableRoomId => " + roomService.findAvailableRoomId(mockBookingRequest));
        System.out.println("defaults: Room Service::getRoomCount => " + roomService.getRoomCount());
    }

    @Test
    void should_calculate_correct_price_when_correct_input() {
         // given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 6),
                2,
                false);

        // when
        double expectedAmount = 5 * 2 * 50.0;

        // then
        assertEquals(expectedAmount, bookingService.calculatePrice(bookingRequest));

    }
}
