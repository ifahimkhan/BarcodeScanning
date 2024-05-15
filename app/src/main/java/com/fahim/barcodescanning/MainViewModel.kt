package com.fahim.barcodescanning

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _barcodeResult = MutableLiveData<String>()
    val barcodeResult: LiveData<String> get() = _barcodeResult

    private val _scanStatus = MutableLiveData<String>()
    val scanStatus: LiveData<String> get() = _scanStatus

    private val scanner: GmsBarcodeScanner

    init {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .enableAutoZoom()
            .build()
        scanner = GmsBarcodeScanning.getClient(application, options)
    }

    fun startScan() {
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                _barcodeResult.value = barcode.rawValue
            }
            .addOnCanceledListener {
                _scanStatus.value = "Scan canceled"
            }
            .addOnFailureListener { e ->
                Log.e("MainViewModel", "startScan: " + e.message, e)
                _scanStatus.value = e.toString()
            }
    }
}
