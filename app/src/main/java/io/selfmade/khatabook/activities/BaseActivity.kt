package io.selfmade.khatabook.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import io.selfmade.khatabook.viewmodel.DataViewModel
import io.selfmade.khatabook.viewmodel.ModelFactory

abstract class BaseActivity() : AppCompatActivity() {

    protected lateinit var activity: Activity
    protected lateinit var dataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = this@BaseActivity
        initModel()
    }

    private fun initModel() {
        dataViewModel =
            ViewModelProvider(this@BaseActivity, ModelFactory()).get(DataViewModel::class.java)
    }

}