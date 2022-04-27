package app.sample.bingewavesampleappkotlin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import app.sample.bingewavesampleappkotlin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = binding.webView

        checkPermissions()

        val map = HashMap<String, String>()
        map["Authorization"] ="Bearer $AUTH_TOKEN"
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                request.grant(request.resources)

            }
        }

        webView.settings.javaScriptEnabled = true




        webView.loadUrl(URL, map)


    }

    private fun checkPermissions(){
        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED || (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_DENIED) ){
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), PERMISSION_REQUEST_CODE)
        }


    }


    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        }else{
            super.onBackPressed()
        }


    }



    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.i(MainActivity::class.java.simpleName,"Permission Granted")
                }else{
                    Toast.makeText(
                        this@MainActivity,
                        "Permission is required to enable feature",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }

        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1001
        const val URL = "[replace_with_url_of_widget]"
        const val AUTH_TOKEN = "[replace_with_users_auth_token]"

    }

}