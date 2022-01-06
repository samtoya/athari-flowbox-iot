package com.grundfos.futurelab.athariflowbox.athariiotservice.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeviceEvent {
    POWER("power"),
    INITIALIZATION("initialization"),
    START_FETCH("start_fetch"),
    STOP_FETCH("stop_fetch"),
    LEAKAGE("leakage"),
    TAMPER("tamper"),
    TIMEOUT("timeout"),
    SCANNED("scanned"),
    BATTERY_LOW("battery_low");

    private final String event;
}
