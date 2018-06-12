package com.mvp.krstreetsname

import android.content.Context
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object StreetsStorage {
    private val FILE_NAME = "street_list.ser"
    /**
     *
     */
    fun writeData(context: Context, streets: List<Streets>) {
        var fos: FileOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            // Open file and write list
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            oos = ObjectOutputStream(fos)
            oos.writeObject(streets)
        } catch (e: Exception) {
            MsgUtils.writeLogStorage("Could not write to file.")
            MsgUtils.writeLogStorage(e.toString())
        } finally {
            try {
                oos?.close()
                fos?.close()
            } catch (e: Exception) {
                MsgUtils.writeLogStorage("Could not close the file.")
                MsgUtils.writeLogStorage(e.toString())
            }

        }
    }

    /**
     *
     */
    fun readData(context: Context): MutableList<Streets> {
        var fis: FileInputStream? = null
        var ois: ObjectInputStream? = null

        var streets: MutableList<Streets> = ArrayList()

        try {
            // Open file and read list
            fis = context.openFileInput(FILE_NAME)
            ois = ObjectInputStream(fis)

            streets = ois.readObject() as MutableList<Streets>
        } catch (e: Exception) {
            MsgUtils.writeLogStorage("Could not read from file.")
            MsgUtils.writeLogStorage(e.toString())
        } finally {
            try {
                ois?.close()
                fis?.close()
            } catch (e: Exception) {
                MsgUtils.writeLogStorage("Could not close the file.")
                MsgUtils.writeLogStorage(e.toString())
            }
        }
        return streets
    }

}