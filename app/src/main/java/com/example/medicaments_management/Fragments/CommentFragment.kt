package com.example.medicaments_management.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.medicaments_management.Data.Commentaire
import com.example.medicaments_management.R
import com.example.medicaments_management.databinding.FragmentCommentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class CommentFragment : Fragment() {
    var binding: FragmentCommentBinding?=null
    lateinit var databaseReference: DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_comment,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun init(){
        binding!!.envoyer.setOnClickListener{
            if(binding!!.nom.text.isEmpty()|| binding!!.message.text.isEmpty() || binding!!.email.text.isEmpty()){
                var view2=View.inflate(context,R.layout.alert_empty_field,null)
                val builder= context?.let { it1 -> AlertDialog.Builder(it1) }
                if (builder != null) {
                    builder.setView(view2)
                }
                val dialogue= builder?.create()
                if (dialogue != null) {
                    dialogue.show()
                }
                if (dialogue != null) {
                    dialogue.window?.setBackgroundDrawableResource(android.R.color.transparent)
                }
                val btn=view2.findViewById<Button>(R.id.btn_confirm)
                btn.setOnClickListener{
                    if (dialogue != null) {
                        dialogue.dismiss()
                    }
                }
            }
            else{
                databaseReference= FirebaseDatabase.getInstance().getReference("commentaires")
                val comment= Commentaire(binding!!.nom.text.toString(),binding!!.message.text.toString(), binding!!.email.text.toString())
                databaseReference.child(binding!!.nom.text.toString()).setValue(comment).addOnSuccessListener {view ->
                    binding!!.nom.text.clear()
                    binding!!.email.text.clear()
                    binding!!.message.text.clear()
                    var view3=View.inflate(context,R.layout.alert_succes_comment,null)
                    val builder= context?.let { it1 -> AlertDialog.Builder(it1) }
                    if (builder != null) {
                        builder.setView(view3)
                    }
                    val dialogue= builder?.create()
                    if (dialogue != null) {
                        dialogue.show()
                    }
                    if (dialogue != null) {
                        dialogue.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    }
                    val btn=view3.findViewById<Button>(R.id.btn_confirm)
                    btn.setOnClickListener{
                        if (dialogue != null) {
                            dialogue.dismiss()
                        }
                    }
                }.addOnFailureListener{
                    Toast.makeText(context,"Un probléme se produit essayez une autre fois",
                        Toast.LENGTH_LONG).show()
                }
            }

        }
    }



}