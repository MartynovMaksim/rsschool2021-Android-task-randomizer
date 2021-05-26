package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private lateinit var communicator: Communicator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = activity as Communicator

    }

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

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
//        previousResult?.text = "Previous result: ${result.toString()}"
        previousResult?.text = getString(R.string.prev_result, result.toString())

        var min: Int? = null
        view.findViewById<EditText>(R.id.min_value).doAfterTextChanged {
            view.findViewById<EditText>(R.id.min_value).text.toString().toIntOrNull()?.let {
                min = it
            }
        }
        var max: Int? = null
        view.findViewById<EditText>(R.id.max_value).doAfterTextChanged {
            view.findViewById<EditText>(R.id.max_value).text.toString().toIntOrNull()?.let {
                max = it
            }
        }


        generateButton?.setOnClickListener {
            if (isValidInfo(min, max)) {
                min?.let { it1 -> max?.let { it2 -> communicator.openSecondFragment(it1, it2) } }
            } else {
                Toast.makeText(activity, R.string.toast_message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isValidInfo(min: Int?, max: Int?): Boolean {
        return when {
            min != null && max != null &&
                    min < max && min <= Int.MAX_VALUE &&
                    max <= Int.MAX_VALUE -> true
            else -> false
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