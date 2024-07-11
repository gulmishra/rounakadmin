package com.tbd.rounakglobal.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.kaopiz.kprogresshud.KProgressHUD
import com.tbd.rounakglobal.R
import com.tbd.rounakglobal.activities.ActAddCat
import com.tbd.rounakglobal.activities.ActAddNews
import com.tbd.rounakglobal.activities.ActAddSubCat
import com.tbd.rounakglobal.activities.ActLists
import com.tbd.rounakglobal.activities.ActLogin
import com.tbd.rounakglobal.controllers.ApiUtils
import com.tbd.rounakglobal.controllers.LoginSession
import com.tbd.rounakglobal.interfaces.APIService
import com.tbd.rounakglobal.models.DashboardCountResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragDashBoard : Fragment() {
    lateinit var login: LoginSession
    var cvPresentCategories : CardView ?= null
    var cvUpcomingCategories : CardView ?= null
    var cvActCat : CardView ?= null
    var cvInActCat : CardView ?= null
    var cvActNews : CardView ?= null
    var cvInActNews : CardView ?= null
    var cvActUsers : CardView ?= null
    var cvInActUsers : CardView ?= null
    var cvAddCat : CardView ?= null
    var cvAddSubCat : CardView ?= null
    var cvAddNews : CardView ?= null

    var tvPresentCat : TextView ?= null
    var tvUpCat : TextView ?= null
    var tvActiveSubCat : TextView ?= null
    var tvInActiveSubCat : TextView ?= null
    var tvActNews : TextView ?= null
    var tvInActNews : TextView ?= null
    var tvActUsers : TextView ?= null
    var tvInActUsers : TextView ?= null
    var loader : KProgressHUD?= null
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_dash_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kotlin.run {
            login = LoginSession()
            login.LoginSession(requireContext())
        }
        cvAddNews = view.findViewById(R.id.cvAddNews)
        cvAddSubCat = view.findViewById(R.id.cvAddSubCat)
        cvAddCat = view.findViewById(R.id.cvAddCat)
        cvPresentCategories = view.findViewById(R.id.cvPresentCategories)
        cvUpcomingCategories = view.findViewById(R.id.cvUpcomingCategories)
        cvActCat = view.findViewById(R.id.cvActCat)
        cvInActCat = view.findViewById(R.id.cvInActCat)
        cvActNews = view.findViewById(R.id.cvActNews)
        cvInActNews = view.findViewById(R.id.cvInActNews)
        cvActUsers = view.findViewById(R.id.cvActUsers)
        cvInActUsers = view.findViewById(R.id.cvInActUsers)

        tvPresentCat = view.findViewById(R.id.tvPresentCat)
        tvUpCat = view.findViewById(R.id.tvUpCat)
        tvActiveSubCat = view.findViewById(R.id.tvActiveSubCat)
        tvInActiveSubCat = view.findViewById(R.id.tvInActiveSubCat)
        tvInActNews = view.findViewById(R.id.tvInActNews)
        tvActNews = view.findViewById(R.id.tvActNews)
        tvActUsers = view.findViewById(R.id.tvActUsers)
        tvInActUsers = view.findViewById(R.id.tvInActUsers)

        loader = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setWindowColor(resources.getColor(R.color.black))
            .setLabel("Please wait")
            .setCancellable(false)
            .setAnimationSpeed(1)
            .setDimAmount(0.6f)

        cvAddCat?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActAddCat::class.java)
            startActivity(intent)
        })

        cvAddNews?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActAddNews::class.java)
            startActivity(intent)
        })

        cvAddSubCat?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActAddSubCat::class.java)
            startActivity(intent)
        })
        cvPresentCategories?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActLists::class.java)
            intent.putExtra("flag", "PresentCat")
            startActivity(intent)
        })

        cvUpcomingCategories?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActLists::class.java)
            intent.putExtra("flag", "UpcomingCat")
            startActivity(intent)
        })

        cvActCat?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActLists::class.java)
            intent.putExtra("flag", "ActCat")
            startActivity(intent)
        })

        cvInActCat?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActLists::class.java)
            intent.putExtra("flag", "InActCat")
            startActivity(intent)
        })

        cvActNews?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActLists::class.java)
            intent.putExtra("flag", "ActNews")
            startActivity(intent)
        })

        cvInActNews?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActLists::class.java)
            intent.putExtra("flag", "InActNews")
            startActivity(intent)
        })

        cvActUsers?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActLists::class.java)
            intent.putExtra("flag", "ActUsers")
            startActivity(intent)
        })

        cvInActUsers?.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), ActLists::class.java)
            intent.putExtra("flag", "InActUsers")
            startActivity(intent)
        })


    }
    override fun onResume() {
        loader?.show()
        getDashBoardCount()
        super.onResume()
    }
    fun getDashBoardCount() {
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        mAPIService.getDashBoardCount()
            .enqueue(object : Callback<DashboardCountResponseModel> {
                override fun onResponse(
                    call: Call<DashboardCountResponseModel>,
                    response: Response<DashboardCountResponseModel>
                ) {
                    if (response.isSuccessful) {
                        loader!!.dismiss()

                        if (response.body()!!.success.equals("1")) {
                            kotlin.run {

                                tvPresentCat?.text = ""+response.body()?.presentCat
                                tvUpCat?.text = ""+response.body()?.UpcomingCat
                                tvActiveSubCat?.text = ""+response.body()?.ActSubCat
                                tvInActiveSubCat?.text = ""+response.body()?.InActSubCat
                                tvActNews?.text = ""+response.body()?.ActNews
                                tvInActNews?.text = ""+response.body()?.InActNews
                                tvActUsers?.text = ""+response.body()?.ActUsers
                                tvInActUsers?.text = ""+response.body()?.InActUsers


                            }
                        } else {

                            Toast.makeText(
                                requireContext(),
                                "Try Again Later.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                }

                override fun onFailure(call: Call<DashboardCountResponseModel>, t: Throwable) {
                    loader!!.dismiss()

                }
            })

    }
}