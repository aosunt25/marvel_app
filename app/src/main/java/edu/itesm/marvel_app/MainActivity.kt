package edu.itesm.marvel_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Instant
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    var comicList = ArrayList<Comic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}