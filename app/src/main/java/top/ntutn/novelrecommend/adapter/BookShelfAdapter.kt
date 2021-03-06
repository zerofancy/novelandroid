package top.ntutn.novelrecommend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import top.ntutn.commonutil.AppUtil
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.databinding.ItemBookshelfBinding
import top.ntutn.novelrecommend.databinding.ItemBookshelfFooterBinding
import top.ntutn.novelrecommend.databinding.ItemBookshelfTitleBinding
import top.ntutn.novelrecommend.model.NovelModel

class BookShelfAdapter : RecyclerView.Adapter<CommonViewHolder<ViewBinding>>() {
    enum class ItemType {
        TITLE,
        BOOK,
        FOOTER
    }

    var bookList: List<NovelModel> = listOf()
        set(value) {
            DiffUtil.calculateDiff(SimpleListDiffCallback(field, value))
                .dispatchUpdatesTo(this)
            field = value.toList()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<ViewBinding> {
        val binding = when (viewType) {
            ItemType.TITLE.ordinal -> ItemBookshelfTitleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ItemType.FOOTER.ordinal -> ItemBookshelfFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            else -> ItemBookshelfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
        return CommonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommonViewHolder<ViewBinding>, rawPosition: Int) {
        val position = rawPosition - 1
        when (getItemViewType(rawPosition)) {
            ItemType.TITLE.ordinal -> Unit
            ItemType.FOOTER.ordinal -> Unit
            else -> (holder.viewBinding as ItemBookshelfBinding).apply {
                bookList[position].cover?.let { coverImageView.setImageURI(it) }
                titleTextView.text = bookList[position].title
                authorTextView.text =
                    AppUtil.getApplicationContext().getString(R.string.template_owner)
                        .format(bookList[position].author)
                tagsTextView.text = bookList[position].tags.toString()
            }
        }
    }

    override fun getItemCount(): Int = bookList.size + 2

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> ItemType.TITLE.ordinal
        bookList.size + 1 -> ItemType.FOOTER.ordinal
        else -> ItemType.BOOK.ordinal
    }
}