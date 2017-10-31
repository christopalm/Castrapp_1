package castrapp.mobile.palm.castrapp_1;

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

public class generales_Tips_Fragment extends Fragment {
    ListView lista;

    String [] beneficios = {"Tu mascota vivirá más tiempo y con una vida más relajada",
    "No querrá ir afuera en busca de pareja",
    "La castración ayuda a reducir la población de mascotas que muchas veces quedan en la calle",
    "Serán mas limpios",
    "La castración no los engordará, recuerda que debes ejercitarla para compensar sus salidas"};

    int [] images = {R.drawable.vida,R.drawable.pareja,R.drawable.poblacion,
            R.drawable.limpio,R.drawable.ejercicio};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_generales__tips_, container, false);
        lista = (ListView)view.findViewById(R.id.listViewG);

        //ArrayAdapter<String> adaptador = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, beneficios);

        //lista.setAdapter(adaptador);

        CustomAdapter customAdapter = new CustomAdapter();
        lista.setAdapter(customAdapter);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return beneficios.length;
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

            ImageView imageConsejo = (ImageView)view.findViewById(R.id.imageView_consejo);
            TextView textConsejo = (TextView)view.findViewById(R.id.textView_consejo);

            imageConsejo.setImageResource(images[i]);
            textConsejo.setText(beneficios[i]);
            return view;
        }
    }
}
