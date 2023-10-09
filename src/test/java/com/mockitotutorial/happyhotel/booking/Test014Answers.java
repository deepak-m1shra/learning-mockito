package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class Test014Answers {

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
    void mock_static_method() {
        try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {
            // given
            BookingRequest bookingRequest = new BookingRequest(
                    "1213",
                    LocalDate.of(2023, 9, 9),
                    LocalDate.of(2023, 9, 10),
                    2,
                    false);

            double expected = 100 * 0.8;
            mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble()))
                    .thenAnswer(inv -> (double)inv.getArgument(0) * 0.8);

            double actual = bookingService.calculatePriceEuro(bookingRequest);

            assertEquals(expected, actual);

        }
    }
}