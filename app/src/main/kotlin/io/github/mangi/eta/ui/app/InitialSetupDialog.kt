package io.github.mangi.eta.ui.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import io.github.mangi.eta.EtaApp
import io.github.mangi.eta.data.repository.InitialSetupRepository
import io.github.mangi.eta.data.repository.RuntimeConfigRepository
import io.github.mangi.eta.ui.components.EtaTextButton
import io.github.mangi.eta.ui.components.EtaWindowDialog
import io.github.mangi.eta.ui.components.MiuixDialogActions
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextField

@Composable
internal fun InitialSetupDialog() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var show by remember { mutableStateOf(InitialSetupRepository.shouldShow(context)) }
    var deepSeekKey by remember { mutableStateOf("") }
    var mcpKey by remember { mutableStateOf("") }
    var working by remember { mutableStateOf(false) }
    var notice by remember { mutableStateOf<String?>(null) }

    EtaWindowDialog(
        show = show,
        title = "小月初始化配置",
        summary = "填写 DeepSeek Key 和共享 MCP Key。MCP 使用 Basic 认证，请填写用户名:密码的 Base64，不含 Basic 前缀。可稍后在模型配置和绘图配置中修改。",
        onDismissRequest = { if (!working) show = false },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.heightIn(max = 420.dp).verticalScroll(rememberScrollState()),
        ) {
            TextField(
                value = deepSeekKey,
                onValueChange = { deepSeekKey = it },
                label = "DeepSeek API Key（可选）",
                enabled = !working,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
            )
            TextField(
                value = mcpKey,
                onValueChange = { mcpKey = it },
                label = "共享 MCP Key（Basic Base64，可选）",
                enabled = !working,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
            )
            notice?.let { Text(it) }
            EtaTextButton(
                text = "不再显示",
                enabled = !working,
                onClick = { InitialSetupRepository.dismiss(context); show = false },
            )
            MiuixDialogActions(
                cancelText = "稍后",
                confirmText = if (working) "正在保存并连接…" else "保存配置",
                confirmEnabled = !working && (deepSeekKey.isNotBlank() || mcpKey.isNotBlank()),
                onCancel = { if (!working) show = false },
                onConfirm = {
                    working = true
                    scope.launch {
                        try {
                            val failures = InitialSetupRepository.save(context, deepSeekKey, mcpKey)
                            RuntimeConfigRepository.syncToRemotePreferences(EtaApp.serviceInstance)
                            deepSeekKey = ""
                            mcpKey = ""
                            if (failures.isEmpty()) {
                                InitialSetupRepository.dismiss(context)
                                show = false
                            } else {
                                notice = "密钥已保存，以下服务器连接失败：${failures.joinToString()}。可在绘图配置中刷新工具重试。"
                            }
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (_: Exception) {
                            notice = "配置未全部保存，请重试或在配置页面检查。"
                        } finally {
                            working = false
                        }
                    }
                },
            )
        }
    }
}
