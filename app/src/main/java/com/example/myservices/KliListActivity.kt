package com.example.myservices

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.KliListAdapter
import com.example.constants.KeyConstants
import com.example.dao.dataclass.KliMark
import com.example.dao.dataclass.KlientAndMark
import com.example.dao.viewmodel.KliViewModel
import com.example.dao.viewmodel.KliViewModelFactory
import com.example.interfaces.CustomOnClickListenerKlientAndMark
import com.example.myservices.databinding.ActivityKliListBinding
import kotlinx.coroutines.launch

class KliListActivity : AppCompatActivity() {
    lateinit var binding: ActivityKliListBinding
    lateinit var adapter: KliListAdapter
    lateinit var layerManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView



    companion object {
        var currentIdKlient: Int = 0
        var needCheck = false
        var currentUpdate: Int = 0
    }

    private val kliViewModel: KliViewModel by viewModels {
        KliViewModelFactory((application as ServApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKliListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.but_kli_txt)

        recyclerView = binding.rcViewKli
        adapter = KliListAdapter()
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        layerManager = binding.rcViewKli.layoutManager as LinearLayoutManager


        // item menu
        adapter.setOnItemClickListener(object : CustomOnClickListenerKlientAndMark {
            override fun onItemClick(item: View, model: KlientAndMark) {
                handleAdapterItemsClick(item, model)
            }
        })

        //bottom menu
        binding.bootomKliMnu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.but_new_kli -> onClickAdd()
                R.id.but_filter_kli -> {
                    needCheck = !needCheck
                    kliViewModel.refresh()
                 }
                R.id.but_check_clear_kli -> {
                    lifecycleScope.launch {
                        ServApplication.database.markDao().deleteAll()
                    }
                    needCheck=false
                    kliViewModel.refresh()
                }
            }
            true
        }

        observeData()
    }

    private fun observeData() {
        kliViewModel.allKli.observe(this) { klient ->
            klient.let {
                adapter.submitList(it)
            }
            refreshRecycler()
        }
        kliViewModel.refresh()
    }

    fun refreshRecycler() {
         if (currentUpdate != 0 && currentIdKlient!=0) {
            currentUpdate = 0
            lifecycleScope.launch {
                val curById: List<KlientAndMark> =
                      ServApplication.database.kliDao().getByIDKliAndMark(
                          intArrayOf(currentIdKlient)
                       )
                val curKli = curById.firstOrNull()?.let { it }
                var newPosition = adapter.getItemPosition(curKli!!)
                layerManager.scrollToPosition(newPosition)
            }
         }
    }

    private fun handleAdapterItemsClick(item: View, model: KlientAndMark) {
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
            R.id.butChek -> {
                onClickCheck(item, model)
            }
        }
    }



    fun onClickCheck(view: View, model: KlientAndMark) {
        val curMark=KliMark(model.klient.id,1)
        lifecycleScope.launch {
            if(model.klimark?.mark==null)
                ServApplication.database.markDao().insertAll(curMark)
            else ServApplication.database.markDao().delete(curMark)
        }
        kliViewModel.refresh()
        adapter.notifyDataSetChanged()
    }

    fun onClickEdit(view: View, model: KlientAndMark) {
        val intent = Intent(this, KliNewActivity::class.java)
        with(intent) {
            putExtra("oper", KeyConstants.FILE_EDIT)
            putExtra("curId",model.klient.id)
            startActivity(this)
        }
    }

    fun onClickDoc(view: View, model: KlientAndMark) {
        val intent = Intent(this, KliDocActivity::class.java)
        with(intent) {
            putExtra(KeyConstants.FILE_EDIT, 0)
            startActivity(this)
        }
    }

    fun onClickCall(view: View, model: KlientAndMark) {
        try {
            val tel = "tel: " + model.klient.phone
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(tel))
            startActivity(intent)
        } catch (e: Exception) {
            //Toast("e", e.message,Toast.LENGTH_LONG).show()
            //println("Exception")
            //println(e.message)
        }
    }

    fun onClickAdd() {
        val intent = Intent(this, KliNewActivity::class.java)
        with(intent) {
            putExtra("oper",KeyConstants.FILE_ADD)
            startActivity(this)
        }
    }

    fun onClose(view: View) {
        finish()
    }

    override fun onResume() {
        super.onResume()
        if(currentUpdate==1) {
            kliViewModel.refresh()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbarmnu_kli, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()

        }
        return true
    }




}