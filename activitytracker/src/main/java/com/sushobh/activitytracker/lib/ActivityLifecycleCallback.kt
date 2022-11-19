package com.sushobh.activitytracker.lib

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

internal object ActivityLifecycleCallback : ActivityLifecycleCallbacks {

    val activityEventName = "ActivityEvent"
    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        addFragmentCallback(p0)
        ActTracker.addEvent(
            ActLifecycleEvent(
                data = "${p0.javaClass.simpleName} => onCreate",
                eventName = activityEventName
            )
        )
    }

    override fun onActivityStarted(p0: Activity) {
        ActTracker.addEvent(
            ActLifecycleEvent(
                data = "${p0.javaClass.simpleName} => onStarted",
                eventName = activityEventName
            )
        )
    }

    override fun onActivityResumed(p0: Activity) {
        ActTracker.addEvent(
            ActLifecycleEvent(
                data = "${p0.javaClass.simpleName} => onResumed",
                eventName = activityEventName
            )
        )
    }

    override fun onActivityPaused(p0: Activity) {
        ActTracker.addEvent(
            ActLifecycleEvent(
                data = "${p0.javaClass.simpleName} => onPaused",
                eventName = activityEventName
            )
        )
    }

    override fun onActivityStopped(p0: Activity) {
        ActTracker.addEvent(
            ActLifecycleEvent(
                data = "${p0.javaClass.simpleName} => onStopped",
                eventName = activityEventName
            )
        )
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {
        removeFragmentCallback(p0)
        ActTracker.addEvent(
            ActLifecycleEvent(
                data = "${p0.javaClass.simpleName} => onDestroyed",
                eventName = activityEventName
            )
        )
    }

    private fun addFragmentCallback(p0: Activity) {
        if (p0 is AppCompatActivity) {
            p0.supportFragmentManager.registerFragmentLifecycleCallbacks(
                FragmentLifeCycleCallback,
                true
            )
        }
    }

    private fun removeFragmentCallback(p0: Activity) {
        if (p0 is AppCompatActivity) {
            p0.supportFragmentManager.unregisterFragmentLifecycleCallbacks(FragmentLifeCycleCallback)
        }
    }
}


object FragmentLifeCycleCallback : FragmentManager.FragmentLifecycleCallbacks() {
    val fragmentEventName = "FragmentEvent"
    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        ActTracker.addEvent(
            FragLifecycleEvent(
                data = "${f.javaClass.simpleName} => onAttached",
                eventName = fragmentEventName
            )
        )
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        ActTracker.addEvent(
            FragLifecycleEvent(
                data = "${f.javaClass.simpleName} => onCreated",
                eventName = fragmentEventName
            )
        )
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        ActTracker.addEvent(
            FragLifecycleEvent(
                data = "${f.javaClass.simpleName} => onResumed",
                eventName = fragmentEventName
            )
        )
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        ActTracker.addEvent(
            FragLifecycleEvent(
                data = "${f.javaClass.simpleName} => onPaused",
                eventName = fragmentEventName
            )
        )
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
        ActTracker.addEvent(
            FragLifecycleEvent(
                data = "${f.javaClass.simpleName} => onStopped",
                eventName = fragmentEventName
            )
        )
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        ActTracker.addEvent(
            FragLifecycleEvent(
                data = "${f.javaClass.simpleName} => onDestroyed",
                eventName = fragmentEventName
            )
        )
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        ActTracker.addEvent(
            FragLifecycleEvent(
                data = "${f.javaClass.simpleName} => onDetached",
                eventName = fragmentEventName
            )
        )
    }
}