package com.example.firebaseremoteconfig

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val viewPager: ViewPager2 by lazy { findViewById(R.id.viewpager) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initView() {

    }

    private fun initData() {
        //개발용 시간을 단축시킴
        val remoteConfig = Firebase.remoteConfig
//        remoteConfig.setConfigSettingsAsync(
//            remoteConfigSettings {
//                minimumFetchIntervalInSeconds = 0
//            }
//        )

        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val quote = parseQuotesJson(remoteConfig.getString("quote"))
                val isNameRevealed = remoteConfig.getBoolean("is_name_revealed")

                viewPager.adapter =
                    QuotePagerAdapter(quotes = quote, isNameRevealed = isNameRevealed)

            }
        }
    }

    private fun parseQuotesJson(json: String): ArrayList<Quote> {
        val jsonArray = JSONArray(json)
        var jsonList = emptyList<JSONObject>()
        for (index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)
            jsonObject?.let {
                jsonList = jsonList + it
            }
        }
        return jsonList.map {
            Quote(
                quote = it.getString("quote"),
                name = it.getString("name")
            )
        } as ArrayList<Quote>
    }
}