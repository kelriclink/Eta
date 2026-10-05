package io.github.mangi.eta.data.repository

import io.github.mangi.eta.data.model.McpAuthorizationType
import io.github.mangi.eta.data.model.OpenAiCompatibleProviderSetting
import io.github.mangi.eta.data.model.OpenAiEndpointMode
import io.github.mangi.eta.data.provider.BuiltinProviders
import io.github.mangi.eta.data.provider.OfficialModelCatalog
import io.github.mangi.eta.data.provider.ReasoningCapabilityResolver
import io.github.mangi.eta.data.model.ProviderSourceTypes
import io.github.mangi.eta.data.model.ReasoningEffort
import io.github.mangi.eta.data.model.ModelReasoningCapabilities
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class InitialSetupDefaultsTest {
    @Test
    fun offOnlyModelConfigurationIsResolvedWithoutProviderSpecialCases() {
        val provider = BuiltinProviders.PROVIDERS.single()
        val flash = OfficialModelCatalog.modelsForProvider(provider).first { it.modelId == "deepseek-flash" }
        val configured = flash.copy(reasoningCapabilitiesOverride = ModelReasoningCapabilities(
            defaultEffort = ReasoningEffort.OFF, defaultEnabled = false, canDisable = true, offOnly = true,
        ))
        val capabilities = requireNotNull(ReasoningCapabilityResolver.resolve(ProviderSourceTypes.DEEPSEEK, configured))
        assertEquals(listOf(ReasoningEffort.OFF), capabilities.selectableEfforts)
        assertEquals(ReasoningEffort.OFF, capabilities.normalize(ReasoningEffort.HIGH))
        assertEquals(ReasoningEffort.OFF, capabilities.normalize(ReasoningEffort.DEFAULT))
    }

    @Test
    fun onlyDeepSeekIsPresetAndUsesResponses() {
        val provider = BuiltinProviders.PROVIDERS.single() as OpenAiCompatibleProviderSetting
        assertEquals(BuiltinProviders.DEEPSEEK_ID, provider.id)
        assertEquals(OpenAiEndpointMode.RESPONSES, provider.endpointMode)
        assertTrue(provider.apiKey.isBlank())
        assertTrue(OfficialModelCatalog.modelsForProvider(provider).all { it.contextWindow == 1_048_576 })
    }

    @Test
    fun drawingPresetsHaveStableAddressesWithoutCachedTools() {
        val presets = McpServerRepository.PRESET_SERVERS
        assertEquals(listOf(
            "https://comfyui-mcp.tangxi.org/app-mcp",
            "https://danbooru-search.tangxi.org/mcp/mcp",
        ), presets.map { it.url })
        assertTrue(presets.all { it.authorizationType == McpAuthorizationType.BASIC && it.tools.isEmpty() })
    }
}
