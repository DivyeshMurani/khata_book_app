package io.selfmade.khatabook.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.customview.widget.Openable
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.google.android.material.appbar.MaterialToolbar
import io.selfmade.khatabook.R
import io.selfmade.khatabook.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mToolbar: MaterialToolbar

    private var navController: NavController? = null
    private lateinit var navHostFragment: NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        initToolbar()

    }

    private fun initToolbar() {

        mToolbar = binding.mToolbarRoot.mToolbar
        mToolbar.overflowIcon?.setTint(Color.WHITE)

        mToolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        if (navController == null) {
            navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
            NavigationUI.setupActionBarWithNavController(this@MainActivity, navController!!)
            navController!!.addOnDestinationChangedListener { controller, destination, arguments ->
                val it =
                    navHostFragment!!.childFragmentManager.findFragmentById(R.id.nav_host_fragment)
            }
        }


    }

    fun getNavController(): NavController {
        return navController!!
    }

    fun popBackFragment() {
        navController!!.popBackStack()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController!!.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_crete_item) {
            startActivity(Intent(activity, CreateItemActivity::class.java))
        } else if (item.itemId == R.id.menu_crete_weight) {
            startActivity(Intent(activity, CreateWeightTypeActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}


