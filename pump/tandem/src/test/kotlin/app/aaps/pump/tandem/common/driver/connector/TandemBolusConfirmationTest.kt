package app.aaps.pump.tandem.common.driver.connector

import app.aaps.pump.common.defs.BolusData
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TandemBolusConfirmationTest {

    @Test
    fun confirmsMatchingFullyDeliveredBolus() {
        val bolus = BolusData(
            amountImmediateRequested = 0.05,
            amountImmediateDelivered = 0.05,
            fullyDelivered = true,
            bolusId = 42
        )

        assertTrue(isConfirmedBolus(bolus, expectedBolusId = 42, requestedAmount = 0.05))
    }

    @Test
    fun rejectsDifferentBolusId() {
        val bolus = BolusData(
            amountImmediateRequested = 0.05,
            amountImmediateDelivered = 0.05,
            fullyDelivered = true,
            bolusId = 41
        )

        assertFalse(isConfirmedBolus(bolus, expectedBolusId = 42, requestedAmount = 0.05))
    }

    @Test
    fun rejectsPartialDelivery() {
        val bolus = BolusData(
            amountImmediateRequested = 0.05,
            amountImmediateDelivered = 0.04,
            fullyDelivered = false,
            bolusId = 42
        )

        assertFalse(isConfirmedBolus(bolus, expectedBolusId = 42, requestedAmount = 0.05))
    }

    @Test
    fun rejectsMissingDeliveredAmount() {
        val bolus = BolusData(
            amountImmediateRequested = 0.05,
            amountImmediateDelivered = null,
            fullyDelivered = true,
            bolusId = 42
        )

        assertFalse(isConfirmedBolus(bolus, expectedBolusId = 42, requestedAmount = 0.05))
    }

    @Test
    fun acceptsSmallConversionDifference() {
        val bolus = BolusData(
            amountImmediateRequested = 0.0523,
            amountImmediateDelivered = 0.052,
            fullyDelivered = true,
            bolusId = 42
        )

        assertTrue(isConfirmedBolus(bolus, expectedBolusId = 42, requestedAmount = 0.0523))
    }
}
