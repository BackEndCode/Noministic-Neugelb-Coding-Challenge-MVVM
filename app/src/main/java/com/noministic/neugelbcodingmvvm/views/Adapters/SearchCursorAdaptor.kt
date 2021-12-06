package com.noministic.neugelbcodingmvvm.views.Adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.noministic.neugelbcodingmvvm.R
import androidx.cursoradapter.widget.SimpleCursorAdapter


class SearchCursorAdaptor(context: Context,val layout: Int, c: Cursor?, from: Array<String>, to: IntArray) :
    SimpleCursorAdapter(context,layout,c,from,to) {

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View? {
        return LayoutInflater.from(context).inflate(layout, null)
    }

    override fun bindView(view: View, context: Context?, cursor: Cursor) {
        super.bindView(view, context, cursor)
        val titleS = view.findViewById<View>(R.id.textView_search_title) as TextView
        val title = cursor.getString(1)
        titleS.text = title
    }

}
