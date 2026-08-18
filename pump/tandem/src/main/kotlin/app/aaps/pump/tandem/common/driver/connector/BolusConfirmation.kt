package app.aaps.pump.tandem.common.driver.connector

import app.aaps.pump.common.defs.BolusData

internal fun isConfirmedBolus(bolusData: BolusData?, expectedBolusId: Int, requestedAmount: Double): Boolean {
    val deliveredAmount = bolusData?.amountImmediateDelivered ?: return false
    // PumpX2 reports milliunits. The 0.001 U tolerance covers conversion truncation.
    return bolusData.bolusId == expectedBolusId.toLong() &&
        bolusData.fullyDelivered &&
        deliveredAmount + 0.001 >= requestedAmount
}
