package com.dingtao.rrmmp.activity

import androidx.fragment.app.Fragment
import android.widget.RadioGroup

import com.alibaba.android.arouter.facade.annotation.Route
import com.dingtao.common.util.Constant
import com.dingtao.rrmmp.R
import com.dingtao.common.core.WDActivity
import com.dingtao.rrmmp.fragment.CircleFragment
import com.dingtao.rrmmp.fragment.HomeFragment
import com.dingtao.rrmmp.fragment.MeFragment
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = Constant.ACTIVITY_URL_MAIN)
class MainActivity : WDActivity(), RadioGroup.OnCheckedChangeListener {

    lateinit internal var homeFragment: HomeFragment
    lateinit internal var circleFragment: CircleFragment
    lateinit internal var meFragment: MeFragment

    override val layoutId: Int = R.layout.activity_main

    lateinit var currentFragment: Fragment

    override fun initView() {
        bottomMenu!!.setOnCheckedChangeListener(this)

        homeFragment = HomeFragment()
        circleFragment = CircleFragment()
        meFragment = MeFragment()

        currentFragment = homeFragment
        val tx = supportFragmentManager.beginTransaction()
        tx.add(R.id.container, homeFragment)
                .show(homeFragment).commit()

    }

    override fun destoryData() {

    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        if (checkedId == R.id.home_btn) {
            showFragment(homeFragment)
        } else if (checkedId == R.id.circle_btn) {
            showFragment(circleFragment)
        } else if (checkedId == R.id.me_btn) {
            showFragment(meFragment)
        }
    }

    /**
     * 展示Fragment
     */
    private fun showFragment(fragment: Fragment) {
        if (currentFragment !== fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.hide(currentFragment)
            currentFragment = fragment
            if (!fragment.isAdded) {
                transaction.add(R.id.container, fragment).show(fragment).commit()
            } else {
                transaction.show(fragment).commit()
            }
        }
    }
}
