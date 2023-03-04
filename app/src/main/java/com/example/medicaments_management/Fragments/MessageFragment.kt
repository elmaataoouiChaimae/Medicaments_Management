package com.example.medicaments_management.Fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.medicaments_management.R
import com.example.medicaments_management.databinding.FragmentMessageBinding
class MessageFragment : Fragment() {
    var binding: FragmentMessageBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_message,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding!!.phone.setOnClickListener{
            val number = Uri.parse("tel:+212 650 65 78 32")
            val callIntent= Intent(Intent.ACTION_DIAL,number)
            startActivity(callIntent)
        }
        binding!!.email.setOnClickListener{
            val myIntent:Intent=Intent(Intent.ACTION_SEND)
            myIntent.data=Uri.parse("mailto:")
            myIntent.type="text/plain"
            myIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("chaimae.elmaataoui2002@gmail.com"))
            myIntent.putExtra(Intent.EXTRA_SUBJECT,"Demade d'information")
            myIntent.putExtra(Intent.EXTRA_TEXT,"Information")
            try {
                startActivity(Intent(myIntent))
            }catch (e:java.lang.Exception){

            }

        }
        binding!!.location.setOnClickListener{
            val locat = Uri.parse("geo:37.422219,-122.08364?z=14")
            val locationIntent = Intent(Intent.ACTION_VIEW,locat)
            startActivity(locationIntent)
        }
        binding!!.website.setOnClickListener{
            val web = Uri.parse("https://medicament.ma/")
            val webIntent = Intent(Intent.ACTION_VIEW,web)
            startActivity(webIntent)
        }
        super.onViewCreated(view, savedInstanceState)
    }


}