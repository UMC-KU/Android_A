package com.example.flo

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(album: Album)
    }

    private lateinit var nItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        nItemClickListener = itemClickListener
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged() //데이터 바뀌었다는 걸 알려줌
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        //사용하고자 하는 아이템 뷰 객체 만듦
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        //데이터 바인딩 해주는 작업
        holder.bind(albumList[position])
        //클릭 이벤트는 보통 여기서, 포지션 정보 갖고있어서
        holder.itemView.setOnClickListener{
            nItemClickListener.onItemClick(albumList[position])
        }
    }

    //리사이클러뷰가 마지막에 어디인지를 알려줌
    override fun getItemCount(): Int = albumList.size

    //아이템뷰 날라가지 않도록 담는 그릇
    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemAlbumTitleTv.text=album.title
            binding.itemAlbumSingerTv.text=album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)

        }
    }

}