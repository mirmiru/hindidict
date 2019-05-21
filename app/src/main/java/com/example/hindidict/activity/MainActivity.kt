package com.example.hindidict.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.hindidict.R
import com.example.hindidict.viewmodel.MainViewModel
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.hostFragment)
        bottomNavBar.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)

        // Set up viewmodel
        mainViewModel = ViewModelProviders.of(this!!).get(MainViewModel::class.java)
    }
}
