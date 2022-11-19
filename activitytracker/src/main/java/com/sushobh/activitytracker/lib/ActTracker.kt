package com.sushobh.activitytracker.lib

import android.app.Application

object ActTracker {

    private val currentEvents = ArrayDeque<ActEvent>()
    private val allEvents = ArrayDeque<ActEvent>()
    var capacity = 10

    private var application: Application? = null
    private val webAppStarter = WebAppStarter()
    private val interceptors = arrayListOf<ActEventInterceptor>()

    init {
        interceptors.addAll(interceptors())
    }

    fun addInterceptor(interceptor: ActEventInterceptor) {
        interceptors.add(interceptor)
    }

    fun start(application: Application) {
        if (this.application == null) {
            this.application = application
            application.registerActivityLifecycleCallbacks(ActivityLifecycleCallback)
            webAppStarter.startWebApp(application)
        }
    }

    fun addEvent(event: ActEvent) {
        var eventToAdd: ActEvent? = event
        interceptors.forEach {
            eventToAdd = it.intercept(event, allEvents)
            if (eventToAdd == null) {
                return
            }
        }
        eventToAdd?.let {
            if (currentEvents.size == capacity) {
                currentEvents.removeLast()
            }
            currentEvents.addFirst(it)
            allEvents.addLast(it)
        }
    }

    fun getCurrentlyStoredEvents(): List<ActEvent> = arrayListOf<ActEvent>().apply {
        addAll(allEvents)
    }

    fun getEventsForApi(): List<ActEvent> {
        val toReturn = arrayListOf<ActEvent>().apply {
            addAll(currentEvents)
        }
        currentEvents.clear()
        return toReturn
    }

    fun stop() {
        webAppStarter.stop()
        currentEvents.clear()
        allEvents.clear()
    }
}


interface ActEventInterceptor {
    fun intercept(event: ActEvent, allEvents: ArrayDeque<ActEvent>): ActEvent?
}

