package org.example.bank;

import org.example.bank.exception.InvalidAmountException;
import org.example.bank.util.AmountValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AmountValidatorTest {

    @Test
    void requirePositive_nullOrZeroOrNegative_shouldThrow() {
        assertThrows(InvalidAmountException.class,
                () -> AmountValidator.requirePositive(null));
        assertThrows(InvalidAmountException.class,
                () -> AmountValidator.requirePositive(BigDecimal.ZERO));
        assertThrows(InvalidAmountException.class,
                () -> AmountValidator.requirePositive(new BigDecimal("-1.00")));
    }

    @Test
    void requirePositive_positive_shouldNotThrow() {
        assertDoesNotThrow(() ->
                AmountValidator.requirePositive(new BigDecimal("0.01")));
    }
}