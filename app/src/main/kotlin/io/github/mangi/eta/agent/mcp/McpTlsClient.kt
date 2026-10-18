package io.github.mangi.eta.agent.mcp

import io.github.mangi.eta.agent.model.AgentHttpClient
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import okhttp3.HttpUrl
import okhttp3.OkHttpClient

/** 私有 CA 仅用于预置 IP 服务；其他请求仍使用系统信任。 */
internal object McpTlsClient {
    fun isPrivateEndpoint(url: HttpUrl): Boolean =
        url.isHttps && url.host == "124.70.165.59" && url.port in setOf(8001, 8002, 8003)

    fun forEndpoint(url: HttpUrl): OkHttpClient =
        if (isPrivateEndpoint(url)) privateClient else AgentHttpClient.client

    private val privateClient: OkHttpClient by lazy {
        val certificate = requireNotNull(javaClass.getResourceAsStream("/mcp/xiaoyue-ip-ca.crt")) {
            "内置 MCP CA 证书不存在"
        }.use { CertificateFactory.getInstance("X.509").generateCertificate(it) as X509Certificate }
        require(certificate.basicConstraints >= 0) { "内置 MCP 证书不是 CA" }
        val store = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
            setCertificateEntry("xiaoyue-ip-ca", certificate)
        }
        val trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
            init(store)
        }.trustManagers.filterIsInstance<X509TrustManager>().single()
        val tls = SSLContext.getInstance("TLS").apply {
            init(null, arrayOf(trustManager), null)
        }
        AgentHttpClient.client.newBuilder()
            .sslSocketFactory(tls.socketFactory, trustManager)
            // 不允许私有 CA 客户端跟随重定向至其他主机或端口。
            .followRedirects(false)
            .followSslRedirects(false)
            .build()
    }
}
