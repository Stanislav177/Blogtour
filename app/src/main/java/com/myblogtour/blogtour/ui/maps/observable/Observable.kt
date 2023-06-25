package com.myblogtour.blogtour.ui.maps.observable

import com.myblogtour.blogtour.ui.maps.data.EntityAddress

class Observable {
    private val observers: MutableList<Observer> = mutableListOf()

    fun add(observer: Observer) {
        observers.add(observer)
    }

    fun remove(observer: Observer) {
        observers.remove(observer)
    }

    fun sendUpdateEvent(entityAddress: EntityAddress) {
        observers.forEach {
            it.update(entityAddress)
        }
    }
}