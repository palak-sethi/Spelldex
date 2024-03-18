package com.palaksethi.spelldex

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.palaksethi.spelldex.paging.SpellPagingAdapter
import com.palaksethi.spelldex.viewmodels.SpellViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var spellViewModel: SpellViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SpellPagingAdapter
    lateinit var search: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // For devices with LOLLIPOP and above
            window.statusBarColor = getResources().getColor(R.color.black)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // For devices with KITKAT
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val decorView = window.decorView
            decorView.setBackgroundColor(getResources().getColor(R.color.black))
        }

        recyclerView = findViewById(R.id.spellList)

        spellViewModel = ViewModelProvider(this).get(SpellViewModel::class.java)

        adapter = SpellPagingAdapter()

        search = findViewById(R.id.search)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        adapter.setClickListener(onCLickedSpellItem)

        search.setOnClickListener(this)

        spellViewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }

    private val onCLickedSpellItem = object : SpellPagingAdapter.OnItemClickListener {
        override fun onClicked(id: String) {
            var intent = Intent(this@MainActivity, SpellActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.search -> {
                var intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }


}