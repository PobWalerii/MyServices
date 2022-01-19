package com.example.myservices

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.constants.KeyConstants
import com.example.dao.dataclass.KliMark
import com.example.dao.dataclass.Klient
import com.example.myservices.ServApplication.Companion.database
import com.example.myservices.databinding.ActivityKliNewBinding
import kotlinx.coroutines.launch

//https://material.io/components/text-fields/android#filled-text-field

class KliNewActivity : AppCompatActivity() {
    lateinit var binding: ActivityKliNewBinding
    var curId: Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityKliNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val oper = intent.getStringExtra("oper")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(
            if(oper== KeyConstants.FILE_EDIT)
                R.string.txt_edit_kli
            else R.string.txt_new_kli)

        if(oper== KeyConstants.FILE_EDIT) {
            curId=intent.extras!!.getInt("curId")
            if(curId!=0) {
                lifecycleScope.launchWhenResumed {
                    val curById: List<Klient> = database.kliDao().getByID(intArrayOf(curId))
                    val curKli = curById.firstOrNull()?.let{ it }
                    binding.editTextFirst.editText?.setText(curKli?.firstName)
                    binding.editTextLast.editText?.setText(curKli?.lastName)
                    binding.editTextSur.editText?.setText(curKli?.surName)
                    binding.editTextPhone.editText?.setText(curKli?.phone)
                }
            }
        } else binding.butdelete.visibility=GONE
    }

    fun onClickSave(view: View) {
        val first = binding.editTextFirst.editText?.text.toString()
        val last = binding.editTextLast.editText?.text.toString()
        val sur = binding.editTextSur.editText?.text.toString()
        val phone = binding.editTextPhone.editText?.text.toString()
        if (last.isNotEmpty()) {
            val klient = Klient(curId, last, first, sur, phone)
            lifecycleScope.launch {
                val kli_= database.kliDao().insertAll(klient).toInt()
                KliListActivity.currentIdKlient=kli_
            }
            if(KliListActivity.needCheck){
                val mark = KliMark(KliListActivity.currentIdKlient,1)
                lifecycleScope.launch {
                    database.markDao().insertAll(mark)
                }
            }
            KliListActivity.currentUpdate=1
            finish()
        }

    }


    fun onClickDelete(view: View) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.but_delete_txt)
            .setIcon(R.drawable.warning)
            .setMessage(R.string.txt_delete_kli)
            .setPositiveButton(R.string.but_yes_txt) { dialog, _ ->
                deleteKli()
                finish()
            }
            .setNegativeButton(R.string.but_no_txt) { dialog, _ -> }
            .create()
        dialog.show()
    }

    fun deleteKli() {
        lifecycleScope.launchWhenResumed {
            val curById: List<Klient> = database.kliDao().getByID(intArrayOf(curId))
            val curKli: Klient? = curById.firstOrNull()?.let { it }
            database.kliDao().delete(curKli!!)
            val curMark: KliMark? = KliMark(KliListActivity.currentIdKlient,1)
            database.markDao().delete(curMark!!)
        }
        KliListActivity.currentUpdate=1
        KliListActivity.currentIdKlient=0
    }

    fun onClickExit(view: View){
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()

        }
        return true
    }

    }
