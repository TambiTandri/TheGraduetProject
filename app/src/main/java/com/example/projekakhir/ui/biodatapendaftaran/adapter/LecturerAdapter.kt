package com.example.projekakhir.ui.biodatapendaftaran.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.projekakhir.network.model.response.lecturer.DataItem

//class LecturerAdapter(context: Context, lecturers: List<DataItem>) :
//    ArrayAdapter<DataItem>(context, android.R.layout.simple_dropdown_item_1line, lecturers) {
//
//    private var allLecturers: List<DataItem> = lecturers
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = convertView ?: LayoutInflater.from(context)
//            .inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
//
//        val lecturer = getItem(position)
//        val textView = view.findViewById<TextView>(android.R.id.text1)
//        textView.text = lecturer?.lecturerName
//
//        return view
//    }
//
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val filterResults = FilterResults()
//                if (constraint == null || constraint.isEmpty()) {
//                    filterResults.values = allLecturers
//                    filterResults.count = allLecturers.size
//                } else {
//                    val filteredList = allLecturers.filter {
//                        it.lecturerName?.contains(constraint, ignoreCase = true) == true
//                    }
//                    filterResults.values = filteredList
//                    filterResults.count = filteredList.size
//                }
//                return filterResults
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                clear()
//                if (results != null && results.count > 0) {
//                    addAll(results.values as List<DataItem>)
//                }
//                notifyDataSetChanged()
//            }
//        }
//    }
//}
class LecturerAdapter(context: Context, lecturers: List<DataItem>) :
    ArrayAdapter<DataItem>(context, android.R.layout.simple_dropdown_item_1line, lecturers) {

    private var allLecturers: List<DataItem> = lecturers

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_dropdown_item_1line, parent, false)

        val lecturer = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = lecturer?.lecturerName

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.values = allLecturers
                    filterResults.count = allLecturers.size
                } else {
                    val filteredList = allLecturers.filter {
                        it.lecturerName?.contains(constraint, ignoreCase = true) == true
                    }
                    filterResults.values = filteredList
                    filterResults.count = filteredList.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                if (results != null && results.count > 0) {
                    addAll(results.values as List<DataItem>)
                }
                notifyDataSetChanged()
            }
        }
    }
}
