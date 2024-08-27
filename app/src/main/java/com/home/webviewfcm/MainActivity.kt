package com.home.webviewfcm

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.home.webviewfcm.ui.theme.WebViewFCMTheme

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebViewFCMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebViewScreen(
                        url = "https://djangofcm.pythonanywhere.com/push/",
                        modifier = Modifier.padding(innerPadding),
                        webViewRef = { webView = it }
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        // Check if the WebView can navigate back
        if (this::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()  // Close the app if there's no history to go back to
        }
    }
}

@Composable
fun WebViewScreen(
    url: String,
    modifier: Modifier = Modifier,
    webViewRef: (WebView) -> Unit
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()  // Ensures web pages open within the WebView
                loadUrl(url)
                webViewRef(this)  // Save WebView reference to be used in onBackPressed
            }
        }
    )
}
