package com.example.udeshya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class LeaderboardFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listofpoints= listOf<PointModel>(
           PointModel("Aryan",20),
            PointModel("Anubhav",30),
            PointModel("Abhaay",10),
            PointModel("Akshay",50),
            PointModel("Arjun",0),
        )
        val adapter=PointAdapter(listofpoints)
        val recyclerView=requireView().findViewById<RecyclerView>(R.id.leaderboard_recycler_view)
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.adapter=adapter

    }
    companion object {

        fun newInstance() = LeaderboardFragment()

        }

    }