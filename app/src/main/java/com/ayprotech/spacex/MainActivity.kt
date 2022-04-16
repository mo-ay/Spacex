package com.ayprotech.spacex

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.ayprotech.spacex.databinding.MainActivityBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolBar)
        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment).navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)


    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        // Get the notifications MenuItem and LayerDrawable (layer-list)
        val item: MenuItem = menu!!.findItem(R.id.notification)

        val badgeDrawable = BadgeDrawable.create(this)
        badgeDrawable.number = 2
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            badgeDrawable.backgroundColor = getColor(R.color.gold)
        }

        // Update LayerDrawable's BadgeDrawable
        BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.appBarMain.toolBar, item.itemId)
        return true

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}