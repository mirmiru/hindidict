package com.example.hindidict.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hindidict.R
import com.example.hindidict.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

//class MainActivity : AppCompatActivity() {
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var mainViewModel: MainViewModel
    var isToolBarSetup = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.top_toolbar)

        setSupportActionBar(toolbar)


        supportActionBar?.hide()
        navController = Navigation.findNavController(this, R.id.hostFragment)
        bottomNavBar.setupWithNavController(navController)
//        NavigationUI.setupActionBarWithNavController(this, navController)
//        setupActionBarWithNavController(navController)

        // Set up viewmodel
        mainViewModel = ViewModelProviders.of(this!!).get(MainViewModel::class.java)
    }
}
