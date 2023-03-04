package com.example.medicaments_management.Fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.medicaments_management.R
import com.example.medicaments_management.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    var binding: FragmentSettingsBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding!!.politique.setOnClickListener{
            val web = Uri.parse("https://devstreams.blogspot.com/2020/05/privacy-policy.html?m=1")
            val webIntent = Intent(Intent.ACTION_VIEW,web)
            startActivity(webIntent)
        }
        binding!!.historique.setOnClickListener{
            val builder= AlertDialog.Builder(requireContext());
            builder.setTitle("Confirmation")
            builder.setMessage("Vous voulez supprimer l\'historique")
            builder.setPositiveButton("Oui"){dialogueInterface ,id->
                Toast.makeText(context,"Lhistorique a bien été supprimer",Toast.LENGTH_SHORT).show()

            }
            builder.setNegativeButton("Non"){dialogueInterface ,id->
                dialogueInterface.dismiss()

            }
            builder.setNeutralButton("Annuler"){dialogueInterface ,id->
                dialogueInterface.dismiss()
            }
            val alertDialog:AlertDialog=builder.create()
            alertDialog.show()
        }
        super.onViewCreated(view, savedInstanceState)
    }

}