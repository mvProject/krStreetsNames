package com.mvp.krstreetsname

import android.content.Context
import android.util.Log
import android.widget.Toast

object MsgUtils{
    private val LOG_TAG = "Streets"
    private val MAIN = "Main: "
    private val ADAPTER = "Adapter: "
    private val STORAGE = "Storage: "
    /**
     *
     */
    fun showToast(ctx : Context,msg : String){
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     *
     */
    fun writeLogAdapter(log : String){
        Log.d(LOG_TAG, ADAPTER + log)
    }

    /**
     *
     */
    fun writeLogMain(log : String){
        Log.d(LOG_TAG, MAIN + log)
    }

    /**
     *
     */
    fun writeLogStorage(log : String){
        Log.d(LOG_TAG, STORAGE + log)
    }
}