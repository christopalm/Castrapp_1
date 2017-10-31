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

public class post_Tips_Fragment extends Fragment {
    ListView lista;

    String [] postconsejos = {"Tu mascota debe descansar por una semana. Los primeros días debe estar encerrado en una jaula o en tu cuarto." +
            " No permitas que salga ni que otros animales se le acerquen, no debe bajar gradas ni correr",
            "Debes procurar que no se lama su herida, aunque esto es muy dificil de evitarlo",
            "Si es posible puedes aplicar una crema cicatrizante, no debes lavar la herida",
            "No te preocupes por los puntos, se absorben solos"};

    int [] images = {R.drawable.consejo2,R.drawable.consejo2,R.drawable.consejo2,
            R.drawable.consejo2};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post__tips_, container, false);
        lista = (ListView)view.findViewById(R.id.listViewPost);

        //ArrayAdapter<String> adaptador = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, postconsejos);

        //lista.setAdapter(adaptador);

        post_Tips_Fragment.CustomAdapter customAdapter = new post_Tips_Fragment.CustomAdapter();
        lista.setAdapter(customAdapter);

        return view;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return postconsejos.length;
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
            textConsejo.setText(postconsejos[i]);
            return view;
        }
    }
}
