package com.devmike.mobilegrocery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast

import com.devmike.mobilegrocery.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding:MainActivityBinding by viewBinding()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//       setUpNavigation()

        setSupportActionBar(binding.toolbar)
        binding.toolbar.inflateMenu(R.menu.cart_menu)


    }

    private fun setUpNavigation() {



        binding.toolbar.setOnMenuItemClickListener { menuitem ->
            when(menuitem.itemId){
                R.id.action_cart ->{

                    Toast.makeText(this,"Mike trials",Toast.LENGTH_LONG).show()


                    true
                }
                else -> false
            }

        }


    }
}