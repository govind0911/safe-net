package com.example.domain.model

enum class EmergencyPriority {
    NORMAL,
    WARNING,
    CRITICAL_COUNTDOWN,
    ACTIVE_SOS
}

data class EmergencyState(
    val priority: EmergencyPriority = EmergencyPriority.NORMAL,
    val statusMessage: String = "System Active & Secure",
    val countdownSeconds: Int = 5,
    val activeProtocol: String = "SAFE_NET_V1",
    val locationDescription: String = "Lat: 37.7749° N, Lon: -122.4194° W (Accuracy: 4m)",
    val emergencyContactNotified: Boolean = false
)
