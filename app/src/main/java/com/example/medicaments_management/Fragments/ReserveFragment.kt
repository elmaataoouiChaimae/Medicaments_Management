package com.example.medicaments_management.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.medicaments_management.Data.Reservation
import com.example.medicaments_management.MainActivity
import com.example.medicaments_management.R
import com.example.medicaments_management.databinding.FragmentReserveBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReserveFragment : Fragment() {
     var binding:FragmentReserveBinding?=null
    lateinit var databaseReference: DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_reserve,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data=arguments
        binding!!.produit.text=data!!.getString("medicament_nom").toString()
        binding!!.confirm.setOnClickListener{
            if(binding!!.name.text.isEmpty() || binding!!.phone.text.isEmpty() || binding!!.adresse.text.isEmpty() || binding!!.qte.text.isEmpty()){
                var view1=View.inflate(context,R.layout.alert_empty_field,null)
                val builder= context?.let { it1 -> AlertDialog.Builder(it1) }
                builder?.setView(view1)
                val dialogue= builder?.create()
                dialogue?.show()
                if (dialogue != null) {
                    dialogue.window?.setBackgroundDrawableResource(android.R.color.transparent)
                }
                val btn=view1.findViewById<Button>(R.id.btn_confirm)
                btn.setOnClickListener{
                    dialogue?.dismiss()
                }
            }
            else{
                if(binding!!.phone.text.length<10){
                    var view2=View.inflate(context,R.layout.alert_phone_wrong,null)
                    val builder= context?.let { it1 -> AlertDialog.Builder(it1) }
                    builder?.setView(view2)
                    val dialogue= builder?.create()
                    dialogue?.show()
                    if (dialogue != null) {
                        dialogue.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    }
                    val btn=view2.findViewById<Button>(R.id.btn_confirm)
                    btn.setOnClickListener{
                        dialogue?.dismiss()
                    }
                }
                else{
                    databaseReference= FirebaseDatabase.getInstance().getReference("reservations")
                    val reservation= Reservation(binding!!.name.text.toString(), binding!!.phone.text.toString(),binding!!.adresse.text.toString(),binding!!.qte.text.toString().toInt(), data!!.getString("medicament").toString())
                    databaseReference.child(binding!!.name.text.toString()).setValue(reservation).addOnSuccessListener {view ->
                        binding!!.name.text.clear()
                        binding!!.phone.text.clear()
                        binding!!.adresse.text.clear()
                        binding!!.qte.text.clear()

                        var view3=View.inflate(context,R.layout.alert_succes,null)
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
        binding!!.homeRedirect.setOnClickListener{
            Intent(context, MainActivity::class.java).also {
                startActivity(it)
            }
        }
        super.onViewCreated(view, savedInstanceState)

    }

}