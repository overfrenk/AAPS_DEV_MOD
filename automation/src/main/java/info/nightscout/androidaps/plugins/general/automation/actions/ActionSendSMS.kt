package info.nightscout.androidaps.plugins.general.automation.actions

import android.widget.LinearLayout
import dagger.android.HasAndroidInjector
import info.nightscout.androidaps.automation.R
import info.nightscout.androidaps.data.PumpEnactResult
import info.nightscout.androidaps.interfaces.SmsCommunicator
import info.nightscout.androidaps.plugins.general.automation.elements.InputString
import info.nightscout.androidaps.plugins.general.automation.elements.LabelWithElement
import info.nightscout.androidaps.plugins.general.automation.elements.LayoutBuilder
import info.nightscout.androidaps.queue.Callback
import info.nightscout.androidaps.utils.JsonHelper
import org.json.JSONObject
import javax.inject.Inject

class ActionSendSMS(injector: HasAndroidInjector) : Action(injector) {

    @Inject lateinit var smsCommunicatorPlugin: SmsCommunicator

    var text = InputString()

    override fun friendlyName(): Int = R.string.sendsmsactiondescription
    override fun shortDescription(): String = rh.gs(R.string.sendsmsactionlabel, text.value)
    override fun icon(): Int = R.drawable.ic_notifications

    override fun doAction(callback: Callback) {
        val result = smsCommunicatorPlugin.sendNotificationToAllNumbers(text.value)
        callback.result(PumpEnactResult(injector).success(result).comment(if (result) R.string.ok else R.string.error))?.run()
    }

    override fun isValid(): Boolean = text.value.isNotEmpty()

    override fun toJSON(): String {
        val data = JSONObject().put("text", text.value)
        return JSONObject()
            .put("type", this.javaClass.name)
            .put("data", data)
            .toString()
    }

    override fun fromJSON(data: String): Action {
        val o = JSONObject(data)
        text.value = JsonHelper.safeGetString(o, "text", "")
        return this
    }

    override fun hasDialog(): Boolean = true

    override fun generateDialog(root: LinearLayout) {
        LayoutBuilder()
            .add(LabelWithElement(rh, rh.gs(R.string.sendsmsactiontext), "", text))
            .build(root)
    }
}