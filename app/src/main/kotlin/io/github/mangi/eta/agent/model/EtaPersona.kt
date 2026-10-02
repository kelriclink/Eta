package io.github.mangi.eta.agent.model

import android.content.Context

/** 常驻人设正文：从 assets 读取全文，由 Runtime 传入系统消息，不依赖模型主动读取。 */
internal object EtaPersona {
    const val ASSET_PATH = "prompts/poxian/poxian.md"

    fun load(context: Context): String =
        runCatching {
            context.assets.open(ASSET_PATH).bufferedReader().use { it.readText() }
        }.getOrNull().orEmpty().trim()
}
