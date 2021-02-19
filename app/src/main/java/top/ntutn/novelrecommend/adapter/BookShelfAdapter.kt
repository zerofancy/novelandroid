package top.ntutn.novelrecommend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import top.ntutn.commonutil.AppUtil
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.databinding.ItemBookshelfBinding
import top.ntutn.novelrecommend.model.NovelModel

class BookShelfAdapter : RecyclerView.Adapter<CommonViewHolder<ItemBookshelfBinding>>() {
    var bookList: List<NovelModel> = listOf()
        set(value) {
            DiffUtil.calculateDiff(SimpleListDiffCallback(field, value))
                .dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<ItemBookshelfBinding> {
        val binding =
            ItemBookshelfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommonViewHolder<ItemBookshelfBinding>, position: Int) {
        holder.viewBinding.apply {
            bookList[position].cover?.let { coverImageView.setImageURI(it) }
            titleTextView.text = bookList[position].title
            authorTextView.text = AppUtil.getApplicationContext().getString(R.string.template_owner)
                .format(bookList[position].author)
            tagsTextView.text = bookList[position].tags.toString()
        }
    }

    override fun getItemCount(): Int = bookList.size
}