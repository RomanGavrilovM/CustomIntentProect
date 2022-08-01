package ru.example.customintentproect.domain

import io.reactivex.rxjava3.core.Single

interface DogRepo {
    fun getDog(): Single<DogEntity>
}