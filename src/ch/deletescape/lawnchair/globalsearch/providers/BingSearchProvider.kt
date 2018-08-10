package ch.deletescape.lawnchair.globalsearch.providers

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.Keep
import ch.deletescape.lawnchair.globalsearch.SearchProvider
import com.android.launcher3.R
import com.android.launcher3.util.PackageManagerHelper

@Keep
class BingSearchProvider(context: Context) : SearchProvider(context) {

    private val PACKAGE = "com.microsoft.bing"
    private val PACKAGE_CORTANA = "com.microsoft.cortana"
    private val PACKAGE_ALEXA = "com.amazon.dee.app"

    private val cortanaInstalled: Boolean
        get() = PackageManagerHelper.isAppEnabled(context.packageManager, PACKAGE_CORTANA, 0)
    private val alexaInstalled: Boolean
        get() = PackageManagerHelper.isAppEnabled(context.packageManager, PACKAGE_ALEXA, 0)

    override val name = context.getString(R.string.search_provider_bing)!!
    override val supportsVoiceSearch: Boolean
        get() = true
    override val supportsAssistant: Boolean
        get() = cortanaInstalled || alexaInstalled

    override val isAvailable: Boolean
        get() = PackageManagerHelper.isAppEnabled(context.packageManager, PACKAGE, 0)

    override fun startSearch(callback: (intent: Intent) -> Unit) = callback(Intent().setClassName(PACKAGE, "com.microsoft.clients.bing.activities.WidgetSearchActivity").setPackage(PACKAGE))
    override fun startVoiceSearch(callback: (intent: Intent) -> Unit) = callback(Intent(Intent.ACTION_SEARCH_LONG_PRESS).setPackage(PACKAGE))
    override fun startAssistant(callback: (intent: Intent) -> Unit) = callback(if(cortanaInstalled) {
        Intent().setClassName(PACKAGE_CORTANA, "com.microsoft.bing.dss.assist.AssistProxyActivity").setPackage(PACKAGE_CORTANA)
    } else {
        Intent(Intent.ACTION_ASSIST).setPackage(PACKAGE_ALEXA)
    })

    override fun getIcon(colored: Boolean): Drawable = context.getDrawable(R.drawable.ic_bing).mutate().apply {
             if(!colored){ setTint(Color.WHITE) }
         }

    override fun getVoiceIcon(colored: Boolean): Drawable = context.getDrawable(R.drawable.ic_mic_color).mutate().apply {
         setTint(if(colored) Color.parseColor("#00897B") else Color.WHITE)
    }

    override fun getAssistantIcon(colored: Boolean): Drawable = context.getDrawable(if (cortanaInstalled) {
        if (colored) {
            R.drawable.ic_cortana
        } else {
            R.drawable.ic_cortana_shadow
        }
    } else {
        if (colored) {
            R.drawable.ic_alexa
        } else {
            R.drawable.ic_alexa_shadow
        }
    })
}