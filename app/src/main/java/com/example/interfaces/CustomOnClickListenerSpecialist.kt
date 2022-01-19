package com.example.interfaces

import android.view.View
import com.example.dao.dataclass.Specialist

interface CustomOnClickListenerSpecialist {
     fun onItemClick(item: View, model: Specialist)
}