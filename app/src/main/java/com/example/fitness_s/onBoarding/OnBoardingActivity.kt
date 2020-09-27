package com.example.fitness_s.onBoarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.fitness_s.R
import com.example.fitness_s.ui.MainActivity
import kotlinx.android.synthetic.main.page_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {
    private val list = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_on_boarding)
        setupViewpager()
        setupListener()
    }

    private fun setupListener() {
        OnviewPage.setOnPageChangeListener(object  : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                if (checkToPage(position)){
                    Btngo.text = "Go to Map"
                } else {
                    Btngo.text = "Next"
                }
            }
        })
        Btngo.setOnClickListener {
            if (checkToPage(OnviewPage.currentItem)) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                OnviewPage.currentItem += 1
            }
        }
    }

    private fun checkToPage(position : Int) = position == list.size - 1

    private fun setupViewpager() {
        val adapter = OnBoardingAdapter(supportFragmentManager)
        OnviewPage.adapter = adapter
        list.add(OnBoardingFragment.getInstance(DataOnBoarding(R.drawable.sport2,getString(R.string.monitor),"")))
        list.add(OnBoardingFragment.getInstance(DataOnBoarding(R.drawable.ic_gym, getString(R.string.blood), "")))
        list.add(OnBoardingFragment.getInstance(DataOnBoarding(R.drawable.nbkn, getString(R.string.life), "")))
        adapter.update(list)
        OnTabLT.setupWithViewPager(OnviewPage)
    }

}