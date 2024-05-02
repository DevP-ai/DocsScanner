package com.compose.android.dev.softcoderhub.androidapp.docscanner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.compose.android.dev.softcoderhub.androidapp.docscanner.databinding.PdfViewBinding
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity.PDF

class PdfAdapter:RecyclerView.Adapter<PdfAdapter.PdfViewHolder>(){

    private var pdfList = ArrayList<PDF>()

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
    }

    fun setData(newList:List<PDF>){
        pdfList.clear()
        pdfList.addAll(newList)
        notifyDataSetChanged()
    }
}