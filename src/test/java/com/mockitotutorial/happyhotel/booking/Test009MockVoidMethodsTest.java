package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class Test009MockVoidMethodsTest {

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
    void throw_exception_when_mail_sender_service() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 6),
                2,
                false);

//        when(roomService.findAvailableRoomId(bookingRequest)).thenThrow(BusinessException.class);

        /**
         * The following won't work as it returns void
         * when(mailSender.sendBookingConfirmation("")).thenThrow(BusinessException.class);
         */
        doThrow(new BusinessException()).when(mailSender).sendBookingConfirmation(any());

        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        // then
        assertThrows(BusinessException.class, executable);
    }
}