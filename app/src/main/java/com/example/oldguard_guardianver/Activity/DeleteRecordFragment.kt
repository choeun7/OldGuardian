package com.example.oldguard_guardianver.Activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oldguard_guardianver.Adapter.DeletedRecordRVAdapter
import com.example.oldguard_guardianver.Request.DeletedRecordData
import com.example.oldguard_guardianver.databinding.FragmentDeletedRecordBinding

/**   기록에서 삭제했던 어르신 보기 및 복구 화면   */
class DeleteRecordFragment : Fragment() {
    lateinit var viewBinding : FragmentDeletedRecordBinding
    private lateinit var adapter : DeletedRecordRVAdapter
    val manager = LinearLayoutManager(activity)
    val dataList : ArrayList<DeletedRecordData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //fragment_deleted_record 와 연결
        viewBinding = FragmentDeletedRecordBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = ArrayList<DeletedRecordData>()
        val deletedRecordRVAdapter = DeletedRecordRVAdapter(dataList)

        adapter = DeletedRecordRVAdapter(dataList)
        viewBinding.deletedRv.adapter = adapter

//      처음 시작은 역순으로 (최신순) 출력
        manager.reverseLayout = true
        manager.stackFromEnd = true
        viewBinding.deletedRv.layoutManager = manager

        //예시 data
        dataList.apply {
            add(DeletedRecordData("문현주","2022.11.22 22:15"))
            add(DeletedRecordData("박상철","2022.11.22 22:16"))
            add(DeletedRecordData("김영길","2022.11.22 22:17"))
            add(DeletedRecordData("유영식","2022.11.22 22:18"))
        }
//                새로운 데이터 추가 시 add, 수정 시 set, 삭제 시 removeAt

        //adapter.items = dataList
        //adapter.notifyDataSetChanged()   //추가로 add시 필수

        //복구 버튼을 누르면 이 화면에서 없어지는 기능
        //이후 main화면에 다시 데이터 나오는 방법 구현 필요
        //Fragment에서 recyclerView button click event가 잘 안돼서 복구 버튼은 DeleteRecordRVAdapter에 만들어놨어요
//        deletedRecordRVAdapter.setOnBtnClickListener(object : DeletedRecordRVAdapter.OnBtnClickListener {
//            override fun onBtnClick(view: View, data: DeletedRecordData, position: Int) {
//                Log.d("확인","복구버튼")
//                dataList.removeAt(position)
//                deletedRecordRVAdapter.notifyItemRemoved(position)
//            }
//        })

        //드롭다운 spinner 구현화면
        viewBinding.deletedSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (viewBinding.deletedSpinner.getItemAtPosition(position)) {
                    "최신순" -> {
                        updateLatestDeleted()
                    }
                    "오래된순" -> {
                        updateOldDeleted()
                    }
                    else -> {
                        updateLatestDeleted()
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    //최신순으로 add
    fun updateLatestDeleted() {
        //역순으로 recyclerView 정렬
        manager.reverseLayout = true
        manager.stackFromEnd = true
        viewBinding.deletedRv.layoutManager = manager
    }
    //오래된 순으로 add
    fun updateOldDeleted() {
        //원래대로 recyclerView 정렬
        manager.reverseLayout = false
        manager.stackFromEnd = false
        viewBinding.deletedRv.layoutManager = manager
    }


}