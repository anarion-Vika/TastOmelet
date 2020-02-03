package com.requestum.tastomelet.views.dishes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.requestum.tastomelet.R
import com.requestum.tastomelet.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_main.*


class DishFragment : Fragment(R.layout.fragment_main) {

    private var dishAdapter = DishAdapter()
    private var intent = Intent(Intent.ACTION_VIEW)
    private lateinit var viewModelDish: ViewModelDish
    private var queryValue = "QUERY_VALUE"
    private val dishQueryDefault = "i=onions,garlic&q=omelet&p=3"
    private lateinit var networkUtil: NetworkUtil
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListener()
        subscribeData()
    }

    private fun setupViews() {
        viewModelDish = ViewModelProviders.of(this).get(ViewModelDish::class.java)
        svSearchDish.queryHint = getString(R.string.hint_for_search)
        val linearLayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(
            rvRecipes.context, linearLayoutManager.orientation
        )
        context?.let {
            dividerItemDecoration.setDrawable(it.resources.getDrawable(R.drawable.divider))
            networkUtil = NetworkUtil(it)
        }
        rvRecipes.addItemDecoration(dividerItemDecoration)
        rvRecipes.layoutManager = linearLayoutManager
        rvRecipes.adapter = dishAdapter
        dishAdapter.setCallBack(object : DishAdapter.CallBack {
            override fun onPositionClicked(position: DishEntity) {
                intent.data = Uri.parse(position.href)
                activity?.startActivity(intent)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.getString(queryValue)?.let {
            if (it.isNotEmpty())
            svSearchDish.setQuery(it, true) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(queryValue,svSearchDish.query.toString() )
    }

    private fun setupListener() {
        svSearchDish.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.equals("") && checkIsNotOffline())
                    viewModelDish.getAllDishesFromServer(dishQueryDefault)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (checkIsNotOffline())
                    viewModelDish.getAllDishesFromServer(query)
                    svSearchDish.clearFocus()
                return false
            }
        })
    }

    private fun checkIsNotOffline(): Boolean {
        return if (networkUtil.checkNetworkAvailable())
            true
        else {
            context?.let { makeToast(it.resources.getString(R.string.error_offline)) }
            false
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun subscribeData() {
        viewModelDish.dishList.observe(viewLifecycleOwner, Observer {
            var dishList = arrayListOf<DishEntity>()
            for (item: DishEntity in it) {
                dishList.add(item)
            }
            dishAdapter.setData(dishList)
            if (dishAdapter.itemCount ==0 )
                context?.let {  makeToast(it.resources.getString(R.string.erro_result_absent))}
        })
        viewModelDish.errorMessage.observe(viewLifecycleOwner, Observer {
            makeToast(it)
        })
    }

}
