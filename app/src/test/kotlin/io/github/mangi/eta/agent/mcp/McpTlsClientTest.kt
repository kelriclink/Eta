package io.github.mangi.eta.agent.mcp

import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class McpTlsClientTest {
    @Test
    fun privateTrustIsRestrictedToHttpsAndPresetPorts() {
        assertTrue(McpTlsClient.isPrivateEndpoint("https://124.70.165.59:8001/app-mcp".toHttpUrl()))
        assertTrue(McpTlsClient.isPrivateEndpoint("https://124.70.165.59:8002/mcp/mcp".toHttpUrl()))
        listOf(
            "http://124.70.165.59:8001/app-mcp",
            "https://124.70.165.59:8003/app-mcp",
            "https://example.com:8001/app-mcp",
            "https://api.deepseek.com",
        ).forEach { assertFalse(McpTlsClient.isPrivateEndpoint(it.toHttpUrl())) }
    }

    @Test
    fun bundledCertificateIsAnUnexpiredCa() {
        val certificate = requireNotNull(javaClass.getResourceAsStream("/mcp/xiaoyue-ip-ca.crt")).use {
            CertificateFactory.getInstance("X.509").generateCertificate(it) as X509Certificate
        }
        assertTrue(certificate.basicConstraints >= 0)
        certificate.checkValidity()
        certificate.verify(certificate.publicKey)
    }
}
