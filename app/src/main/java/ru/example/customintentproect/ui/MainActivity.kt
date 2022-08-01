package ru.example.customintentproect.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import ru.example.customintentproect.app
import ru.example.customintentproect.databinding.ActivityMainBinding
import ru.example.customintentproect.intent.CustomIntentServiceImp
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private  lateinit var dogViewModel: DogViewModel

    private val dogRepo by lazy { app.dogRepo }

    private val viewModelDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        binding.mainActivityLoadButton.setOnClickListener() { view ->


            dogViewModel.onLoad()
        }
    }

    private fun initViewModel() {
        dogViewModel = getViewModel()

        viewModelDisposable.addAll(

            dogViewModel.dogLiveData.subscribe{
                if (it.message !=null) {
                    binding.mainActivityImageView.load(it.message)
                    val myCustomIntentServiceImp = Intent(app,CustomIntentServiceImp::class.java)
                    startService(myCustomIntentServiceImp.putExtra("status",it.status))
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