package com.example.myservices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.constants.KeyConstants
import com.example.dao.dataclass.KliMark
import com.example.dao.dataclass.Klient
import com.example.dao.dataclass.Specialist
import com.example.myservices.databinding.ActivityKliNewBinding
import com.example.myservices.databinding.ActivitySpecNewBinding
import kotlinx.coroutines.launch

class SpecNewActivity : AppCompatActivity() {
    lateinit var binding: ActivitySpecNewBinding
    var curId: Int =0
    var curacc: Int =0
    var curpas: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySpecNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val oper = intent.getStringExtra("oper")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(
            if(oper== KeyConstants.FILE_EDIT)
                R.string.txt_edit_spec
            else R.string.txt_new_spec)

        if(oper== KeyConstants.FILE_EDIT) {
            curId=intent.extras!!.getInt("curId")
            if(curId!=0) {
                lifecycleScope.launchWhenResumed {
                    val curById: List<Specialist> = ServApplication.database.specDao().getByID(intArrayOf(curId))
                    val curSpec = curById.firstOrNull()?.let{ it }
                    binding.editTextSpeciality.editText?.setText(curSpec?.speciality)
                    binding.editTextFirst.editText?.setText(curSpec?.firstName)
                    binding.editTextLast.editText?.setText(curSpec?.lastName)
                    binding.editTextSur.editText?.setText(curSpec?.surName)
                    binding.editTextPhone.editText?.setText(curSpec?.phone)
                    curacc=curSpec?.access!!.toInt()
                    curpas=curSpec?.password
                }
            }
        } else binding.butdelete.visibility= View.GONE

    }

    fun onClickSave(view: View) {
        val special = binding.editTextSpeciality.editText?.text.toString()
        val first = binding.editTextFirst.editText?.text.toString()
        val last = binding.editTextLast.editText?.text.toString()
        val sur = binding.editTextSur.editText?.text.toString()
        val phone = binding.editTextPhone.editText?.text.toString()
        if (last.isNotEmpty()&&special.isNotEmpty()) {
            val specialist = Specialist(curId, last, first, sur, special, phone, curacc, curpas)
            lifecycleScope.launch {
                 SpecListActivity.currentIdSpecialist=ServApplication.database.specDao().insertAll(specialist).toInt()
            }
            SpecListActivity.currentUpdate=1
            finish()
        }

    }

    fun onClickDelete(view: View) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.but_delete_txt)
            .setIcon(R.drawable.warning)
            .setMessage(R.string.txt_delete_spec)
            .setPositiveButton(R.string.but_yes_txt) { dialog, _ ->
                deleteSpec()
                finish()
            }
            .setNegativeButton(R.string.but_no_txt) { dialog, _ -> }
            .create()
        dialog.show()
    }

    fun deleteSpec() {
        lifecycleScope.launchWhenResumed {
            val curById: List<Specialist> = ServApplication.database.specDao().getByID(intArrayOf(curId))
            val curSpec: Specialist? = curById.firstOrNull()?.let { it }
            ServApplication.database.specDao().delete(curSpec!!)
         }
        SpecListActivity.currentUpdate=1
        SpecListActivity.currentIdSpecialist=0
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