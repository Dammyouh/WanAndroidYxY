package com.exam.home.wanandroid.ui.nodecategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exam.home.wanandroid.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.item_home_article.view.title
import kotlinx.android.synthetic.main.item_node_artical.view.*

class NodeCategoryFragment : Fragment() {

    private lateinit var nodeVModel: NodeCategoryVModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        nodeVModel = ViewModelProviders.of(this).get(NodeCategoryVModel::class.java)
        initListener()
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    private fun initListener() {
        nodeVModel.hasNodeList.observe(this, Observer {
            if (it == true) {
                initRV(nodeVModel.nodeList)
            }
        })
        nodeVModel.getNodeTree()
    }

    private fun initRV(nodeList: List<NodeListEntity>) {
        iv_node_category.layoutManager = LinearLayoutManager(context)
        iv_node_category.adapter = object : RecyclerView.Adapter<Holder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_node_artical, parent, false)
                return Holder(view)
            }

            override fun getItemCount(): Int {
                return nodeList.size
            }

            override fun onBindViewHolder(holder: Holder, position: Int) {
                holder.itemView.title.text = nodeList[position].name
                var str = ""
                nodeList[position].children.map {
                    str += "${it.name}； "
                }

                holder.itemView.detail.text = str

                holder.itemView.setOnClickListener {
                    startActivity(
                        NodeDetailActivity.goDetailActivity(
                            context!!, nodeList[position].children as
                                    ArrayList<NodeListEntity>, nodeList[position].name ?: ""
                        )
                    )
                }
            }
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}