package com.example.taskdl

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.taskdl.databinding.ActivityHomeBinding
import com.example.taskdl.utils.OnFragmentTitleChangeListener
import com.google.android.material.navigation.NavigationView


class HomeActivity : AppCompatActivity() , OnFragmentTitleChangeListener {

    var layoutBinding: ActivityHomeBinding? = null
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initNavigationMenu()
    }

    private fun initNavigationMenu() {
        layoutBinding?.apply {

            setSupportActionBar(appBarDashboard.mainToolbar)

            setupActionBarWithNavController(
                navController,
                appBarConfiguration
            )


            toggle = ActionBarDrawerToggle(this@HomeActivity, mainDrawerLayout, appBarDashboard.mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            mainDrawerLayout.addDrawerListener(toggle)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)


            navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
                val navOption = NavOptions.Builder().setPopUpTo(R.id.allMatchesFragment, false).build()
                if (layoutBinding?.mainDrawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
                    layoutBinding?.mainDrawerLayout?.closeDrawer(GravityCompat.START)
                }
                when (it.itemId) {

                    R.id.navAllMatches -> {
                        if (navController.currentDestination?.id != R.id.allMatchesFragment) {
                            navController.navigate(R.id.allMatchesFragment, null, navOption)
                            true
                        } else false
                    }

                    R.id.navSavedMatches -> {
                        if (navController.currentDestination?.id != R.id.savedMatchesFragment) {
                            navController.navigate(R.id.savedMatchesFragment, null, navOption)
                            true
                        } else false
                    }
                }

                false
            })
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**Lazy init Navigation host controller*/
    private val navController by lazy { findNavController(R.id.containerNavHost) }

    /**Here add the navigation drawer fragments to show Menu(Hamburger) icon
     * This appBarConfiguration is used to handle the menu icon and back arrow*/
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.allMatchesFragment,
                R.id.savedMatchesFragment
            ), layoutBinding?.mainDrawerLayout
        )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onFragmentTitle(strTitle: String?, showSubTitle: Boolean) {
        try {
            val currentApiVersion = Build.VERSION.SDK_INT
            if (currentApiVersion >= Build.VERSION_CODES.LOLLIPOP) {
                layoutBinding?.appBarDashboard?.mainAppbar?.elevation = 0.0f
            }
            layoutBinding?.appBarDashboard?.tvTitle?.apply {
                text = strTitle
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("${e.message}","$e")
        }
    }

}