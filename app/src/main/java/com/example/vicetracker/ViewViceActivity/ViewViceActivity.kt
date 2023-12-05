package com.example.vicetracker.ViewViceActivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.vicetracker.NewViceActivity.EXTRA_ID
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
        setSupportActionBar(findViewById(R.id.toolbar))

        val toolbar = getSupportActionBar()
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true)
        }

        // Get ID from intent
        val id = intent.getIntExtra(EXTRA_ID,-1)
        viewViceViewModel.updateId(id)
        viewViceViewModel.curVice.observe(this) {
                vice->vice?.let { if (toolbar != null) { toolbar.setTitle(vice.name) } }
        }
    }

    // override action bar back button callback
    override fun onSupportNavigateUp(): Boolean {
        setResult(AppCompatActivity.RESULT_OK)
        finish()
        return true
    }
}