package io.github.mangi.eta.data.repository

import android.content.Context
import io.github.mangi.eta.agent.mcp.McpServerManager
import io.github.mangi.eta.data.model.McpAuthorizationType
import io.github.mangi.eta.data.model.ModelReasoningCapabilities
import io.github.mangi.eta.data.model.OpenAiCompatibleProviderSetting
import io.github.mangi.eta.data.model.OpenAiEndpointMode
import io.github.mangi.eta.data.model.ReasoningEffort
import io.github.mangi.eta.data.provider.BuiltinProviders
import io.github.mangi.eta.data.provider.OfficialModelCatalog
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
                    as OpenAiCompatibleProviderSetting
                val defaults = OfficialModelCatalog.modelsForProvider(provider)
                val flash = provider.models.firstOrNull { it.modelId == "deepseek-flash" }
                    ?: defaults.first { it.modelId == "deepseek-flash" }
                val models = (listOf(flash) + provider.models.filterNot { it.id == flash.id })
                    .mapIndexed { index, model ->
                        model.copy(
                            sortOrder = index,
                            isEnabled = if (model.id == flash.id) true else model.isEnabled,
                            contextWindowOverride = 1_048_576,
                            reasoningOverride = true,
                            reasoningCapabilitiesOverride = ModelReasoningCapabilities(
                                defaultEffort = ReasoningEffort.OFF,
                                defaultEnabled = false,
                                canDisable = true,
                                offOnly = true,
                            ),
                        )
                    }
                // 更新供应商不写入模型表，必须单独保存完整模型配置。
                ProviderRepository.replaceModels(provider.id, models)
                ProviderRepository.updateProvider(provider.copy(
                    apiKey = deepSeekKey.trim(),
                    endpointMode = OpenAiEndpointMode.RESPONSES,
                ))
                RuntimeConfigRepository.setSelectedModelId(flash.id)
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
