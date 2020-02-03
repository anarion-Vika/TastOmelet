package com.requestum.tastomelet.views.dishes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.requestum.tastomelet.R
import kotlinx.android.synthetic.main.list_dishes.view.*
import com.bumptech.glide.request.RequestOptions


class DishAdapter : RecyclerView.Adapter<DishAdapter.ViewHolder>() {

    private var dishList = arrayListOf<DishEntity>()
    private var callback: CallBack? = null

    fun setCallBack(callback: CallBack) {
        this.callback = callback
    }

    fun setData(newData: ArrayList<DishEntity>) {
        val diffResult = DiffUtil.calculateDiff(DishDiffUtil(dishList, newData))
        dishList = newData
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_dishes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dishList[position])
        holder.itemView.setOnClickListener { callback?.onPositionClicked(dishList[position]) }

    }

    override fun getItemCount(): Int {
        return dishList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(dishEntity: DishEntity) {
            itemView.tvTitleRecipe.text = dishEntity.title
            itemView.tvDescriptionRecipe.text = dishEntity.ingredients
            Glide
                .with(itemView.context)
                .load(dishEntity.thumbnail)
                .placeholder(R.drawable.ic_dish_default)
                .error(R.drawable.ic_dish_default)
                .apply(RequestOptions.circleCropTransform())
                .into(itemView.ivIconDishes_list)
        }

    }

    interface CallBack {
        fun onPositionClicked(position: DishEntity)
    }
}
