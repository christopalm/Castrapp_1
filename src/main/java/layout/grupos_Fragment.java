package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.castrapp.palm.castrapp_1.R;


public class grupos_Fragment extends Fragment {
    ListView lista;

    String [] grupos = {"AMA Asociación de Amigos de los Animales",
            "Castraciones Guaus & Miaus Guatemala",
            "Animals Hope",
            "Piscap Guatemala",
            "SavingAnimals Guatemala",
            "Adopta Una Mascota",
            "Huellitas de la Calle"};

    int [] images = {R.drawable.ama,R.drawable.guausmiaus,R.drawable.animaler,
            R.drawable.animalshope,R.drawable.piscap, R.drawable.savinganimals,
            R.drawable.adopta, R.drawable.huellitas, R.drawable.alberguez21};

    String [] links = {"www.facebook.com/amigosanimales",
            "www.facebook.com/CastracionesGuausMiausGuatemala",
            "www.facebook.com/AnimalsHope14",
            "www.facebook.com/piscapguatemala",
            "www.facebook.com/dalaipets",
            "www.facebook.com/277714615591014",
            "https://www.facebook.com/Huellitas-de-la-Calle-819440698127339"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grupos_, container, false);
        lista = (ListView)view.findViewById(R.id.listViewGrupos);

        //ArrayAdapter<String> adaptador = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, beneficios);

        //lista.setAdapter(adaptador);

        grupos_Fragment.CustomAdapter customAdapter = new grupos_Fragment.CustomAdapter();
        lista.setAdapter(customAdapter);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return grupos.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getActivity().getLayoutInflater().inflate(R.layout.consejos_layout, null);

            ImageView imageGrupo = (ImageView)view.findViewById(R.id.imageView_consejo);
            TextView textGrupo = (TextView)view.findViewById(R.id.textView_consejo);
            TextView textWeb = (TextView)view.findViewById(R.id.textView_link);

            imageGrupo.setImageResource(images[i]);
            textGrupo.setText(grupos[i]);
            textWeb.setText(links[i]);
            return view;
        }
    }
}
