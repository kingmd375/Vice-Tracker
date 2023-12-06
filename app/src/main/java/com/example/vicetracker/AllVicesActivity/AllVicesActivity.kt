package com.example.vicetracker.AllVicesActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vicetracker.Model.Vice
import com.example.vicetracker.NewViceActivity.EXTRA_ID
import com.example.vicetracker.NewViceActivity.NewViceActivity
import com.example.vicetracker.NewViceActivity.NewViceViewModel
import com.example.vicetracker.R
import com.example.vicetracker.VicesApplication
import com.example.vicetracker.ViewViceActivity.ViewViceActivity
import com.example.vicetracker.ViewViceActivity.ViewViceViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllVicesActivity : AppCompatActivity() {
    //This is our viewModel instance for the MainActivity class
    private val viceListViewModel: ViceListViewModel by viewModels {
        ViceListViewModelFactory((application as VicesApplication).repository)
    }
    //This is our ActivityResultContracts value that defines
    //the behavior of our application when the activity has finished.
    val startNewViceActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode== Activity.RESULT_OK){
            //Note that all we are doing is logging that we completed
            //This means that the other activity is handling updates to the data
            Log.d("MainActivity","Completed")
        }
    }
    // Start activity for viewing info of a clicked vice
    val startViewViceActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode== Activity.RESULT_OK){
            //Note that all we are doing is logging that we completed
            //This means that the other activity is handling updates to the data
            Log.d("MainActivity","Viewed Vice")
        }
    }

    fun adapting(vice: Vice) {
//        Log.d("MainActivity",name)
        //Then create a new intent with the ID of the vice
        val intent = Intent(this@AllVicesActivity, ViewViceActivity::class.java)
        intent.putExtra(EXTRA_ID,vice.id)
        //And start the activity through the results contract
        startViewViceActivity.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_vices)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
//        val adapter = ViceListAdapter(this::incrementUsage(id))
        val adapter = ViceListAdapter(this::adapting, this::incrementAmount)
            //This is the callback function to be executed
            //when a view in the ViceListAdapter is clicked

            //First we log the vice


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedVices.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        viceListViewModel.allVices.observe( this) { vices ->
            // Update the cached copy of the vices in the adapter.
            vices.let {
                adapter.submitList(it)
            }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@AllVicesActivity, NewViceActivity::class.java)
            startNewViceActivity.launch(intent)
        }
    }
    private fun incrementAmount(id:Int){
        viceListViewModel.increment(id)
    }


//    companion object {
//        fun incrementUsage(id: Int) {
//            viceListViewModel.increment(id)
//            }
//        }
//    }
}