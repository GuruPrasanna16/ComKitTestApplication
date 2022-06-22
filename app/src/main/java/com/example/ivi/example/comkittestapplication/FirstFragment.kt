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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      _binding = FragmentFirstBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createcomkitbutton.setOnClickListener {
            Log.i("Guru ", "OnCreateButtonClicked")
            createComKitService()
            binding.textviewFirst.setText("Create ComKit button clicked ")
        }

        binding.button2.setOnClickListener {
            Log.i("Guru", "Stop Comkit Service")
            stopComKitService()
            binding.textviewFirst.setText("Stop ComKit button clicked ")
        }
    }

    private fun stopComKitService() {
        TODO("Not yet implemented")
    }

    private fun createComKitService() {
        val intent = Intent(context, ComKitHostService::class.java)
        context?.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.i("Guru", "Service Connection Established")

        }

        override fun onServiceDisconnected(componentName: ComponentName) {
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}