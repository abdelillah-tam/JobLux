package com.example.joblux.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.joblux.R
import com.google.android.material.textview.MaterialTextView
import javax.inject.Inject

class JobsRecyclerListAdapter
    @Inject constructor()
    : RecyclerView.Adapter<JobsRecyclerListAdapter.ViewHolder>() {

    private var jobList = mutableListOf<com.example.joblux.domain.models.Result>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val jobTitle = itemView.findViewById(R.id.job_title) as MaterialTextView
        val jobDescription = itemView.findViewById(R.id.job_description) as MaterialTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jobItem = jobList[position]
        holder.jobTitle.text = jobItem.title
        holder.jobDescription.text = jobItem.description
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    fun setJobList(list: List<com.example.joblux.domain.models.Result>){
        val diff = Diff(jobList, list)
        val diffResult = DiffUtil.calculateDiff(diff)

        jobList.clear()
        jobList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    class Diff(
        val oldList : List<com.example.joblux.domain.models.Result>,
        val newList : List<com.example.joblux.domain.models.Result>
    ) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title.equals(newList[newItemPosition].title)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].equals(newList[newItemPosition])
        }

    }
}

