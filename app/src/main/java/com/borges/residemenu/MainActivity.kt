package com.borges.residemenu

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.special.ResideMenu.ResideMenu
import com.special.ResideMenu.ResideMenu.OnMenuListener
import com.special.ResideMenu.ResideMenuItem


class MainActivity : AppCompatActivity() {

    private var resideMenu: ResideMenu? = null
    private var itemHome: ResideMenuItem? = null
    private var itemProfile: ResideMenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpMenu()
        changeFragment(HomeFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun setUpMenu() {

        // attach to current activity;
        resideMenu = ResideMenu(this)
//        resideMenu?.setUse3D(true)
        resideMenu?.setBackground(R.drawable.background)
        resideMenu?.attachToActivity(this)
        resideMenu?.menuListener = menuListener
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu?.setScaleValue(0.6f)

        // criando os menu items;
        itemHome = ResideMenuItem(this, R.drawable.icon_home, "Home")
        itemProfile = ResideMenuItem(this, R.drawable.icon_profile, "Profile")
        itemHome?.setOnClickListener {
            onClick(itemHome!!)
        }
        itemProfile?.setOnClickListener {
            onClick(itemProfile!!)
        }

        resideMenu?.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT)
        resideMenu?.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT)

        resideMenu?.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT)

    }

    private val menuListener: OnMenuListener = object : OnMenuListener {
        override fun openMenu() {
            Toast.makeText(applicationContext, "Menu is opened!", Toast.LENGTH_SHORT).show()
        }

        override fun closeMenu() {
            Toast.makeText(applicationContext, "Menu is closed!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return resideMenu!!.dispatchTouchEvent(ev)
    }

    fun onClick(view: View) {
        if (view === itemHome) {
            changeFragment(HomeFragment())
        } else if (view === itemProfile) {
            changeFragment(ProfileFragment())
        }
        resideMenu!!.closeMenu()
    }

    private fun changeFragment(targetFragment: Fragment) {
        resideMenu!!.clearIgnoredViewList()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

    fun signOut(item: MenuItem) {
        resideMenu?.openMenu(ResideMenu.DIRECTION_LEFT)
    }

}