package ru.example.customintentproect.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import ru.example.customintentproect.app
import ru.example.customintentproect.databinding.ActivityMainBinding
import ru.example.customintentproect.intent.CustomIntentServiceImp
import ru.example.customintentproect.intent.CustomThread
import io.reactivex.rxjava3.disposables.CompositeDisposable
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dogViewModel: DogViewModel

    private val dogRepo by lazy { app.dogRepo }

    private val viewModelDisposable = CompositeDisposable()

    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.d("###", " in when")
            when (msg.what) {
                0 -> binding.mainActivityCounterDataTextView.text = msg.arg1.toString()
            }
        }
    }

    private val thread = CustomThread(handler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        thread.start()
        binding.mainActivityLoadButton.setOnClickListener() { view ->

            thread.post(thread.getCounter() + 1)
            dogViewModel.onLoad()
        }
    }
    private fun initViewModel() {

        dogViewModel = getViewModel()

        viewModelDisposable.addAll(

            dogViewModel.dogLiveData.subscribe {
                if (it.message != null) {
                    binding.mainActivityImageView.load(it.message)
                    val myCustomIntentServiceImp = Intent(app, CustomIntentServiceImp::class.java)
                    startService(myCustomIntentServiceImp.putExtra("status", it.status))
                }
            }
        )
    }
    private fun getViewModel(): DogViewModel {
        return lastCustomNonConfigurationInstance as? DogViewModel
            ?: DogViewModel(dogRepo)
    }
    override fun onRetainCustomNonConfigurationInstance(): DogViewModel {
        return dogViewModel
    }
}