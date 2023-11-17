package com.example.vicetracker.ViewViceActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.vicetracker.AllVicesActivity.ViceListViewModel
import com.example.vicetracker.AllVicesActivity.ViceListViewModelFactory
import com.example.vicetracker.R
import com.example.vicetracker.VicesApplication

class ViewViceActivity : AppCompatActivity() {
    //This is our viewModel instance for the MainActivity class
    private val viewViceViewModel: ViewViceViewModel by viewModels {
        ViewViceViewModelFactory((application as VicesApplication).repository, -1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_vice)
    }
}