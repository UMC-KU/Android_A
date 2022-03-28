package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import com.example.flo.databinding.FragmentAlbumBinding


class AlbumFragment :Fragment(){
    lateinit var binding:FragmentAlbumBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding= FragmentAlbumBinding.inflate(inflater,container,false)
        binding.albumMoreGoBackIb.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()
            Toast.makeText(activity,"눌림",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }



}