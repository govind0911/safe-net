package com.example.domain.model

enum class ConnectionState {
    CONNECTED,
    WARNING,
    DISCONNECTED
}

data class SystemStatus(
    val isGpsActive: Boolean = true,
    val gpsAccuracyMeters: Float = 4.2f,
    val batteryPercentage: Int = 84,
    val networkState: ConnectionState = ConnectionState.CONNECTED,
    val networkName: String = "5G / Emergency Priority",
    val isSilentMode: Boolean = false
)
