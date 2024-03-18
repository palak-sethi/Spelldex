package com.palaksethi.spelldex

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.palaksethi.spelldex.databinding.ActivitySearchBinding
import com.palaksethi.spelldex.models.Spells
import com.palaksethi.spelldex.paging.SpellPagingAdapter
import com.palaksethi.spelldex.retrofit.GetData
import com.palaksethi.spelldex.retrofit.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

        binding.searchString.onSubmit { getSearchItem(binding.searchString.text.toString()) }

        binding.searchString.doOnTextChanged { newText, _, _, _ ->
            binding.search.visibility = if (newText.isNullOrEmpty()) View.INVISIBLE
            else View.VISIBLE
        }

        adapter = SearchAdapter()
        adapter.setClickListener(onCLickedSpellItem)

        binding.back.setOnClickListener {
            finish()
        }
        binding.search.setOnClickListener {
            getSearchItem(binding.searchString.text.toString())
        }


    }


    fun getSearchItem(searchStr: String) {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetData::class.java)
        val call = service.getSpellBySearch(searchStr)
        call.enqueue(object : Callback<Spells> {
            override fun onFailure(call: Call<Spells>, t: Throwable) {

                Toast.makeText(this@SearchActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<Spells>,
                response: Response<Spells>
            ) {
                Toast.makeText(this@SearchActivity, "Data Received", Toast.LENGTH_SHORT)
                response.body()?.data?.let { adapter.setSpellList(it) }

                binding.searchList.layoutManager = LinearLayoutManager(this@SearchActivity)
                binding.searchList.setHasFixedSize(true)
                binding.searchList.adapter = adapter
            }
        })
    }

    private val onCLickedSpellItem  = object : SearchAdapter.OnItemClickListener{
        override fun onClicked(id: String) {
            var intent = Intent(this@SearchActivity,SpellActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
    }

    fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                func()
            }

            true

        }
    }


}
