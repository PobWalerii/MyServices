package com.example.myservices

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myservices.databinding.ActivityMainBinding
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
        animTranslate = AnimationUtils.loadAnimation(this, R.anim.translate)

        binding.butKli.startAnimation(animRotate)
        binding.butSpec.startAnimation(animAlpha)
        binding.butFree.startAnimation(animAlpha)
        binding.butWaiting.startAnimation(animRotate)
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

    fun startAnimate(){
        //binding.butKli.startAnimation(animScale)
        //binding.butSpec.startAnimation(animScale)
        //binding.butWaiting.startAnimation(animScale)
        //binding.butFree.startAnimation(animScale)
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

        }
        return true
    }

}