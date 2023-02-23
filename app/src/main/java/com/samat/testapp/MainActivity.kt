package com.samat.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.samat.testapp.ui.main.MainFragment
import com.samat.testapp.ui.main.MainFragment.Companion.KEY_PARENT_ID

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.container, MainFragment::class.java, bundleOf(KEY_PARENT_ID to 0))
            }
        }
    }
}
