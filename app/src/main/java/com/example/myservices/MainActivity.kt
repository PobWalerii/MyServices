package com.example.myservices

import android.content.Intent
import android.os.Bundle
import android.view.Gravity.END
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myservices.databinding.ActivityMainBinding
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import kotlinx.coroutines.delay


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var animAlpha: Animation
    lateinit var animRotate: Animation
    lateinit var animScale: Animation
    lateinit var animTranslate: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.title="  "

        animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha)
        animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate)
        animScale = AnimationUtils.loadAnimation(this, R.anim.scale)
        //val animTranslate = AnimationUtils.loadAnimation(this, R.anim.translate)

        binding.butKli.startAnimation(animRotate)
        binding.butSpec.startAnimation(animAlpha)
        binding.butServList.startAnimation(animAlpha)
        binding.butFree.startAnimation(animAlpha)
        binding.butWaiting.startAnimation(animRotate)

        binding.nvSettings.setNavigationItemSelectedListener {
            binding.drawer.closeDrawer(GravityCompat.END)
            when(it.itemId){
                R.id.but_settings_shedule -> {
                    val intent= Intent(this,KliListActivity::class.java)
                    startActivity(intent)
                }
                R.id.but_rouding_seting -> {
                    val intent= Intent(this,SpecListActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }



    }
//https://developer.android.com/training/data-storage/room
    //https://developer.android.com/codelabs/android-room-with-a-view-kotlin#0
    //https://developer.android.com/topic/libraries/architecture/livedata
    fun onClickKliList(view: View){
        view.startAnimation(animScale)
        view.postDelayed({
            val intent= Intent(this,KliListActivity::class.java)
            startActivity(intent)},450)
    }
    fun onClickSpecList(view: View){
        view.startAnimation(animScale)
        view.postDelayed({

            val intent= Intent(this,SpecListActivity::class.java)
            startActivity(intent)},450)
    }

    fun onClickServiceList(view: View){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view,ServiceListFragment.newInstance())
            .commit()
    }


    fun onClose(view: View) {
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbarmnu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.settings -> {
                binding.drawer.apply {
                    if (this.isDrawerOpen(GravityCompat.END))
                        this.closeDrawer(GravityCompat.END)
                    else
                        this.openDrawer(GravityCompat.END)
                }
            }
        }
        return true
    }















}