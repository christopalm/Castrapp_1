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

public class pre_Tips_Fragment extends Fragment {
    ListView lista;

    String [] preconsejos = {"Debes llevar a tu mascota en ayunas (12 horas sin haber comido)",
            "Lleva a tu mascota en una jaula o canasta, también es recomendable llevarlos con correa (recuerda quitarla antes de la operación)",
            "Llevar una o mas frazadas para cubrir a la mascota",
            "La jaula o canasta debe ir identificada con el nombre de la mascota y dueño"};

    int [] images = {R.drawable.consejo,R.drawable.consejo,R.drawable.consejo,
            R.drawable.consejo};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pre__tips_, container, false);
        lista = (ListView)view.findViewById(R.id.listViewPre);

        //ArrayAdapter<String> adaptador = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, preconsejos);

        //lista.setAdapter(adaptador);

        pre_Tips_Fragment.CustomAdapter customAdapter = new pre_Tips_Fragment.CustomAdapter();
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
            return preconsejos.length;
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
            textConsejo.setText(preconsejos[i]);
            return view;
        }
    }
}
