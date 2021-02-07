package top.ntutn.novelrecommend.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import top.ntutn.novelrecommend.adapter.CommonDiffCallback
import top.ntutn.novelrecommend.adapter.CommonMutiItem
import top.ntutn.novelrecommend.adapter.CommonViewHolder
import top.ntutn.novelrecommend.databinding.FragmentDebugEntranceBinding
import top.ntutn.novelrecommend.databinding.ItemDebugEntranceBinding
import top.ntutn.novelrecommend.ui.base.BaseFragment

class DebugEntranceFragment : BaseFragment() {
    private lateinit var binding: FragmentDebugEntranceBinding
    private val debugEntranceViewModel by viewModels<DebugEntranceViewModel>()
    private lateinit var adapter: DebugEntranceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebugEntranceBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
//        binding.entranceRecyclerView.adapter
        binding.entranceRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = DebugEntranceAdapter(requireContext())
        binding.entranceRecyclerView.adapter = adapter
        debugEntranceViewModel.debugEntranceList.observe(viewLifecycleOwner) {
            adapter.dataList = it
        }
    }

    private fun initData() {
        debugEntranceViewModel.initData()
    }
}

class DebugEntrance(val title: String, val owner: String, val operation: () -> Unit) :
    CommonMutiItem {
    override fun getType(): Int = TYPE_ENTRANCE

    companion object {
        const val TYPE_ENTRANCE = 0
    }
}

class DebugEntranceDiffCallback() :
    CommonDiffCallback<CommonMutiItem>()

class DebugEntranceAdapter(val context: Context) :
    RecyclerView.Adapter<CommonViewHolder<ViewBinding>>() {
    val diffCalback = DebugEntranceDiffCallback()

    var dataList: List<DebugEntrance> = listOf()
        set(value) {
            diffCalback.oldList = field
            diffCalback.newList = value
            DiffUtil.calculateDiff(diffCalback).dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<ViewBinding> = when (viewType) {
        DebugEntrance.TYPE_ENTRANCE -> {
            val binding =
                ItemDebugEntranceBinding.inflate(LayoutInflater.from(context), parent, false)
            CommonViewHolder(binding)
        }
        else -> {
            throw IllegalArgumentException("未知参数类型")
        }
    }

    override fun onBindViewHolder(holder: CommonViewHolder<ViewBinding>, position: Int) {
        holder.viewBinding.root.setBackgroundColor(if (position % 2 == 1) Color.LTGRAY else Color.WHITE)
        when (holder.viewBinding) {
            is ItemDebugEntranceBinding -> {
                holder.viewBinding.title.text = dataList[position].title
                holder.viewBinding.owner.text = dataList[position].owner
                holder.viewBinding.root.setOnClickListener { dataList[position].operation() }
            }
            else -> Unit
        }
    }

    override fun getItemCount(): Int = dataList.size
}
