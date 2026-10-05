package io.github.mangi.eta.data.repository

import android.content.Context
import io.github.mangi.eta.agent.mcp.McpServerManager
import io.github.mangi.eta.data.model.McpAuthorizationType
import io.github.mangi.eta.data.model.withApiKey
import io.github.mangi.eta.data.provider.BuiltinProviders
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal object InitialSetupRepository {
    private fun preferences(context: Context) =
        context.applicationContext.getSharedPreferences("xiaoyue_setup", Context.MODE_PRIVATE)

    fun shouldShow(context: Context): Boolean = !preferences(context).getBoolean("dismissed", false)

    fun dismiss(context: Context) {
        check(preferences(context).edit().putBoolean("dismissed", true).commit())
    }

    suspend fun save(context: Context, deepSeekKey: String, mcpKey: String): List<String> =
        withContext(Dispatchers.IO) {
            ProviderRepository.ensureBuiltInsMerged()
            McpServerRepository.ensurePresets()
            if (deepSeekKey.isNotBlank()) {
                val provider = requireNotNull(ProviderRepository.providerById(BuiltinProviders.DEEPSEEK_ID))
                ProviderRepository.updateProvider(provider.withApiKey(deepSeekKey.trim()))
                RuntimeConfigRepository.setSelectedProviderId(provider.id)
            }
            val failures = mutableListOf<String>()
            if (mcpKey.isNotBlank()) {
                McpServerRepository.PRESET_SERVERS.forEach { preset ->
                    val current = McpServerRepository.servers().firstOrNull { it.url == preset.url }
                        ?: McpServerRepository.add(preset, "")
                    val configured = current.copy(authorizationType = McpAuthorizationType.BASIC)
                    // 先保存凭据，连接失败时用户仍可在服务器详情中重试。
                    McpServerRepository.update(configured, bearerToken = mcpKey.trim())
                    try {
                        McpServerManager.refresh(configured.id)
                    } catch (cancelled: CancellationException) {
                        throw cancelled
                    } catch (_: Exception) {
                        failures += configured.name
                    }
                }
            }
            failures
        }
}
