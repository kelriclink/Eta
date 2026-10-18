package io.github.mangi.eta.agent.mcp

import io.github.mangi.eta.data.model.McpAuthorizationType
import io.github.mangi.eta.data.model.McpServerSetting
import org.junit.Assert.assertThrows
import org.junit.Test

class McpCredentialValidationTest {
    @Test
    fun authenticatedEndpointsRejectMissingCredentialsBeforeSendingRequests() {
        for (type in listOf(McpAuthorizationType.BASIC, McpAuthorizationType.BEARER)) {
            for (token in listOf(null, "", "  ")) {
                assertThrows(IllegalArgumentException::class.java) {
                    McpHttpClient(
                        McpServerSetting("server", "Server", "https://example.com/mcp", authorizationType = type),
                        bearerToken = token,
                    )
                }
            }
        }
    }
}
