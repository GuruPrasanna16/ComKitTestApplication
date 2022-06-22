package com.example.ivi.example.comkittestapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ivi.example.comkittestapplication.databinding.FragmentFirstBinding
import com.tomtom.comkit.ComKitHostService


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var binder: ComKitHostService.ComKitHostLocalBinder
    lateinit var comKitHostService: ComKitHostService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      _binding = FragmentFirstBinding.inflate(inflater, container, false)
      return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.StartAndBindToComKit.setOnClickListener {
            Log.i("Guru ", "StartAndBindToComKit")
            createComKitService()
            binding.textviewFirst.setText("ComKit Service Started")
        }
        binding.CreateComKitSettings.isClickable = false
        binding.StopComKit.isClickable = false
    }

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.i("Guru", "Service Connection Established")
            binding.CreateComKitSettings.isClickable = true
            binding.StartAndBindToComKit.isClickable = false
            binder = iBinder as ComKitHostService.ComKitHostLocalBinder
            comKitHostService = binder.service

            binding.CreateComKitSettings.setOnClickListener{
                Log.i("Guru", "Enable Create Comkit settings and Stop Comkit button")
                if (configureComKitSettings()) {
                    binding.StopComKit.isClickable = true
                    binding.StopComKit.setOnClickListener {
                        Log.i("Guru", "Stop Button Clicked")
                        stopComKitService()
                    }
                }
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
        }
    }

    private fun createComKitService() {
        val intent = Intent(context, ComKitHostService::class.java)
        val context = context
        if (context != null)  {
            val check = context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
            Log.i("GURU", "BindService Return Value " + check)
        }
    }

    private fun configureComKitSettings(): Boolean {
        var status = comKitHostService.configureComKitSettings("abc", "xyz")
        Log.d("Guru", "Return Value " + status)
        if (status == 0L) {
            Log.d("Guru", "Failed to configure Comkit Settings")
            binding.textviewFirst.setText("Configure Settings Failed")
        } else {
            Log.d("Guru", "configure Comkit Settings successful")
            binding.textviewFirst.setText("Configure Settings SuccessFul")
        }
        return (status != 0L)
    }

    private fun stopComKitService() {
        comKitHostService.onComKitDestory()
        binding.StartAndBindToComKit.isClickable = true
        binding.CreateComKitSettings.isClickable = false
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}