package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Test012MockitoBDDTest {

    @InjectMocks
    BookingService bookingService;

    @Mock
    PaymentService paymentService;

    @Mock
    RoomService roomService;

    @Mock
    BookingDAO bookingDAO;

    @Mock
    MailSender mailSender;

    @Captor
    ArgumentCaptor<Double> argumentCaptor;

    @Test
    void count_available_places_one_room() {
        // given
        given(roomService.getAvailableRooms())
                .willReturn(List.of(new Room("1A", 10)));


        int expected = 10;

        // when
        int actual = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(expected, actual);

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
        then(paymentService).should(times(1)).pay(bookingRequest,500.0);
        then(paymentService).shouldHaveNoMoreInteractions();

    }


}