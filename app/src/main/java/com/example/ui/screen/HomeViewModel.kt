package com.example.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ConnectionState
import com.example.domain.model.EmergencyPriority
import com.example.domain.model.EmergencyState
import com.example.domain.model.SystemStatus
import com.example.ui.components.TacticalNavDestination
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _emergencyState = MutableStateFlow(EmergencyState())
    val emergencyState: StateFlow<EmergencyState> = _emergencyState.asStateFlow()

    private val _systemStatus = MutableStateFlow(SystemStatus())
    val systemStatus: StateFlow<SystemStatus> = _systemStatus.asStateFlow()

    private val _currentNavDestination = MutableStateFlow(TacticalNavDestination.HOME)
    val currentNavDestination: StateFlow<TacticalNavDestination> = _currentNavDestination.asStateFlow()

    private var countdownJob: Job? = null

    fun triggerSos() {
        if (_emergencyState.value.priority == EmergencyPriority.ACTIVE_SOS) return

        _emergencyState.update {
            it.copy(
                priority = EmergencyPriority.CRITICAL_COUNTDOWN,
                statusMessage = "CRITICAL SOS INITIATED",
                countdownSeconds = 5
            )
        }
        _systemStatus.update {
            it.copy(networkState = ConnectionState.WARNING)
        }

        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            for (sec in 4 downTo 0) {
                delay(1000)
                _emergencyState.update { it.copy(countdownSeconds = sec) }
            }
            // Transition to ACTIVE SOS
            _emergencyState.update {
                it.copy(
                    priority = EmergencyPriority.ACTIVE_SOS,
                    statusMessage = "BROADCASTING EMERGENCY TELEMETRY",
                    emergencyContactNotified = true
                )
            }
            _systemStatus.update {
                it.copy(networkState = ConnectionState.DISCONNECTED)
            }
        }
    }

    fun cancelSos() {
        countdownJob?.cancel()
        _emergencyState.update {
            EmergencyState(
                priority = EmergencyPriority.NORMAL,
                statusMessage = "System Disarmed & Secure",
                emergencyContactNotified = false
            )
        }
        _systemStatus.update {
            it.copy(networkState = ConnectionState.CONNECTED)
        }
    }

    fun triggerCrisisLine() {
        _emergencyState.update {
            it.copy(
                priority = EmergencyPriority.WARNING,
                statusMessage = "Connecting Crisis Dispatch..."
            )
        }
        _systemStatus.update {
            it.copy(networkState = ConnectionState.WARNING)
        }
    }

    fun triggerSilentAlert() {
        _emergencyState.update {
            it.copy(
                priority = EmergencyPriority.WARNING,
                statusMessage = "Silent Protocol Engaged. Tracking..."
            )
        }
        _systemStatus.update {
            it.copy(isSilentMode = true, networkState = ConnectionState.WARNING)
        }
    }

    fun selectNavDestination(destination: TacticalNavDestination) {
        _currentNavDestination.value = destination
    }
}
