package com.sam.amman.rescue.Normal_Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sam.amman.rescue.Adapters.CallDoctor_ListFrag;
import com.sam.amman.rescue.Adapters.Preferences;
import com.sam.amman.rescue.Adapters.ServiceHandler;
import com.sam.amman.rescue.R;


public class CallDoctor extends Fragment {

    Preferences pref;
    ListView categoriesLst;
    Button BtnSendSymp;
    View v;
    final String url = "http://rescueproject2016.netne.net/myPHP/CaseReport.php";
    String uid,locationStr,symptoms;
    String response;
    // arraylist
    public static String[] symptomsChecked =new String[3];


    CallDoctor_ListFrag a = new CallDoctor_ListFrag();
    final String[] datasource1={"Swelling and redness of the injured area"
            ,"Pain develops"
            ,"Burned area becomes white on touch"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_call_doctor,container,false);
        a.setDatasource(datasource1);


        v.findViewById(R.id.BtnSendSymp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Symptoms will be sent", Toast.LENGTH_LONG).show();
//                List<Integer> symptoms=new ArrayList<Integer>();
//                for(int q=0;q<3;q++){
//                    Log.e("CheckCounter",q+"");
//                    if( symptomsChecked[q]){
//                        symptoms.add(q);
//                    }
//                }
                symptoms="";

                for(int i = 0 ; i < symptomsChecked.length ; i++){
                    symptoms = symptoms + symptomsChecked[i];
                }

                //temp
                for(int i = 0 ; i < symptoms.length() ; i++){
                    if(symptoms.charAt(i)==' '){
                        symptoms=symptoms.replace(symptoms.charAt(i),',');
                    }
                }

                CreateCase casee = new CreateCase();
                casee.execute();


            }
        });


        FragmentTransaction tr = getChildFragmentManager().beginTransaction().replace(R.id.sympListFrame,a);
        tr.commit();

        //categories list
//        categoriesLst = (ListView) v.findViewById(R.id.catLst);
//        String[] sympSource = {"First degree burn","2","3"};
//        ArrayAdapter<String> sympAdapter =
//                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sympSource);
//        categoriesLst.setAdapter(sympAdapter);


//        final String[] datasource2={"4","5","6"};
//        final String[] datasource3={"7","8","9"};
//
//        categoriesLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                // CHANGE DATA SOURCE DEPENDING ON i
//
//                //symptoms list
//                if(i==0){
//                    CallDoctor_ListFrag a = new CallDoctor_ListFrag();
//                    a.setDatasource(datasource1);
//                    FragmentTransaction tr = getChildFragmentManager().beginTransaction().replace(R.id.sympListFrame,a);
//                    tr.commit();
//                }
//                if(i==1){
//                    CallDoctor_ListFrag a = new CallDoctor_ListFrag();
//                    a.setDatasource(datasource2);
//                    FragmentTransaction tr = getChildFragmentManager().beginTransaction().replace(R.id.sympListFrame,a);
//                    tr.commit();
//                }
//                if(i==2){
//                    CallDoctor_ListFrag a = new CallDoctor_ListFrag();
//                    a.setDatasource(datasource3);
//                    FragmentTransaction tr = getChildFragmentManager().beginTransaction().replace(R.id.sympListFrame,a);
//                    tr.commit();
//                }
//            }
//        });

        return v;
    }

    class CreateCase extends AsyncTask<Void,Void,Void>{
        ServiceHandler serviceHandler = new ServiceHandler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pref = new Preferences(getActivity());
            uid = pref.getUID();
            //TODO not working getUID
//            uid = "22";
//            locationStr = pref.getLocation();
            locationStr = "99.000000,99.000000";
//            Toast.makeText(getActivity(),locationStr,Toast.LENGTH_SHORT).show();

            //TODO fill symptoms string
//            for(int i = 0 ; i < symptomsChecked.length ; i++){
//                symptoms = symptoms + symptomsChecked[i];
//            }
//            symptoms="test from android";


        }

        @Override
        protected Void doInBackground(Void... voids) {


            response = serviceHandler.createCase(url,uid,locationStr,symptoms);

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            response=response.trim();
            if(response.equals("1"))
                Toast.makeText(getActivity(),"sent successfully",Toast.LENGTH_SHORT).show();

        }
    }



}
