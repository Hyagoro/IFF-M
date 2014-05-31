package com.toto.tdtestmobile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

import org.openintents.intents.WikitudeARIntent;
import org.openintents.intents.WikitudePOI;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends Activity implements OnClickListener
{

	TextView textViewResultat = null;
	TextView textViewNom = null;
	TextView textViewPrenom = null;
	EditText inputNom = null;
	EditText inputPrenom = null;
    String reponse = null;
    Button comm = null;
	Context lecontext = this;
	Button b = null;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) 
	 {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        b = (Button) findViewById(R.id.Button1);
//	         b.setOnClickListener(new View.OnClickListener()
//	         {
//		        public void onClick(View v) 
//		        {
//		        	startARViewBasic();
//		        }
//	         });
	         
	         inputNom = (EditText) findViewById(R.id.inputNom);
	         inputPrenom = (EditText) findViewById(R.id.inputPrenom);
	         comm = (Button) findViewById(R.id.recherche);
	         textViewResultat = (TextView) findViewById(R.id.resultat);
	         textViewNom = (TextView) findViewById(R.id.nom);
	         textViewPrenom = (TextView) findViewById(R.id.prenom);
	  
	         comm.setOnClickListener(this);
	   
	  }	
	 
	 public void onClick(View v)
	 {
		 
	        switch (v.getId())
	        {
	        case R.id.recherche:
	 
	              new Thread(new Runnable() 
	              {
	                    public void run() 
	                    {
	 
	                        try
	                        {
	                        	/**
	                        	 * 10.0.2.2 : adresse localhost de l'émulateur
	                        	 */
	                            URL url = new URL("http://10.0.2.2:8080/test/toto");
	                            URLConnection connection = url.openConnection();
	 
	                            String nom = inputNom.getText().toString();
	                            String prenom = inputPrenom.getText().toString();
	                            //inputString = URLEncoder.encode(inputString, "UTF-8");
	 
	                            Log.d("inputs:", inputNom+" "+inputPrenom);
	 
	                            connection.setDoOutput(true);
	                            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
	                            out.write(nom+"#"+prenom);
	                            out.close();
	 
	                            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	 
	                            String returnString="";
	                            reponse = "";
	 
	                            while ((returnString = in.readLine()) != null) 
	                            {
	                                reponse= returnString;
	                            }
	                            in.close();
	 
	 
	                            runOnUiThread(new Runnable() 
	                            {
	                                 public void run() 
	                                 {
	 
	                                     textViewResultat.setText(reponse.toString());	 
	                                }
	                            });
	 
	                            }
	                        catch(Exception e)
	                            {
	                                Log.d("Exception",e.toString());
	                            }
	 
	                    }
	                  }).start();
	 
	            break;
	            }
	        }
	 
	 
	  
	  private void startARViewBasic() 
	  {
		  
		   // Creation d'un intent basic  AR
		   WikitudeARIntent intent = prepareIntent();
	 
		   // Titre de mon applicaiton
		   intent.addTitleText("Coucou");
	 
		   // lancement de mon activity
		   try
		   {
			   lecontext.startActivity(intent);
		   }
	     // erreur si l'application wikitude n'est pas installer sur votre téléphone
	     catch (ActivityNotFoundException e) 
	     {
			   Toast.makeText(this, "Rien trouvé "+e.getMessage(),
			   Toast.LENGTH_SHORT).show();
		   }
	   }
	  
	  
	  /**
	     * prepares a Wikitude AR Intent (e.g. adds the POIs to the view)
	     * 
	     * @return the prepared intent
	     */
	    private WikitudeARIntent prepareIntent() 
	    {
		    // create the intent
		    WikitudeARIntent intent = new WikitudeARIntent(this.getApplication(), null, null);
		    //add the POIs
		    this.addPois(intent);
		    return intent;
	    }
	    
	    
	    /**
	     * adds hard-coded POIs to the intent
	     * 
	     * @param intent
	     *            the intent
	     */
	    private void addPois(WikitudeARIntent intent)
	    {
	      WikitudePOI poi1 = new WikitudePOI(50.130361, 3.370028, 1, "FONTAINE-AU-PIRE COMMUNAL CEMETERY",
		    "The North-West end of the Communal Cemetery was used by the enemy after the Battle of Le Cateau, 1914," +
		    " and the British wounded who died in the Mairie were buried there. Other British graves were made by the" +
		    " enemy during the War; and in October, 1918, the New Zealand Division, who took the village, buried three of" +
		    " their dead. The majority of the British burials are in one row, behind which is the terrace carrying the War" +
		    " Cross, but some are isolated in the ground South-East of the row.");
	      
	      //WikitudePOI poi2 = new WikitudePOI(42.697532, 2.893424, 1, "Palais des Rois de Majorque",
		    //"e palais des Rois de Majorque est un palais-forteresse de style gothique situé à Perpignan. Cet édifice fut achevé en 1309");
	   
	    Collection<WikitudePOI> pois = new ArrayList<WikitudePOI>();
		    pois.add(poi1);
		    //pois.add(poi2);
		    intent.addPOIs(pois);
	    }
}