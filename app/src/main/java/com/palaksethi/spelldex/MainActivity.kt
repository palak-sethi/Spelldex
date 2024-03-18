package com.palaksethi.spelldex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
class MainActivity : AppCompatActivity() {

    lateinit var spellViewModel: SpellViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SpellPagingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.spellList)

        spellViewModel = ViewModelProvider(this).get(SpellViewModel::class.java)

        adapter = SpellPagingAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        adapter.setClickListener(onCLickedSpellItem)


        spellViewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }

    private val onCLickedSpellItem  = object : SpellPagingAdapter.OnItemClickListener{
        override fun onClicked(id: String) {
            var intent = Intent(this@MainActivity,SpellActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
    }
}