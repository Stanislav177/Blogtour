package com.myblogtour.blogtour.ui.maps.observable

import com.myblogtour.blogtour.ui.maps.data.EntityAddress

interface Observer {
    fun update(entityAddress: EntityAddress)
}