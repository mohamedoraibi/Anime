package camp.codelab.anime

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import camp.codelab.anime.models.Anime
import camp.codelab.anime.models.SearchResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchAnime(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun searchAnime(searchQuery: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val animeInterface = retrofit.create(AnimeInterface::class.java)
        animeInterface.searchAnime(searchQuery)
                .enqueue(object : Callback<SearchResponse> {
                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {


//
//                    Log.i("","response is message = ${response.message()}")
//                    Log.i("","response is raw = ${response.raw()}")
//                    Log.i("","response is error body = ${response.errorBody()}")
//                    Log.i("","response is null = ${response.body()==null}")
                        response.body()?.let {
                            prepareRecycleView(it.results)
                        }
                    }


                })
    }

    fun prepareRecycleView(animeList: List<Anime>) {

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = AnimeAdapter(animeList)
    }
}
