package com.example.medicaments_management

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicaments_management.Adapter.MedicamentAdapter
import com.example.medicaments_management.Data.Medicament
import com.example.medicaments_management.Fragments.CommentFragment
import com.example.medicaments_management.Fragments.MessageFragment
import com.example.medicaments_management.Fragments.ReserveFragment
import com.example.medicaments_management.Fragments.SettingsFragment
import com.example.medicaments_management.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    private lateinit var medicamentArrayList: ArrayList<Medicament>
    private lateinit var myAdapter: MedicamentAdapter
    private lateinit var searchList: ArrayList<Medicament>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(
                this, R.layout.activity_main)
        toggle= ActionBarDrawerToggle(this,binding.drawerlayout,R.string.open,R.string.close)
        binding.drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding!!.navView.setNavigationItemSelectedListener {
            it.isChecked=true
            when(it.itemId){
                R.id.nav_home-> {
                    Intent(this,MainActivity::class.java).also {view ->
                        startActivity(view)
                    }
                }
                R.id.nav_contact-> replaceFragment(MessageFragment(),it.title.toString())
                R.id.nav_sync-> Toast.makeText(applicationContext,"Synchronisation", Toast.LENGTH_SHORT).show()
                R.id.nav_setting-> replaceFragment(SettingsFragment(),it.title.toString())
                R.id.nav_share-> {

                    val myIntent: Intent = Intent(Intent.ACTION_SEND)
                    myIntent.data= Uri.parse("mailto:")
                    myIntent.type="text/plain"
                    myIntent.putExtra(Intent.EXTRA_TEXT,"Medca application pour tous les medicaments\n https://medicament.ma/")

                    try {
                        startActivity(Intent.createChooser(myIntent,"Share the app with"))
                    }catch (e:java.lang.Exception){

                    }
                }
                R.id.nav_rate_us-> replaceFragment(CommentFragment(),it.title.toString())

            }
            true
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        medicamentArrayList = arrayListOf<Medicament>()
        searchList = arrayListOf<Medicament>()
        getData()
        binding.search.clearFocus()
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    medicamentArrayList.forEach{
                        if (it.nom?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
                            searchList.add(it)
                        }
                    }
                    binding.recyclerView.adapter!!.notifyDataSetChanged()

                } else {
                    searchList.clear()
                    searchList.addAll(medicamentArrayList)
                    binding.recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
        myAdapter = MedicamentAdapter(searchList)
        binding.recyclerView.adapter = myAdapter
        // lorsqu'il choisit un medicament
        myAdapter.onItemClick={
            val fragment=ReserveFragment()
            val bundle=Bundle()
            bundle.putString("medicament_nom",it.nom)
            fragment.arguments=bundle
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commit()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
      private fun replaceFragment(fragment: Fragment, title:String){
              val fragmentManager=supportFragmentManager
              val fragmentTransaction=fragmentManager.beginTransaction()
              fragmentTransaction.replace(R.id.frameLayout,fragment).commit()
              binding.drawerlayout.closeDrawers()
              setTitle(title)
    }

    private fun getData(){
        medicamentArrayList.add(Medicament(R.drawable.nurodul,"Nurodol",19,"20 mg", "Ce médicament contient de l’ibuprofène. Il est indiqué chez le nourrisson et l’enfant de 3 mois à 12 ans (soit environ 40 kg), dans le traitement de la fièvre et/ou douleurs telles que : maux de tête, états grippaux, douleurs dentaires et courbatures."))
        medicamentArrayList.add(Medicament(R.drawable.gaviscon,"Gaviscon",34,"24 sachets", "GAVISCON suspension est un médicament indiqué dans le traitement des symptômes du reflux gastro-oesophagien : régurgitations acides, digestion difficile, œsophagite."))
        medicamentArrayList.add(Medicament(R.drawable.vitaminec,"Vitamine C",25,"20 comprimés effervescents à 4,1 g", "Les comprimés sains plus vitamine C effervescents à la saveur d’orange sanguine ne contiennent pas de sucre ajouté. La vitamine C soutient le système immunitaire.20 comprimés effervescents à 4,1 g."))
        medicamentArrayList.add(Medicament(R.drawable.bronchocist,"Bronchocist",15,"125 ML", "Ce médicament est un fluidifiant des sécrétions bronchiques.Il facilite leur évacuation par la toux et dégage les bronches en fluidifiant les sécrétions.Il est indiqué en cas d’affection respiratoire récente avec difficulté d’expectoration (difficulté à rejeter en crachant les sécrétions bronchiques)"))
        medicamentArrayList.add(Medicament(R.drawable.rinomicine,"Rinomicine",15,"10 sachets", "Rinomicine® est indiqué dans le traitement symptomatique des : Etats grippaux : rhume, congestion nasale, catarrhe nasal et éternuement. Etats fébriles.: fièvre, courbatures, refroidissement, douleurs modérées ou élevées (telles que maux de tête)."))
        medicamentArrayList.add(Medicament(R.drawable.docivox,"Sirop Docivox",40,"125 ml", "Sirop naturel aux extraits de Propolis et de plantes, spécialement conçu à partir d\\'ingrédients 100 % d\\'origine naturelle, sans conservateur et sans colorant, recommandé pour tous les types de toux (sèche ou productive/grasse), qui convient à toute la famille.Il aide à adoucir la gorge irritée et facilite la respiration."))
        medicamentArrayList.add(Medicament(R.drawable.doliprane,"Doliprane",15,"1000 mg", "comprimé effervescent sécableParacétamol,Il est indiqué en cas de douleur et/ou fièvre telles que maux de tête, états grippaux, douleurs dentaires, courbatures, règles douloureuses."))
        medicamentArrayList.add(Medicament(R.drawable.alixgene,"ALGIXENE",48,"250 mg","ALGIXENE® (naproxène) est un anti-inflammatoire non stéroïdien du groupe des arylpropioniques. Il possède également un effet analgésique et antipyrétique. Son mécanisme d’action est basé sur l’inhibition des prostaglandines."))
        medicamentArrayList.add(Medicament(R.drawable.replax,"Replax",97,"40 mg","RELPAX est indiqué chez l'adulte dans le traitement de la phase #céphalalgique de la crise de migraine avec ou sans aura."))
        medicamentArrayList.add(Medicament(R.drawable.actisoufre,"Actisoufre",80,"100ml","ACTISOUFRE spray est un médicament utilisé chez les nourrissons et par toute la famille dans les états inflammatoires chroniques des voies respiratoires supérieures (nez et gorge)."))
        medicamentArrayList.add(Medicament(R.drawable.hylocomodo,"HYLO COMOD",150,"50ml","Les gouttes oculaires hydratantes HYLO COMOD® permettent une (ré)humidification efficace et fiable des yeux secs et douloureux. Grâce au renforcement du film lacrymal naturel, les yeux sont protégés durablement contre les effets sévères provoqués par la sécheresse oculaire. Les gouttes oculaires hydratantes HYLO COMOD® sont donc la solution optimale pour traiter les symptômes de la sécheresse oculaire."))
        medicamentArrayList.add(Medicament(R.drawable.topbradex,"Topbradex",36,"50 ml","TOBRADEX (dexaméthasone, tobramycine) est un traitement de 2ème intention dans le traitement local anti-inflammatoire et antibactérien de l'œil, des infections oculaires avec composante inflammatoire dues à des germes sensibles à la tobramycine, à l'exclusion des conjonctivites infectieuses."))
        medicamentArrayList.add(Medicament(R.drawable.cataflam,"CATAFLAM",85,"50 MG 30 COMP","Diclofenac appartient à la classe des AINS (anti-inflammatoires non stéroïdiens). Ces médicaments réduisent les effets des enzymes responsables de douleurs et inflammations."))
        medicamentArrayList.add(Medicament(R.drawable.panadol,"Panadol Extra",20,"10 comprimé","Panadol Extra contient la substance antalgique qu'est le paracétamol ainsi que de la caféine, Panadol Extra est utilisé pour le traitement de courte durée des maux de tête d'intensité légère à modérée."))
        medicamentArrayList.add(Medicament(R.drawable.ducray,"ducray-keracnyl",95,"10ml","Ducray Keracnyl Stop Bouton est un soin d'urgence / SOS à appliquer directement sur les boutons.\n" + "Sa formule originale asséchante et purifiante favorise la disparition des boutons."))
        searchList.addAll(medicamentArrayList)
        binding.recyclerView.adapter = MedicamentAdapter(searchList)
    }





}