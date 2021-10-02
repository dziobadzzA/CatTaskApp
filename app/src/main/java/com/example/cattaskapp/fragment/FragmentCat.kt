package com.example.cattaskapp.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.api.load
import com.example.cattaskapp.R
import com.example.cattaskapp.databinding.ActivityFragmentCatBinding
import com.example.cattaskapp.databinding.ActivityFragmentCatBinding.inflate


class FragmentCat : Fragment() {

    private var _binding: ActivityFragmentCatBinding? = null
    private val binding get() = _binding!!
    private var imageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = inflate(inflater, container, false)

        binding.toolImage.setOnClickListener {
            binding.toolImage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_in))
        }

        binding.imageButton.setOnClickListener {

            val permissionStatus = activity?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.WRITE_EXTERNAL_STORAGE) }

            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                activity?.let { it1 -> ActivityCompat.requestPermissions(it1, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0) }
            }

            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                context?.let { it1 -> SaveImage(it1).execute(imageUrl) }
                Toast.makeText(context, this.requireArguments().getString(context?.resources?.getString(R.string.save)), Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(context, this.requireArguments().getString(context?.resources?.getString(R.string.request)), Toast.LENGTH_LONG).show()

        }

        imageUrl = this.requireArguments().getString(context?.resources?.getString(R.string.keyBundle))
        if (!imageUrl.isNullOrEmpty())
            binding.toolImage.load(imageUrl)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}