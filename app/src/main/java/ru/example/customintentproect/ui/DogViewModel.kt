package ru.example.customintentproect.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.example.customintentproect.domain.DogEntity
import ru.example.customintentproect.domain.DogRepo
import ru.example.customintentproect.intent.CustomThread
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

class DogViewModel(
    private val repo: DogRepo
) {
    val dogLiveData: Observable<DogEntity> = BehaviorSubject.create()
    val errorLiveData: Observable<Throwable> = BehaviorSubject.create()

    fun onLoad() {
        loadData()
    }

    private fun loadData() {

        repo.getDog()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { dog ->
                    dogLiveData.toMutable().onNext(dog)
                },
                onError = { error ->
                    errorLiveData.toMutable().onNext(error)
                }
            )
    }


    private fun <T> LiveData<T>.toMutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw IllegalStateException("not MutableLiveData")
    }

    private fun <T : Any> Observable<T>.toMutable(): Subject<T> {
        return this as? Subject<T>
            ?: throw IllegalStateException("It is not Mutable o_O")
    }

}