package com.exam.home.wanandroid.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exam.home.wanandroid.R
import com.exam.home.wanandroid.WebActivity
import com.exam.home.wanandroid.customview.BaseRvAdapter
import kotlinx.android.synthetic.main.item_home_article.view.*

/**
 * Created by Yangxy on 2019-12-30
 * description -- 首页文章Adapter & Holder
 */
class HomeArticleAdapter : BaseRvAdapter<HomeArticleAdapter.ArticleHolder>() {


    override fun _getItemCount(): Int {
        return articles.size
    }

    override fun _onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_article, parent, false)
        return ArticleHolder(view)
    }

    override fun _onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bindHolder(articles[position])
    }

    private var articles: List<Datas> = ArrayList()

    fun updateDatas(articles: List<Datas>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    inner class ArticleHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        fun bindHolder(article: Datas) {
            itemView.apply {
                tv_tag.text = article.chapterName
                title.text = article.title
                if (article.author.isNullOrEmpty()) {
                    author.visibility = View.GONE
                } else {
                    author.text = article.author
                    author.visibility = View.VISIBLE
                }

                val time =
                    if (article.niceDate.contains(" ")) {
                        article.niceDate.split(" ")[0]
                    } else {
                        article.niceDate
                    }
                tv_time.text = time
                setOnClickListener {
                    val intent = Intent(itemView.context, WebActivity::class.java)
                    intent.putExtra("pageUrl", article.link)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }
}