package com.example.projekakhir.ui.admin.AdminFragment.adapter

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projekakhir.databinding.ListSklBinding
import com.example.projekakhir.network.model.response.getAllStudent.Data
import com.example.projekakhir.network.model.response.getAllStudent.DataItem

class AdminAdapter(
    private val onVerificationAction: (DataItem, String) -> Unit
) :
    RecyclerView.Adapter<AdminAdapter.InnerViewHolder>() {

    inner class InnerViewHolder(
        private val binding: ListSklBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: DataItem) {
            with(item) {
                binding.run {
                    val firstName = item.data.firstName
                    val lastName = item.data.lastName
                    tvNama.text = "$firstName $lastName"
                    setProdi.text = item.data.major
                    tvNpm.text = item.data.studentId
                    tvNo.text = (position + 1).toString()
                    //get dokumen
                    var documentUrlArtikel: String? = null
                    documentUrlArtikel = item.document.article
                    btnArtikel.setOnClickListener {
                        documentUrlArtikel?.let { url ->
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            itemView.context.startActivity(browserIntent)
                        } ?: run {
                            Toast.makeText(
                                itemView.context,
                                "Document Url tidak ada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    var documentUrlValsheet: String? = null
                    documentUrlValsheet = item.document.validitySheet
                    btnLembarPengesahan.setOnClickListener {
                        documentUrlValsheet?.let { url ->
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            itemView.context.startActivity(browserIntent)
                        } ?: run {
                            Toast.makeText(
                                itemView.context,
                                "Document Url tidak ada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    var documentUrlSertifikatKompe: String? = null
                    documentUrlSertifikatKompe = item.document.competencyCertificate
                    btnSertifikatKompetensi.setOnClickListener {
                        documentUrlSertifikatKompe?.let { url ->
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            itemView.context.startActivity(browserIntent)
                        } ?: run {
                            Toast.makeText(
                                itemView.context,
                                "Document Url tidak ada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    btnTerima.setOnClickListener {
                        onVerificationAction(item,"VERIFIED")
                    }
                    btnTolak.setOnClickListener {
                        onVerificationAction(item,"REJECTED")
                    }

                }
            }
            binding.ivExpandle.setOnClickListener {
                binding.rowAksi.visibility = View.VISIBLE
                binding.rowBerkas.visibility = View.VISIBLE
                binding.rowBerkasBtn.visibility = View.VISIBLE
                binding.rowProdi.visibility = View.VISIBLE
                binding.ivExpandle.visibility = View.GONE
                binding.ivExpandleClose.visibility = View.VISIBLE
            }
            binding.ivExpandleClose.setOnClickListener {
                binding.rowAksi.visibility = View.GONE
                binding.rowBerkas.visibility = View.GONE
                binding.rowBerkasBtn.visibility = View.GONE
                binding.rowProdi.visibility = View.GONE
                binding.ivExpandle.visibility = View.VISIBLE
                binding.ivExpandleClose.visibility = View.GONE
            }
        }


    }

    private var items: MutableList<DataItem> = mutableListOf()

    fun setItems(items: List<DataItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val binding =
            ListSklBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bindView(items[position])

    }
}