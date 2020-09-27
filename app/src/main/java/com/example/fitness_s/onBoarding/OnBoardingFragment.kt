package com.example.fitness_s.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness_s.R
import kotlinx.android.synthetic.main.view_on_boarding.*

class OnBoardingFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_on_boarding,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        val data = arguments?.get(DATA_ID) as DataOnBoarding
        tvt1.text = data.firstText
        tvt2.text = data.secondText
        image.setImageResource(data.img)
    }

    companion object{
        const val DATA_ID = "DATA_ID"

        fun getInstance(data: DataOnBoarding):OnBoardingFragment{
            val fragment = OnBoardingFragment()
            val bundle = Bundle()
            bundle.putParcelable(DATA_ID,data)
            fragment.arguments = bundle
            return fragment
        }
    }


}