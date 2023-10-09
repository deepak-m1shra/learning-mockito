package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Test016MockFinalMethods {

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

    // Use mockito-inline for static and final methods (instead of mockito-core)
    @Test
    void count_rooms_final_method() {
        // given
        when(roomService.getAvailableRooms())
                .thenReturn(List.of(new Room("1A", 10)));

        int expected = 10;

        // when
        int actual = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(expected, actual);

    }
}