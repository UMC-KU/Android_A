package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import java.util.zip.Inflater

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding

    //activity : onCreate vs fragment : onCreateView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        //fragment_album에서 back누르면 다시 HomeFragment로 이동
        binding.albumBackIv.setOnClickListener{
        (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        //toast message 띄우기
        binding.songLilacLayout.setOnClickListener{
            Toast.makeText(activity,"LILAC",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}