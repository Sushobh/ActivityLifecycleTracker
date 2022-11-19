package com.sushobh.activitytracker.lib


import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable


@Serializable
sealed class ActEvent {
    abstract var eventName: String
    abstract var data: String
    abstract var extras: HashMap<String, String>
}

@Serializable
internal class ActLifecycleEvent(
    override var eventName: String,
    override var data: String
) : ActEvent() {
    @EncodeDefault
    override var extras: HashMap<String, String> = hashMapOf()

    @EncodeDefault
    val style: HashMap<String, HashMap<String, String>> = getActivityStyle()
    fun isDestroyEvent() = eventName.equals("onDestroyed")

    fun isCreateEvent() = eventName.equals("onCreated")

}

@Serializable
internal class FragLifecycleEvent(
    override var data: String,
    override var eventName: String,
) : ActEvent() {
    @EncodeDefault
    override var extras: HashMap<String, String> = hashMapOf()

    @EncodeDefault
    var style: HashMap<String, HashMap<String, String>> = getFragmentStyle()
}

private fun getActivityStyle() = hashMapOf(
    "tagStyle" to hashMapOf("backgroundColor" to "seashell"),
    "cardStyle" to hashMapOf("backgroundColor" to "cadetblue")
)

private fun getFragmentStyle() = hashMapOf(
    "tagStyle" to hashMapOf("backgroundColor" to "lavender"),
    "cardStyle" to hashMapOf("backgroundColor" to "cadetblue")
)





