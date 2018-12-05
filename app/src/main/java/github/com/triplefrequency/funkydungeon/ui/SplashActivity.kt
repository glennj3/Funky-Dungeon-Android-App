package github.com.triplefrequency.funkydungeon.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import github.com.triplefrequency.funkydungeon.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch {
            delay(1500)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
        return@runBlocking
    }

}
