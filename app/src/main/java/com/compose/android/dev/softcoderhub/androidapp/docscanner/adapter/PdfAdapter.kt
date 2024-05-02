package com.compose.android.dev.softcoderhub.androidapp.docscanner.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.compose.android.dev.softcoderhub.androidapp.docscanner.databinding.PdfViewBinding
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity.PDF

class PdfAdapter:RecyclerView.Adapter<PdfAdapter.PdfViewHolder>(){

    private var pdfList = ArrayList<PDF>()

    var onItemClick :((PDF)->Unit?)?=null
    var onDotClick :((PDF)->Unit?)?=null


    inner class PdfViewHolder(val binding : PdfViewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        return PdfViewHolder(PdfViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return  pdfList.size
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        val data=pdfList[position]

        holder.binding.pdfTitle.text = data.title.toString()

        holder.binding.pdfLayout.setOnClickListener {
            onItemClick!!.invoke(pdfList[position])
        }

        holder.binding.dotLayout.setOnClickListener {
            onDotClick!!.invoke(pdfList[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList:List<PDF>){
        pdfList.clear()
        pdfList.addAll(newList)
        notifyDataSetChanged()
    }
}