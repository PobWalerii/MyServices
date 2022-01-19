package com.example.myservices

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.SpecListAdapter
import com.example.constants.KeyConstants
import com.example.dao.dataclass.KlientAndMark
import com.example.dao.dataclass.Specialist
import com.example.dao.viewmodel.SpecViewModel
import com.example.dao.viewmodel.SpecViewModelFactory
import com.example.interfaces.CustomOnClickListenerKlientAndMark
import com.example.interfaces.CustomOnClickListenerSpecialist
import com.example.myservices.databinding.ActivitySpecListBinding
import kotlinx.coroutines.launch

class SpecListActivity : AppCompatActivity() {
    lateinit var binding: ActivitySpecListBinding
    lateinit var adapter: SpecListAdapter
    lateinit var layerManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView

    companion object {
        var currentIdSpecialist: Int = 0
        var currentUpdate: Int = 0
     }

    private val specViewModel: SpecViewModel by viewModels {
        SpecViewModelFactory((application as ServApplication).repository1)
    }

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       binding = ActivitySpecListBinding.inflate(layoutInflater)
       setContentView(binding.root)

       supportActionBar?.setDisplayHomeAsUpEnabled(true)
       supportActionBar?.setTitle(R.string.but_spec_txt)

       recyclerView = binding.rcViewSpec
       adapter = SpecListAdapter()
       adapter.setHasStableIds(true)
       recyclerView.adapter = adapter
       recyclerView.layoutManager = LinearLayoutManager(this)
       layerManager = binding.rcViewSpec.layoutManager as LinearLayoutManager

       // item menu
       adapter.setOnItemClickListener(object : CustomOnClickListenerSpecialist {
           override fun onItemClick(item: View, model: Specialist) {
               handleAdapterItemsClick(item, model)
           }
       })

       //bottom menu
       binding.bootomSpecMnu.setOnItemSelectedListener {
           when (it.itemId) {
               R.id.but_new_spec -> onClickAdd()
            }
           true
       }
       observeData()
   }

    private fun observeData() {
        specViewModel.allSpec.observe(this) { specialist ->
            specialist.let {
                adapter.submitList(it)
            }
            refreshRecycler()
        }
        specViewModel.refresh()
    }

    fun refreshRecycler() {
        if (currentUpdate != 0 && currentIdSpecialist !=0) {
            currentUpdate = 0
            lifecycleScope.launch {
                val curById: List<Specialist> = ServApplication.database.specDao().getByID(
                    intArrayOf(currentIdSpecialist)
                )
                val curSpec = curById.firstOrNull()?.let { it }
                var newPosition = adapter.getItemPosition(curSpec!!)
                layerManager.scrollToPosition(newPosition)
            }
        }
    }

    private fun handleAdapterItemsClick(item: View, model: Specialist) {
        when(item.id) {
            R.id.butEdit -> {
                onClickEdit(item, model)
            }
            R.id.butDoc -> {
                onClickDoc(item, model)
            }
            R.id.butCall -> {
                onClickCall(item, model)
            }
            R.id.butSchedule -> {
                onClickSchedule(item, model)
            }
        }
    }

    fun onClickAdd() {
        val intent = Intent(this, SpecNewActivity::class.java)
        with(intent) {
            putExtra("oper", KeyConstants.FILE_ADD)
            startActivity(this)
        }
    }

    fun onClickEdit(view: View, model: Specialist) {
        val intent = Intent(this, SpecNewActivity::class.java)
        with(intent) {
            putExtra("oper", KeyConstants.FILE_EDIT)
            putExtra("curId", model.id)
            startActivity(this)
        }
    }

    fun onClickDoc(view: View, model: Specialist) {
        val intent = Intent(this, SpecDocActivity::class.java)
        with(intent) {
            putExtra(KeyConstants.FILE_EDIT, 0)
            startActivity(this)
        }
    }

    fun onClickSchedule(view: View, model: Specialist) {
        val intent = Intent(this, SpecScheduleActivity::class.java)
        with(intent) {
            putExtra(KeyConstants.FILE_EDIT, 0)
            startActivity(this)
        }
    }

    fun onClickCall(view: View, model: Specialist) {
        try {
            val tel = "tel: " + model.phone
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(tel))
            startActivity(intent)
        } catch (e: Exception) {
            //Toast("e", e.message,Toast.LENGTH_LONG).show()
            //println("Exception")
            //println(e.message)
        }
    }


    fun onClose(view: View) {
        finish()
    }

    override fun onResume() {
        super.onResume()
        if(currentUpdate ==1) {
            specViewModel.refresh()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}