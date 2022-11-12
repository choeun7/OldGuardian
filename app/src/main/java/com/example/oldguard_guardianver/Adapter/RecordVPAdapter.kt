package com.example.oldguard_guardianver.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.oldguard_guardianver.Activity.DeleteRecordFragment
import com.example.oldguard_guardianver.Activity.ReceivedRecordFragment
import com.example.oldguard_guardianver.Activity.RecordActivity

class RecordVPAdapter(fragmentActivity: RecordActivity) : FragmentStateAdapter(fragmentActivity) {
    //총 아이템의 개수
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ReceivedRecordFragment()
            1 -> DeleteRecordFragment()
            else -> ReceivedRecordFragment()    //둘 다 아닐 때는 ReceivedRecordFragment 기본
        }
    }

}