package com.example.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.castomclass.KliCardView
import com.example.dao.dataclass.KlientAndMark
import com.example.interfaces.CustomOnClickListenerKlientAndMark
import com.example.myservices.KliListActivity
import com.example.myservices.R
import com.example.myservices.databinding.KliItemBinding


class KliListAdapter : ListAdapter<KlientAndMark, KliListAdapter.KliViewHolder>(KLIENT_COMPARATOR) {

        private var customListener: CustomOnClickListenerKlientAndMark? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KliViewHolder {
            return KliViewHolder.create(parent)
        }
        override fun getItemCount(): Int {
            return currentList.size
        }
        fun getItemPosition(item: KlientAndMark): Int {
            val x= currentList.indexOf(item)
            return x
        }
        override fun getItemId(position: Int): Long {
            return currentList.get(position).klient.id.toLong()
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: KliViewHolder, position: Int) {

            val current = getItem(position)
            val mark = if(current.klimark?.mark==null) 0 else current.klimark.mark

            holder.bind(current.klient.lastName+" "+current.klient.firstName+" "+current.klient.surName, current.klient.phone, current.klient.id, mark)

            val binding = KliItemBinding.bind(holder.itemView)
            val cardView = binding.kliCard

            binding.butName.setOnClickListener {
                KliListActivity.currentIdKlient=cardView.kliid
                val card = binding.cardMnu
                val status= card.visibility
                card.visibility=if(status==GONE) VISIBLE else GONE
                val color = ContextCompat.getColor(cardView.context, R.color.button_activ)
                cardView.setCardBackgroundColor(color)
                if(card.visibility== VISIBLE) {notifyDataSetChanged()}
            }
            binding.butChek.setOnClickListener {
                customListener?.onItemClick(it,current)
            }
            binding.butEdit.setOnClickListener {
                 customListener?.onItemClick(it,current)
            }
            binding.butDoc.setOnClickListener {
                 customListener?.onItemClick(it,current)
            }
            binding.butCall.setOnClickListener {
                 customListener?.onItemClick(it,current)
            }
        }

    fun setOnItemClickListener(listener: CustomOnClickListenerKlientAndMark) {
        customListener = listener
    }

    class KliViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val binding = KliItemBinding.bind(itemView)
            private val kliTxtBut: Button = binding.butName
            private val kliPhBut: Button = binding.butPhone
            private val kliCardView: KliCardView = binding.kliCard
            private val kliCheck: ImageView = binding.imageCheck
            fun bind(text: String?, phone: String?, id: Int?, klimark: Int?) {
                val mark= klimark==1
                kliTxtBut.text= if(mark) {"  "} else {""} + text
                kliCheck.visibility= if(mark) VISIBLE else INVISIBLE
                kliPhBut.text=phone

                kliCardView.kliid=id!!

                val cardView = binding.kliCard

                if(kliCardView.kliid != KliListActivity.currentIdKlient) {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context,R.color.button))
                    binding.cardMnu.visibility=GONE
                } else {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context,R.color.button_activ))
                    binding.cardMnu.visibility= VISIBLE
                }
            }

            companion object {
                fun create(parent: ViewGroup): KliViewHolder {
                    val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.kli_item, parent, false)
                    return KliViewHolder(view)
                }
            }
        }

    companion object {
            private val KLIENT_COMPARATOR = object : DiffUtil.ItemCallback<KlientAndMark>() {

            override fun areItemsTheSame(oldItem: KlientAndMark, newItem: KlientAndMark): Boolean {
                return oldItem.klient.id == newItem.klient.id
            }

            override fun areContentsTheSame(oldItem: KlientAndMark, newItem: KlientAndMark): Boolean {
                return oldItem.klient.lastName == newItem.klient.lastName
                        && oldItem.klimark?.mark == newItem.klimark?.mark
                        && oldItem.klient.firstName == newItem.klient.firstName
                        && oldItem.klient.surName == newItem.klient.surName
                        && oldItem.klient.phone == newItem.klient.phone
            }
        }
    }

}

