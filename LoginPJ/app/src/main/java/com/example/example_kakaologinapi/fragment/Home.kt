package com.example.example_kakaologinapi.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.example_kakaologinapi.databinding.HomeBinding
import com.example.example_kakaologinapi.retrofit.DTO.List
import com.example.example_kakaologinapi.viewModel.ApiViewModel
import com.example.example_kakaologinapi.viewModel.LoginViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class Home : Fragment() {
    private var _binding : HomeBinding ?= null
    private val binding get() = _binding !!
    private val apiViewModel by lazy { ApiViewModel(requireActivity().application) }
    private val mapView by lazy{MapView(requireActivity())}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apiViewModel=apiViewModel
        binding.lifecycleOwner=this
        val markerList= ArrayList<MapPOIItem>()
        apiViewModel.restaurantList.observe(viewLifecycleOwner, Observer {
            for (data in it){
                markerList.add(apiViewModel.makeMarker(data))
            }
            Log.d("test",it.toString())
            mapView.addPOIItems(markerList.toArray(arrayOfNulls(markerList.size)))
        })
        binding.mapView.addView(mapView)
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.5034686, 129.3804962), true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)

    }
}