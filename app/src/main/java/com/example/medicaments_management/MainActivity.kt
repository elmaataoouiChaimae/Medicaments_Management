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
        medicamentArrayList.add(Medicament(R.drawable.nurodul,"Nurodol",19,"20 mg", "Ce m??dicament contient de l???ibuprof??ne. Il est indiqu?? chez le nourrisson et l???enfant de 3 mois ?? 12 ans (soit environ 40 kg), dans le traitement de la fi??vre et/ou douleurs telles que : maux de t??te, ??tats grippaux, douleurs dentaires et courbatures."))
        medicamentArrayList.add(Medicament(R.drawable.gaviscon,"Gaviscon",34,"24 sachets", "GAVISCON suspension est un m??dicament indiqu?? dans le traitement des sympt??mes du reflux gastro-oesophagien : r??gurgitations acides, digestion difficile, ??sophagite."))
        medicamentArrayList.add(Medicament(R.drawable.vitaminec,"Vitamine C",25,"20 comprim??s effervescents ?? 4,1 g", "Les comprim??s sains plus vitamine C effervescents ?? la saveur d???orange sanguine ne contiennent pas de sucre ajout??. La vitamine C soutient le syst??me immunitaire.20 comprim??s effervescents ?? 4,1 g."))
        medicamentArrayList.add(Medicament(R.drawable.bronchocist,"Bronchocist",15,"125 ML", "Ce m??dicament est un fluidifiant des s??cr??tions bronchiques.Il facilite leur ??vacuation par la toux et d??gage les bronches en fluidifiant les s??cr??tions.Il est indiqu?? en cas d???affection respiratoire r??cente avec difficult?? d???expectoration (difficult?? ?? rejeter en crachant les s??cr??tions bronchiques)"))
        medicamentArrayList.add(Medicament(R.drawable.rinomicine,"Rinomicine",15,"10 sachets", "Rinomicine?? est indiqu?? dans le traitement symptomatique des : Etats grippaux : rhume, congestion nasale, catarrhe nasal et ??ternuement. Etats f??briles.: fi??vre, courbatures, refroidissement, douleurs mod??r??es ou ??lev??es (telles que maux de t??te)."))
        medicamentArrayList.add(Medicament(R.drawable.docivox,"Sirop Docivox",40,"125 ml", "Sirop naturel aux extraits de Propolis et de plantes, sp??cialement con??u ?? partir d\\'ingr??dients 100 % d\\'origine naturelle, sans conservateur et sans colorant, recommand?? pour tous les types de toux (s??che ou productive/grasse), qui convient ?? toute la famille.Il aide ?? adoucir la gorge irrit??e et facilite la respiration."))
        medicamentArrayList.add(Medicament(R.drawable.doliprane,"Doliprane",15,"1000 mg", "comprim?? effervescent s??cableParac??tamol,Il est indiqu?? en cas de douleur et/ou fi??vre telles que maux de t??te, ??tats grippaux, douleurs dentaires, courbatures, r??gles douloureuses."))
        medicamentArrayList.add(Medicament(R.drawable.alixgene,"ALGIXENE",48,"250 mg","ALGIXENE?? (naprox??ne) est un anti-inflammatoire non st??ro??dien du groupe des arylpropioniques. Il poss??de ??galement un effet analg??sique et antipyr??tique. Son m??canisme d???action est bas?? sur l???inhibition des prostaglandines."))
        medicamentArrayList.add(Medicament(R.drawable.replax,"Replax",97,"40 mg","RELPAX est indiqu?? chez l'adulte dans le traitement de la phase #c??phalalgique de la crise de migraine avec ou sans aura."))
        medicamentArrayList.add(Medicament(R.drawable.actisoufre,"Actisoufre",80,"100ml","ACTISOUFRE spray est un m??dicament utilis?? chez les nourrissons et par toute la famille dans les ??tats inflammatoires chroniques des voies respiratoires sup??rieures (nez et gorge)."))
        medicamentArrayList.add(Medicament(R.drawable.hylocomodo,"HYLO COMOD",150,"50ml","Les gouttes oculaires hydratantes HYLO COMOD?? permettent une (r??)humidification efficace et fiable des yeux secs et douloureux. Gr??ce au renforcement du film lacrymal naturel, les yeux sont prot??g??s durablement contre les effets s??v??res provoqu??s par la s??cheresse oculaire. Les gouttes oculaires hydratantes HYLO COMOD?? sont donc la solution optimale pour traiter les sympt??mes de la s??cheresse oculaire."))
        medicamentArrayList.add(Medicament(R.drawable.topbradex,"Topbradex",36,"50 ml","TOBRADEX (dexam??thasone, tobramycine) est un traitement de 2??me intention dans le traitement local anti-inflammatoire et antibact??rien de l'??il, des infections oculaires avec composante inflammatoire dues ?? des germes sensibles ?? la tobramycine, ?? l'exclusion des conjonctivites infectieuses."))
        medicamentArrayList.add(Medicament(R.drawable.cataflam,"CATAFLAM",85,"50 MG 30 COMP","Diclofenac appartient ?? la classe des AINS (anti-inflammatoires non st??ro??diens). Ces m??dicaments r??duisent les effets des enzymes responsables de douleurs et inflammations."))
        medicamentArrayList.add(Medicament(R.drawable.panadol,"Panadol Extra",20,"10 comprim??","Panadol Extra contient la substance antalgique qu'est le parac??tamol ainsi que de la caf??ine, Panadol Extra est utilis?? pour le traitement de courte dur??e des maux de t??te d'intensit?? l??g??re ?? mod??r??e."))
        medicamentArrayList.add(Medicament(R.drawable.ducray,"ducray-keracnyl",95,"10ml","Ducray Keracnyl Stop Bouton est un soin d'urgence / SOS ?? appliquer directement sur les boutons.\n" + "Sa formule originale ass??chante et purifiante favorise la disparition des boutons."))
        searchList.addAll(medicamentArrayList)
        binding.recyclerView.adapter = MedicamentAdapter(searchList)
    }





}