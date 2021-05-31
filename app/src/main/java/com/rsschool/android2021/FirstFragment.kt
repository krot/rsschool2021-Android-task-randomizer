package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        var minInput = view.findViewById<EditText>(R.id.min_value);
        var maxInput = view.findViewById<EditText>(R.id.max_value);

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
        // TODO: val max = ...


        generateButton?.setOnClickListener {

            if (minInput.text.isEmpty()) {
                minInput.setError("Cannot be blank");
            }
            if (maxInput.text.isEmpty()) {
                maxInput.setError("Cannot be blank");
            }

            if (!minInput.text.isEmpty() && !(maxInput.text.isEmpty())) {

                val min = minInput.text.toString().toLongOrNull()
                val max = maxInput.text.toString().toLongOrNull()

                if (min != null && max != null) {
                    if (max > Int.MAX_VALUE) {
                        maxInput.setError("Enter value [0.." + Int.MAX_VALUE.toString() + ']');
                    } else if (min > max) {
                        minInput.setError("Min must be smaller than Max");
                    } else {
                        (getActivity() as MainActivity).openSecond(min.toInt(), max.toInt())
                    }
                }
                else {
                    if (min == null) {
                        minInput.setError("Incorrect value");
                    }
                    if (max == null) {
                        maxInput.setError("Incorrect value");
                    }
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback {
            activity?.finish()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}