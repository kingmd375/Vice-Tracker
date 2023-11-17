package com.example.vicetracker.NewEditViceActivity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.vicetracker.Model.Vice
import com.example.vicetracker.R
import com.example.vicetracker.VicesApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

const val EXTRA_ID:String = "com.example.vicetracker.NewViceActivity.EXTRA_ID"
class NewViceActivity : AppCompatActivity() {
    private lateinit var editViceView: EditText

    private val newViceViewModel: NewViceViewModel by viewModels {
        NewViceViewModelFactory((application as VicesApplication).repository,-1)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_vice)
        editViceView = findViewById(R.id.edit_vice)

        val id = intent.getIntExtra(EXTRA_ID,-1)
        if(id != -1){
            newViceViewModel.updateId(id)
        }
        newViceViewModel.curVice.observe(this){
                vice->vice?.let { editViceView.setText(vice.name)}
        }

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            CoroutineScope(SupervisorJob()).launch {
                if(id==-1) {
                    newViceViewModel.insert(
                        Vice(null, editViceView.text.toString(),0, 0)
                    )
                }else{
                    val updatedVice = newViceViewModel.curVice.value
                    if (updatedVice != null) {
                        updatedVice.name = editViceView.text.toString()
                        newViceViewModel.update(updatedVice)
                    }
                }
            }

            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }
    }
}