package com.example.app.ui

import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.net.Uri
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.app.AppSettings
import com.example.app.Intents
import com.example.app.JobStatusInfo
import com.example.app.SettingChangeListener
import com.example.app.ThemeMode
import com.example.app.massstorage.EtchDroidUsbMassStorageDevice.Companion.massStorageDevices
import com.example.app.massstorage.UsbMassStorageDeviceDescriptor
import com.example.app.utils.exception.base.EtchDroidException
import com.example.app.utils.exception.base.RecoverableException
import com.example.app.utils.ktexts.safeParcelableExtra
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


interface IThemeState {
    val dynamicColors: Boolean
    val themeMode: ThemeMode
}

interface IViewModel<T> {
    val state: StateFlow<T>
}

interface ThemeViewModel<T> : IViewModel<T> where T : IThemeState {
    val darkMode: State<Boolean>
        @Composable get() {
            val stateValue by state.collectAsState()
            val systemInDarkMode = isSystemInDarkTheme()
            return remember(stateValue.themeMode) {
                derivedStateOf {
                    when (stateValue.themeMode) {
                        ThemeMode.SYSTEM -> systemInDarkMode
                        ThemeMode.DARK -> true
                        ThemeMode
