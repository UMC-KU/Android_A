package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList:ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>(){
    //인터페이스
    interface MyItemClickListener{
        //앨범에 있는 정보를 그대로 넘기고 싶을 때 사용
        fun onItemClick(album: Album)
        //앨범 삭제할 경우
//        fun onRemoveAlbum(position: Int)
    }

    //외부에서 넘겨받은 listener객체를 adapter에서 사용할 수 있도록 변수에 저장
    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }
    
    //item을 추가하거나 삭제
    fun addItem(album: Album){
        albumList.add(album) //리스트에 album 추가
        notifyDataSetChanged() //recyclerView는 data가 바뀐 것을 모르기 때문에 반드시 알려주기
    }
    fun removeItem(position: Int){
        albumList.removeAt(position) //리스트에 album 추가
        notifyDataSetChanged() //recyclerView는 data가 바뀐 것을 모르기 때문에 반드시 알려주기
    }
    
    //ViewHolder를 생성할 때
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        //itemView 객체를 만들고 ViewHolder에 던지기
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        
        return ViewHolder(binding)
    }

    //ViewHolder에 data를 binding할 때마다 호출 -> 사용자가 화면을 스크롤 할 때마다 호출
    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        //position : recyclerView에서 index역할
        holder.bind(albumList[position])
        
        //position값을 가진 onBindViewHolder에서 일반적으로 click이벤트 수행
        holder.itemView.setOnClickListener{
            //여기에만 코드를 작성하면 Adapter클래스안에서만 유효
            //but P: activity나 fragment(Adapter 외부)에서 클릭이벤트를 처리하고 싶은 경우가 있음
            //S: clickListener이벤트를 수행할 인터페이스를 따로 생성해야함 (recyclerView는 이벤트 내장X)
            mItemClickListener.onItemClick(albumList[position])
        }

        //앨범 삭제할 경우 -> HomeFragment에서 호출 : 앨범타이틀을 클릭하면 앨범 삭제됨
//        holder.binding.itemAlbumTitleTv.setOnClickListener{mItemClickListener.onRemoveAlbum(position)}
    }

    //dataset의 크기를 알려줌 : recyclerView의 마지막을 알려줌
    override fun getItemCount(): Int = albumList.size

    //ViewHolder 만들기 : itemView객체를 매개변수로 받아 묶기
    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album:Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}