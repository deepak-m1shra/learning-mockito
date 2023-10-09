package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test006ArgumentMatchers {

    BookingRequest bookingRequest = new BookingRequest(
            "1",
            LocalDate.of(2023, 9, 1),
            LocalDate.of(2023, 9, 6),
            2,
            true);

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
    void throw_exception_when_room_not_available_any_matcher() {
        // given


        /**
         * 1. We can use any() (for object) (for primitives, we have anyInteger(), anyDouble() and so on
         * 2. Mixing and matching argument matcher throws exception (for e.g: using any() with a concrete value such as "123"
         * 3. If we wish to provide a fixed value we can use "eq" as such: getValue(any(), eq(123))
         * 4. anyString matches everything except null, so use any() in such cases
         */
        when(roomService.findAvailableRoomId(any()))
                .thenThrow(BusinessException.class);


        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);


        // then
        assertThrows(BusinessException.class, executable);
    }

    @Test
    void throw_exception_argument_matcher_primitive() {
        when(paymentService.pay(any(), anyDouble()))
                .thenThrow(BusinessException.class);

        /**
         * The following won't work as we need both arguments of type any* (and not concrete bookingRequest)
         */
        // when(paymentService.pay(any(), bookingRequest))
        //                .thenThrow(BusinessException.class);

        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        assertThrows(BusinessException.class, executable);
    }
}