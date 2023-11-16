package com.example.kapkan

import androidx.appcompat.app.AppCompatActivity
import com.example.kapkan.StartFragment.StartFragment

class MainActivityLogicHolder(context: AppCompatActivity) {

    private val fragmentManager = context.supportFragmentManager
    private val startFragment = StartFragment(fragmentManager)
    fun showStartFragment() {
        fragmentManager
            .beginTransaction()
            .add(R.id.fragment, startFragment)
            .commit()
    }
}