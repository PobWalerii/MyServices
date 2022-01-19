package com.example.interfaces

import android.view.View
import com.example.dao.dataclass.KlientAndMark

interface CustomOnClickListenerKlientAndMark {
    fun onItemClick(item: View, model: KlientAndMark)
}