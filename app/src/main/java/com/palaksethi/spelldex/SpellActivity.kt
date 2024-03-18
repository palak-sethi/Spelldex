package com.palaksethi.spelldex

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.palaksethi.spelldex.databinding.ActivitySpellBinding
import com.palaksethi.spelldex.models.SpellItem
import com.palaksethi.spelldex.retrofit.GetData
import com.palaksethi.spelldex.retrofit.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpellActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpellBinding

    var wikiLink = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpellBinding.inflate(layoutInflater)
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

        var id = intent.getStringExtra("id")
        getSpecificItem(id!!)

        binding.back.setOnClickListener {
            finish()
        }

        binding.wikipedia.setOnClickListener {
            val uri = Uri.parse(wikiLink)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }


    fun getSpecificItem(id: String) {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetData::class.java)
        val call = service.getSpellById(id)
        call.enqueue(object : Callback<SpellItem> {
            override fun onFailure(call: Call<SpellItem>, t: Throwable) {

                Toast.makeText(this@SpellActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<SpellItem>,
                response: Response<SpellItem>
            ) {


                if (response.body()!!.data.attributes?.name.equals(null)) {
                    binding.nameLayout.visibility = View.GONE
                }
                binding.name.text = response.body()!!.data.attributes?.name

                if (response.body()!!.data.attributes?.incantation.equals(null)) {
                    binding.pronunciationLayout.visibility = View.GONE
                }
                binding.pronunciation.text = response.body()!!.data.attributes?.incantation

                if (response.body()!!.data.attributes?.category.equals(null)) {
                    binding.categoryLayout.visibility = View.GONE
                }
                binding.category.text = response.body()!!.data.attributes?.category

                if (response.body()!!.data.attributes?.effect.equals(null)) {
                    binding.effectLayout.visibility = View.GONE
                }
                binding.effect.text = response.body()!!.data.attributes?.effect

                if (response.body()!!.data.attributes?.light.equals(null)) {
                    binding.lightLayout.visibility = View.GONE
                }
                binding.light.text = response.body()!!.data.attributes?.light

                if (response.body()!!.data.attributes?.hand.equals(null)) {
                    binding.handLayout.visibility = View.GONE
                }
                binding.hand.text = response.body()!!.data.attributes?.hand

                if (response.body()!!.data.attributes?.creator.equals(null)) {
                    binding.creatorLayout.visibility = View.GONE
                }
                binding.creator.text = response.body()!!.data.attributes?.creator



                if (response.body()!!.data.attributes?.image.equals(null) || response.body()!!.data.attributes?.image.equals(
                        "https://potterdb.com/images/missing_spell.svg"
                    )
                ) {
                    binding.spellImage.setImageResource(R.drawable.missing_spell);
                } else {
                    Glide.with(this@SpellActivity).load(response.body()!!.data.attributes?.image)
                        .thumbnail(0.05f)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.spellImage)
                }
                if (response.body()!!.data.attributes?.wiki != null) {
                    wikiLink = response.body()!!.data.attributes?.wiki.toString()
                } else {
                    binding.wikipedia.visibility = View.INVISIBLE
                }
            }

        })
    }


}
