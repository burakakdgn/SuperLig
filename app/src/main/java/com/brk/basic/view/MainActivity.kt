package com.brk.basic.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.view.menu.MenuItemImpl
import androidx.fragment.app.Fragment
import com.brk.basic.R
import com.brk.basic.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        val fragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()


        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    Log.e("sayfa","anasayfa")

                }
                R.id.nav_second -> {
                    replaceFragment(SecondFragment())

                }
                R.id.nav_third -> {
                    replaceFragment(ThirdFragment())

                }
            }
            true
        }
    }
    @SuppressLint("RestrictedApi")
    fun BottomNavigationView.deselectAllItems() {
        val menu = this.menu
        for(i in 0 until menu.size()) {
            (menu.getItem(i) as? MenuItemImpl)?.let {
                it.isCheckable = false
                it.isChecked = false
                it.isCheckable = true
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,fragment)
            .commit()
    }
}