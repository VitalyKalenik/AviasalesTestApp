package com.vitalykalenik.aviatest.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.vitalykalenik.aviatest.R
import com.vitalykalenik.aviatest.view.models.CityModel
import com.vitalykalenik.aviatest.view.recycler.SearchResultAdapter
import com.vitalykalenik.aviatest.view.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Экран со строкой поиска
 *
 * @author Vitaly Kalenik
 */
@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchResultAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        searchView = findViewById(R.id.search_view)
        recyclerView = findViewById(R.id.search_result_recycler)
        progressBar = findViewById(R.id.progress_bar)
        errorTextView = findViewById(R.id.error_text_view)
        initRecycler()
        startObservingSearchView()
        searchView.requestFocus()
    }

    private fun initRecycler() {
        adapter = SearchResultAdapter(object : SearchResultClick {
            override fun click(city: CityModel) {
                setResult(RESULT_OK, Intent().apply {
                    putExtra(ChooseDestinationActivity.CITY_EXTRA_KEY, city)
                })
                finish()
            }
        })
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        searchViewModel.getSearchSuccessLiveData().observe(this, { cities ->
            progressBar.stop()
            if (cities.isEmpty()) {
                showErrorMessage(R.string.no_results_error)
            } else {
                adapter.updateList(cities)
            }
        })

        searchViewModel.getSearchEmptyQueryLiveData().observe(this, {
            progressBar.stop()
        })

        searchViewModel.getSearchFailureLiveData().observe(this, {
            progressBar.stop()
            showErrorMessage(R.string.search_error)
            Toast.makeText(this, getString(R.string.search_error), Toast.LENGTH_SHORT).show()
        })
    }

    private fun startObservingSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                hideErrorMessage()
                progressBar.start()
                searchViewModel.newQuery(newText)
                return true
            }
        })
    }

    private fun showErrorMessage(errorMessage: String) {
        errorTextView.text = errorMessage
        errorTextView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun showErrorMessage(errorMessageId: Int) {
        errorTextView.text = getString(errorMessageId)
        errorTextView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun hideErrorMessage() {
        errorTextView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun ProgressBar.stop() {
        visibility = View.INVISIBLE
        isIndeterminate = false
    }

    private fun ProgressBar.start() {
        visibility = View.VISIBLE
        isIndeterminate = true
    }

    interface SearchResultClick {

        fun click(city: CityModel)
    }

    companion object {

        /**
         * Новый [intent] для старта этой активити
         *
         * @param context Контекст для старта активити
         */
        @JvmStatic
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }
}