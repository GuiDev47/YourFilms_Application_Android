package fr.mm.yourfilmsvf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import android.view.MenuItem
import android.widget.*


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val fragment_home = fragment_home()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment_home)
            .commit()

        bottomNavigationView.menu.findItem(R.id.menu_item_main).isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_qrcode -> {
                    val fragment_home = fragment_home() // Remplacez FragmentMenu1 par votre propre fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment_home)
                        .commit()
                    true
                }
                R.id.menu_item_loop -> {
                    val fragment_search = fragment_search()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment_search)
                        .commit()
                    true
                }
                R.id.menu_item_main -> {
                    val fragment_home = fragment_home() // Remplacez FragmentMenu1 par votre propre fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment_home)
                        .commit()
                    true
                }
                R.id.menu_item_heart -> {
                    val fragment_home = fragment_home() // Remplacez FragmentMenu1 par votre propre fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment_home)
                        .commit()
                    true
                }
                R.id.menu_item_about -> {
                    val fragment_home = fragment_home() // Remplacez FragmentMenu1 par votre propre fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment_home)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
