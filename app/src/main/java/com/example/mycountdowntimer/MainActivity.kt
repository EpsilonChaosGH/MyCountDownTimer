package com.example.mycountdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.mycountdowntimer.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var timer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, EventFragment())
                .commit()
        }
//        binding.button.setOnClickListener {
//            if (binding.editTextNumber.text.isNotEmpty()) {
//                startCountDownTimer(toMillis(binding.editTextNumber.text.toString().toInt()))
//            } else {
//                Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

//    private fun startCountDownTimer(timeMillis: Long) {
//        timer?.cancel()
//        timer = object : CountDownTimer(timeMillis, 6000) {
//            override fun onTick(time: Long) {
//                binding.time.text = toMinutes(time)
//            }
//
//            override fun onFinish() {
//                Toast.makeText(this@MainActivity, "FINISH", Toast.LENGTH_SHORT).show()
//            }
//
//        }.start()
//    }
//
//    private fun toMinutes(time: Long): String {
//        return (time / 60_000).toString()
//    }
//
//    private fun toMillis(time: Int): Long {
//        return (time * 60_000).toLong()
//    }
}