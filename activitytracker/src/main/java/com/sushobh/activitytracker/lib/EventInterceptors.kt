package com.sushobh.activitytracker.lib

internal fun interceptors(): ArrayList<ActEventInterceptor> = arrayListOf(
    CountTracker
)


internal object CountTracker : ActEventInterceptor {
    var activityCount = 0
    override fun intercept(event: ActEvent, allEvents: ArrayDeque<ActEvent>): ActEvent {
        if (event is ActLifecycleEvent) {
            if (event.isCreateEvent()) {
                activityCount++
            } else if (event.isDestroyEvent()) {
                activityCount--
                if (activityCount == 0) {
                    ActTracker.stop()
                }
            }
        }
        return event
    }

}