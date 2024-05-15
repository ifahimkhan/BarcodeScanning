package com.fahim.barcodescanning

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fahim.barcodescanning.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(application)
        })[MainViewModel::class.java]

        binding.startScanning.setOnClickListener {
            viewModel.startScan()
        }

        viewModel.barcodeResult.observe(this, Observer { result ->
            binding.barcodes.text = binding.barcodes.text.toString() + "\n" + result
        })

        viewModel.scanStatus.observe(this, Observer { status ->
            Toast.makeText(this@MainActivity, status, Toast.LENGTH_SHORT).show()
        })
    }
}