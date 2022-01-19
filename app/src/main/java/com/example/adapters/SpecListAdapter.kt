package com.example.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.castomclass.SpecCardView
import com.example.dao.dataclass.KlientAndMark
import com.example.dao.dataclass.Specialist
import com.example.interfaces.CustomOnClickListenerKlientAndMark
import com.example.interfaces.CustomOnClickListenerSpecialist
import com.example.myservices.KliListActivity
import com.example.myservices.R
import com.example.myservices.SpecListActivity
import com.example.myservices.databinding.SpecItemBinding

class SpecListAdapter : ListAdapter<Specialist, SpecListAdapter.SpecViewHolder>(SPECIALIST_COMPARATOR) {

    private var customListener: CustomOnClickListenerSpecialist? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecViewHolder {
        return SpecViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun getItemPosition(item: Specialist): Int {
        val x= currentList.indexOf(item)
        return x
    }

    override fun getItemId(position: Int): Long {
        return currentList.get(position).id.toLong()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: SpecViewHolder, position: Int) {

        val current = getItem(position)

        holder.bind(current.speciality+"\n"+current.lastName+" "+current.firstName+" "+current.surName, current.phone, current.id)

        val binding = SpecItemBinding.bind(holder.itemView)
        val cardView = binding.specCard

        binding.butName.setOnClickListener {
            SpecListActivity.currentIdSpecialist=cardView.specid
            val card = binding.cardMnu
            val status= card.visibility
            card.visibility=if(status== View.GONE) View.VISIBLE else View.GONE
            val color = ContextCompat.getColor(cardView.context, R.color.button_activ)
            cardView.setCardBackgroundColor(color)
            if(card.visibility== View.VISIBLE) {notifyDataSetChanged()}
        }

        binding.butEdit.setOnClickListener {
             customListener?.onItemClick(it,current)
        }
        binding.butSchedule.setOnClickListener {
             customListener?.onItemClick(it,current)
        }
        binding.butDoc.setOnClickListener {
             customListener?.onItemClick(it,current)
        }
        binding.butCall.setOnClickListener {
             customListener?.onItemClick(it,current)
        }
    }

    fun setOnItemClickListener(listener: CustomOnClickListenerSpecialist) {
        customListener = listener
    }

    class SpecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = SpecItemBinding.bind(itemView)
        private val specTxtBut: Button = binding.butName
        private val specPhBut: Button = binding.butPhone
        private val specCardView: SpecCardView = binding.specCard
        fun bind(text: String?, phone: String?, id: Int?) {
            specTxtBut.text= text
            specPhBut.text=phone
            specCardView.specid=id!!
            specCardView.spectel=phone!!
            val cardView = binding.specCard

            if(specCardView.specid != SpecListActivity.currentIdSpecialist) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context,R.color.button))
                binding.cardMnu.visibility= View.GONE
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context,R.color.button_activ))
                binding.cardMnu.visibility= View.VISIBLE
            }
        }

        companion object {
            fun create(parent: ViewGroup): SpecViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.spec_item, parent, false)
                return SpecViewHolder(view)
            }
        }
    }

    companion object {
        private val SPECIALIST_COMPARATOR = object : DiffUtil.ItemCallback<Specialist>() {

            override fun areItemsTheSame(oldItem: Specialist, newItem: Specialist): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Specialist, newItem: Specialist): Boolean {
                return oldItem.lastName == newItem.lastName && oldItem.speciality == newItem.speciality
                        && oldItem.firstName == newItem.firstName
                        && oldItem.surName == newItem.surName
                        && oldItem.phone == newItem.phone
            }


        }
    }

}