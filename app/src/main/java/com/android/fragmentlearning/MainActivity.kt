package com.android.fragmentlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonNext = findViewById<Button>(R.id.button_next)
        val buttonBack = findViewById<Button>(R.id.btn_back)

        var count = 0;
        onPageChanged(FirstFragment(), "firstFragment")

        buttonNext.setOnClickListener {
            when (count) {
                0 -> onPageChanged(FirstFragment(), "firstFragment")
                1 -> onPageChanged(SecondFragment(), "secondFragment")
                2 -> {
                    onPageChanged(ThirdFragment(), "thirdFragment")
                    count = -1;
                }
            }
            count++
        }

        buttonBack.setOnClickListener {
            supportFragmentManager.popBackStack(
                "secondFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE

                //POP_BACK_STACK_INCLUSIVE
                /*
                Flag for popBackStack(java.lang.String, int) and popBackStack(int, int): If set,
                and the name or ID of a back stack entry has been supplied, then all matching entries will be
                consumed until one that doesn't match is found or the bottom of the stack is reached. Otherwise,
                all entries up to but not including that entry will be removed.
                 */

                // Difference between 0 and POP_BACK_STACK_INCLUSIVE is the 2nd one removes
                //all fragment and given fragment given name with popBackStack will removed
                // with 0 given name fragment will be saved

            )
        }
    }

    private fun onPageChanged(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)


            .addToBackStack(name) // add the transaction to the backstack with name or null that we can pop back to that
            // fragment with given name. Moreover if we do not call addToBackStack on transaction, the removed fragment is destroyed() when
            //transaction is committed and user cant navigate back to it. When we use addToBackStack() on our transaction removed fragment
            // only stopped and is later resumed when user navigates back.


            .setReorderingAllowed(true)
            //this will removes redundant operations like if if we add FragmentA to container and we replace it FragmentB
            // in one transaction only FragmentB will be added


            .setPrimaryNavigationFragment(fragment)
            /*
                Consider the navigation structure as a series of layers with the activty
                as the outermost layer,  which each layer of child fragments are wrapped
                underneath. Each layer must have a single primary navigation fragment.
                When the Back stack occurs, the innermost controls navigation behaviour.
                Once the innermost layer has no more fragment transaction from which to
                pop back, control returns to next layer out and this process repeats until
                you reach the activity
                We have to call setPrimaryNavigationFragment() on each transaction so each
                fragment will have primary navigation. and we can control our fragment
                navigation on innermost fragment, actually we can only navigate to other
                fragments from last fragment so it has to have primary navigation
             */
            .commit()
    }
}