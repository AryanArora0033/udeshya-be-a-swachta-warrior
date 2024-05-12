package com.example.udeshya

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


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
        val my_points=view.findViewById<TextView>(R.id.my_points)
        val listofpoints = mutableListOf<PointModel>()


        val db = FirebaseFirestore.getInstance().collection("users")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    listofpoints.add(
                        PointModel(
                            document.id,
                            document.data.getValue("Points").toString().toInt()
                        )
                    )
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
                listofpoints.sortByDescending { it.point }
                for (item in listofpoints) {
                    Log.d("PointModel", "Name: ${item.name}, Points: ${item.point}")
                }
                val adapter = PointAdapter(listofpoints)
                val recyclerView =
                    requireView().findViewById<RecyclerView>(R.id.leaderboard_recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter

            }




        SharedPreferences.init(this.requireContext())
        val points=SharedPreferences.getInt(PrefConstant.Points_of_User)
        my_points.text=points.toString()

    }

    companion object {

        fun newInstance() = LeaderboardFragment()

        }

    }