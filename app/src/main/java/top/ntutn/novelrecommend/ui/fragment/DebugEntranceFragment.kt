package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import top.ntutn.novelrecommend.databinding.FragmentDebugEntranceBinding
import top.ntutn.novelrecommend.ui.base.BaseFragment

class DebugEntranceFragment : BaseFragment() {
    private lateinit var binding:FragmentDebugEntranceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebugEntranceBinding.inflate(layoutInflater,container,false)
        initView()
        return binding.root
    }

    private fun initView() {
//        binding.entranceRecyclerView.adapter
    }
}

data class DebugEntrance(val title: String, val owner: String, val operation: () -> Unit)

class DebugEntranceAdapter:RecyclerView.Adapter<DebugEntranceAdapter.ViewHolde>() {
    data class ViewHolde(val view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolde {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolde, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}