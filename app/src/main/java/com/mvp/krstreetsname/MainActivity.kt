package com.mvp.krstreetsname

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import com.android.volley.toolbox.Volley
import android.net.ConnectivityManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val JSON_URL = "https://rename.kr.ua/api/v1/streets"
    val StreetList = mutableListOf<Streets>()
    lateinit var adapter : StreetsAdapter
    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MsgUtils.writeLogMain("on create")
        setContentView(R.layout.activity_main)

        initToolbar()
        initRecyclerView()
    }

    /**
     *
     */
    private fun initToolbar() {
        // Set the toolbar as support action bar
        setSupportActionBar(toolbar)
        // Now get the support action bar
        val actionBar = supportActionBar
        // Set toolbar title/app title
        actionBar!!.title = getString(R.string.app_name)
        // Set action bar/toolbar sub title
        actionBar.subtitle = getString(R.string.app_name_subtitle)
        // Set action bar elevation
        actionBar.elevation = 4.0F
        // Display the app icon in action bar/toolbar
        actionBar.setDisplayShowHomeEnabled(true)
        //actionBar.setLogo(R.mipmap.ic_launcher)
        //actionBar.setDisplayUseLogoEnabled(true)
    }

    /**
     *
     */
    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.streets_list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        MsgUtils.writeLogMain("recyclerView init")
    }

    /**
     *
     */
    fun GetStreetsNames(){
        val stringRequest = StringRequest(Request.Method.GET, JSON_URL,
            Response.Listener { response ->
                try {
                    //getting the whole json object from the response
                    val obj = JSONObject(response)
                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    val Array = obj.getJSONObject("r1").getJSONArray("objects")
                    MsgUtils.writeLogMain("r1 objects loaded")
                    val Array2 = obj.getJSONObject("r2").getJSONArray("objects")
                    MsgUtils.writeLogMain("r2 objects loaded")
                    //now looping through all the elements of the json array
                    for (i in 0 until Array.length()) {
                        //getting the json object of the particular index inside the array
                        val street = Array.getJSONObject(i)
                        //creating a hero object and giving them the values from json object
                        val str = Streets(street.getString("old_name"), street.getString("new_name"))
                        //adding the hero to herolist
                        StreetList.add(str)
                        MsgUtils.writeLogMain("r1 objects added to list")
                    }
                    MsgUtils.writeLogMain("list capacity with r1 - " + StreetList.size.toString())
                    for (i in 0 until Array2.length()) {
                        //getting the json object of the particular index inside the array
                        val street = Array2.getJSONObject(i)
                        //creating a hero object and giving them the values from json object
                        val str = Streets(street.getString("old_name"), street.getString("new_name"))
                        //adding the hero to herolist
                        StreetList.add(str)
                        MsgUtils.writeLogMain("r2 objects added to list")
                    }
                    MsgUtils.writeLogMain("list capacitywith r1 -  " + StreetList.size.toString())
                    //creating custom adapter object
                    adapter = StreetsAdapter(StreetList, this)
                    recyclerView.adapter = adapter
                    MsgUtils.writeLogMain("adapter assigned")
                } catch (e: JSONException) {
                    MsgUtils.writeLogMain(e.message!!)
                }
            },
            Response.ErrorListener { error ->
                //displaying the error in toast if occurrs
                MsgUtils.showToast(applicationContext, error.message!!)
            })
        //creating a request queue
        val requestQueue = Volley.newRequestQueue(this)
        //adding the string request to request queue
        requestQueue.add(stringRequest)
    }

    /**
     *
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.searchfile,menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(str: String): Boolean {
                if (str.length>0)
                {
                    val findadapter = StreetsAdapter(adapter.findStreets(str),applicationContext)
                    recyclerView.adapter = findadapter
                    MsgUtils.writeLogMain("data filtered")
                }
                else {
                    recyclerView.adapter = adapter
                    MsgUtils.writeLogMain("data not filtered")
                }
                return false
            }

            override fun onQueryTextSubmit(str: String): Boolean {
                return false
            }
        })
        return true
       // return super.onCreateOptionsMenu(menu)
    }

    /**
     *
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.refresh -> {
                loadData()
                MsgUtils.showToast(this, "Refresh selected")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     *
     */
    fun isConnected(ctx : Context): Boolean {
        val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    /**
     *
     */
    override fun onResume() {

        MsgUtils.writeLogMain("on resume")
        initData()
        super.onResume()
    }

    /**
     *
     */
    override fun onPause() {
        MsgUtils.writeLogMain("on pause")
        adapter.saveStreets()
        super.onPause()
    }

    /**
     *
     */
    fun initData(){
        adapter = StreetsAdapter(StreetList, this)
        adapter.loadStreets()
        MsgUtils.writeLogMain("data load from file")
        recyclerView.adapter = adapter
        if (adapter.streets.size == 0){
            MsgUtils.showToast(this,"File is empty!Try to download")
            loadData()
        }
    }

    /**
     *
     */
    fun loadData(){
        if (isConnected(this)) {
            GetStreetsNames()
            MsgUtils.writeLogMain("data load from internet")
        }
        else {
            MsgUtils.showToast(this, "Check Internet connection")
        }
    }
}

