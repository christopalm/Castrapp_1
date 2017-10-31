package castrapp.mobile.palm.castrapp_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.castrapp.palm.castrapp_1.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter.FeedListAdapter;
import data.GroupItem;
import data.Filtro;
import data.Grupo;

public class main_Act extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = AppController.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<GroupItem> groupItems;
    public static List<Grupo> grupos_Animalistas;

    public static List<GroupItem> listaCastraciones;
    public static List<GroupItem> listaVacunaciones;
    public static List<GroupItem> listaAdopciones;
    public static List<GroupItem> listaGeneral;

    public static ArrayList<Filtro> lista_Filtros;

    private static boolean recargar=true;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        if(recargar==true || main_Act.listaCastraciones == null){
            grupos_Animalistas = new ArrayList<Grupo>();
            groupItems = new ArrayList<GroupItem>();
            listaCastraciones = new ArrayList<GroupItem>();
            listaVacunaciones = new ArrayList<GroupItem>();
            listaAdopciones = new ArrayList<GroupItem>();
            listaGeneral = new ArrayList<GroupItem>();
            lista_Filtros = new ArrayList<>();

            listView = (ListView) findViewById(R.id.list);
            listAdapter = new FeedListAdapter(this, groupItems);
            listView.setAdapter(listAdapter);

            crearFiltros();
            prepararGruposEnPreLista();

            if(isOnlineNet()==true){
                recorrerListaGrupos();
                recargar=false;
            }
            else {
                Toast.makeText(main_Act.this,
                        "No se detectó conexión a Internet", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            groupItems = new ArrayList<GroupItem>();
            listAdapter = new FeedListAdapter(this, groupItems);
            llenarViews();

            listView = (ListView) findViewById(R.id.list);

            listView.setAdapter(listAdapter);
        }



        //obtenerPictureProfile("AnimalsHope14");
        //guardarPostsCastraciones("AnimalsHope14", "Animals Hope");

        //GroupItem feed = new GroupItem(1, "Hola", "http://api.androidhive.info/feed/img/cosmos.jpg", "STATUS", "http://api.androidhive.info/feed/img/nat.jpg", "1403375851930", "http://ti.me/1qW8MLB");

        //groupItems.add(feed);

        //listAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        if(isOnlineNet()==true){
            groupItems.clear();
            listAdapter.notifyDataSetChanged();

            grupos_Animalistas = new ArrayList<Grupo>();
            groupItems = new ArrayList<GroupItem>();
            listaCastraciones = new ArrayList<GroupItem>();
            listaVacunaciones = new ArrayList<GroupItem>();
            listaAdopciones = new ArrayList<GroupItem>();
            listaGeneral = new ArrayList<GroupItem>();
            lista_Filtros = new ArrayList<>();

            listView = (ListView) findViewById(R.id.list);
            listAdapter = new FeedListAdapter(this, groupItems);
            listView.setAdapter(listAdapter);

            crearFiltros();
            prepararGruposEnPreLista();
            recorrerListaGrupos();
        }
        else{
            Toast.makeText(main_Act.this,
                    "No se detectó conexión a Internet", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings2) {
            Toast.makeText(main_Act.this,
                    "Christopher Palma\nabimaelpalma@gmail.com\nGracias a todos los grupos\nVe a Grupos para conocerlos", Toast.LENGTH_LONG).show();
            Toast.makeText(main_Act.this,
                    "Castrapp 2017", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_castracion) {
            Intent actividadCastraciones = new Intent(this, main_Act.class);
            startActivity(actividadCastraciones);
            finish();
        } else if (id == R.id.nav_salud) {
            Intent actividadVacunaciones = new Intent(this, vac_Act.class);
            startActivity(actividadVacunaciones);
            finish();
        } else if (id == R.id.nav_adopta) {
            Intent actividadAdopciones = new Intent(this, adop_Act.class);
            startActivity(actividadAdopciones);
            finish();
        } else if (id == R.id.nav_general) {
            Intent actividadGenerales = new Intent(this, general_Act.class);
            startActivity(actividadGenerales);
            finish();
        } else if (id == R.id.nav_consejo) {
            Intent actividadTips = new Intent(this, acitivity_tips.class);
            startActivity(actividadTips);
        } else if (id == R.id.nav_grupos) {
            Intent actividadGrupos = new Intent(this, grupos_layout.class);
            startActivity(actividadGrupos);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cambiarTexto() {
        //TextView text1 = (TextView) findViewById(R.id.textView1);

        //text1.setText("JORNADAS DE CASTRACIONES");
    }

    public void guardarPostsCastraciones(String grupo, final String nombre_Grupo, final String picture_Image_Url, final int posicionGrupo){ //para obtener el JSON de los posts del grupo especificado en el parametro
        FacebookSdk.setApplicationId("135605863619857");
        FacebookSdk.sdkInitialize(getApplicationContext());

        AccessToken token = new AccessToken("135605863619857|jth_v2J5vVMsDKdaE5oeK7rftD0", "135605863619857", "christopalm", null, null, null, null, null);

        try {
            GraphRequest req = GraphRequest.newGraphPathRequest(token, grupo + "/feed",
                    new GraphRequest.Callback() {

                        @Override
                        public void onCompleted(GraphResponse graphResponse) {
                            Log.v("Output", graphResponse.getJSONObject().toString());

                            filtrarGruposYMostrar(guardarPostEnLista(graphResponse.getJSONObject().toString(), nombre_Grupo, picture_Image_Url));

                            if (posicionGrupo == grupos_Animalistas.size() - 1) { //una vez llegado al ultimo grupo y filtrado todas sus publicaciones
                                Collections.sort(listaAdopciones);
                                Collections.sort(listaCastraciones);
                                Collections.sort(listaVacunaciones);
                                Collections.sort(listaGeneral);

                                System.out.println("TAMANIO CASTRA: " + listaCastraciones.size());
                                mostrarCastraciones();
                                System.out.println("FINALIZANDO");
                            }
                            swipeRefreshLayout.setRefreshing(false);
                            siguienteGrupo(posicionGrupo);
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "attachments,created_time,name,message");
            parameters.putInt("limit", 50);
            req.setParameters(parameters);
            GraphRequest.executeBatchAsync(req);
        }
        catch (Exception e){
            Toast.makeText(main_Act.this,
                    "Error inesperado, creemos que fue Firulais. Carga todo de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

    public void obtenerInfoGrupo(final String grupo, final String nombre_Grupo, final int posicionGrupo){
        swipeRefreshLayout.setRefreshing(true);
        Toast.makeText(main_Act.this,
                "Obteniendo de " + nombre_Grupo, Toast.LENGTH_SHORT).show();
        FacebookSdk.setApplicationId("135605863619857");

        if(FacebookSdk.isInitialized()==false)
            FacebookSdk.sdkInitialize(getApplicationContext());

        AccessToken token = new AccessToken("135605863619857|jth_v2J5vVMsDKdaE5oeK7rftD0", "135605863619857", "christopalm", null, null, null, null, null);

        try {
            GraphRequest req = GraphRequest.newGraphPathRequest(token, grupo + "/picture",
                    new GraphRequest.Callback() {

                        @Override
                        public void onCompleted(GraphResponse graphResponse) {
                            Log.v("Output", graphResponse.getJSONObject().toString());

                            JSONObject reader = graphResponse.getJSONObject();

                            try {
                                JSONObject dataObject = reader.getJSONObject("data");

                                if (dataObject.has("url")) {
                                    String picture_Image_Url = dataObject.getString("url");
                                    System.out.println("PROFILE: " + picture_Image_Url);

                                    guardarPostsCastraciones(grupo, nombre_Grupo, picture_Image_Url, posicionGrupo);
                                }
                            } catch (Exception e) {
                                System.out.println("ERROR al Leer JSON de Picture");
                            }

                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putBoolean("redirect", false);
            req.setParameters(parameters);
            GraphRequest.executeBatchAsync(req);
        }
        catch (Exception e){
            Toast.makeText(main_Act.this,
                    "Error inesperado, creemos que fue Firulais. Carga todo de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<GroupItem> guardarPostEnLista(String cadenaJson, String nombre_Grupo2, String picture_Image_Url){
        ArrayList<GroupItem> listaContenido = new ArrayList<>();
        String target = "";
        String pathImage = "";
        try{
            JSONObject reader = new JSONObject(cadenaJson);
            JSONArray dataArray = reader.getJSONArray("data");

            for(int i=0; i<=dataArray.length()-1; i++){ //primer Array de data
                JSONObject dataObject = dataArray.getJSONObject(i);
                String id_post = dataObject.getString("id");
                //System.out.println("ID[" + i + "]: " + id_post);

                String created_Time = null;
                if(dataObject.has("created_time"))
                    created_Time = dataObject.getString("created_time");
                //System.out.println("Created_Time: " + created_Time);

                String formattedDate = transformar_Fecha(created_Time); //TRANSFORMAR FECHA A UNIX Y SUMAR EL HORARIO DE GUATEMALA

                String nombre_Grupo = null;
                if(dataObject.has("name"))
                    nombre_Grupo = dataObject.getString("name");
                //System.out.println("Group Name: " + nombre_Grupo);

                String message = null;
                if(dataObject.has("message"))
                    message = dataObject.getString("message");

                //System.out.println("Picture Group: " + picture_Image_Url);

                if(dataObject.has("attachments")){
                    JSONObject attachmentsObject = dataObject.getJSONObject("attachments");
                    JSONArray attachArray = attachmentsObject.getJSONArray("data");

                    for(int j=0; j<=attachArray.length()-1; j++) { //segundo array de data dentro de attachments
                        JSONObject attachObject = attachArray.getJSONObject(j);

                        if(attachObject.has("description") && message == null)
                            message = attachObject.getString("description");

                        if(attachObject.has("media")){
                            JSONObject mediaObject = attachObject.getJSONObject("media");

                            if(mediaObject.has("image")){
                                JSONObject imageObject = mediaObject.getJSONObject("image");
                                int height = imageObject.getInt("height");
                                pathImage = imageObject.getString("src");
                                int width = imageObject.getInt("width");

                                //System.out.println("path_Image= " + pathImage);
                            }
                            else{
                                //System.out.println("NO_IMAGE");
                            }
                        }
                        else{
                            //System.out.println("NO_MEDIA");
                            //System.out.println("NO_DESCRIPTION, POSIBLES SUBATTACHMENTS");
                        }

                        if(attachObject.has("subattachments")){
                            JSONObject subAttachmentsObject = attachObject.getJSONObject("subattachments");

                            if(subAttachmentsObject.has("data")){
                                JSONArray subAttachArray = subAttachmentsObject.getJSONArray("data");

                                for(int y=0; y<=subAttachArray.length()-1; y++) {
                                    JSONObject subAttachObject = subAttachArray.getJSONObject(y);

                                    if(subAttachObject.has("media")){
                                        JSONObject mediaObject = subAttachObject.getJSONObject("media");

                                        if(mediaObject.has("image")){
                                            JSONObject imageObject = mediaObject.getJSONObject("image");
                                            pathImage = imageObject.getString("src");
                                            //System.out.println("SI MEDIA EN SUB");
                                        }
                                    }
                                    else{
                                        //System.out.println("NO MEDIA EN SUBATTACHMETNS");
                                    }

                                    if(y==1){
                                        break;
                                    }
                                }
                            }
                            else {
                                //System.out.println("NO DATA EN SUBATTACHMENTS");
                            }
                        }
                        else{
                            //System.out.println("NO SUBATTACHMENTS");
                        }

                        if(attachObject.has("target")){
                            JSONObject targetObject = attachObject.getJSONObject("target");

                            target = targetObject.getString("url");
                            //System.out.println("Target: " + target);
                        }
                        else{
                            //System.out.println("NO TARGET");
                        }
                    }

                    //GUARDAR OBJETO
                    GroupItem feed = new GroupItem(id_post, nombre_Grupo2, pathImage, message, picture_Image_Url, formattedDate, target);
                    listaContenido.add(feed);
                    //groupItems.add(feed);
                    //listAdapter.notifyDataSetChanged();
                }
                else{
                    //System.out.println("NO_ATTACHMENTS");
                }
            }
        }
        catch(Exception e){
            System.out.println("Error en lectura de JSON");
        }
        return listaContenido;
    }

    public String transformar_Fecha(String created_Time){
        String formattedDate = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date parsedDate = dateFormat.parse(created_Time);
            long time_Time_Actual = parsedDate.getTime() + TimeZone.getTimeZone("America/Guatemala").getRawOffset();

            formattedDate = Long.toString(time_Time_Actual);

            //System.out.println("TIMESTAMP: " + formattedDate);
        }catch(Exception e){//this generic but you can control another types of exception
            e.printStackTrace();
            System.out.println("Error al transformar la fecha");
        }

        return formattedDate;
    }

    public void prepararGruposEnPreLista(){
        Grupo grupo1 = new Grupo("amigosanimales", "AMA Asociación de Amigos de los Animales");
        grupos_Animalistas.add(grupo1);

        Grupo grupo2 = new Grupo("CastracionesGuausMiausGuatemala","Castraciones Guaus & Miaus Guatemala");
        grupos_Animalistas.add(grupo2);

        //Grupo grupo3 = new Grupo("698817973537924","Animal ER");
        //grupos_Animalistas.add(grupo3);

        Grupo grupo4 = new Grupo("AnimalsHope14","Animals Hope");
        grupos_Animalistas.add(grupo4);

        Grupo grupo5 = new Grupo("piscapguatemala", "Piscap Guatemala");
        grupos_Animalistas.add(grupo5);

        Grupo grupo6 = new Grupo("dalaipets", "SavingAnimals Guatemala");
        grupos_Animalistas.add(grupo6);

        Grupo grupo7 = new Grupo("277714615591014", "Adopta Una Mascota");
        grupos_Animalistas.add(grupo7);

        Grupo grupo8 = new Grupo("castrapp", "Castrapp");
        grupos_Animalistas.add(grupo8);

        /*Grupo grupo9 = new Grupo("AMAextraviados", "AMA Perdidos y Encontrados");
        grupos_Animalistas.add(grupo9);*/

        Grupo grupo10 = new Grupo("819440698127339", "Huellitas de la Calle");
        grupos_Animalistas.add(grupo10);

        //Grupo grupo11 = new Grupo("alberguemascotas", "Albergue Municipal de Mascotas");
        //grupos_Animalistas.add(grupo11);
    }

    public void recorrerListaGrupos(){
        for(int i=0; i<=0; i++){
            obtenerInfoGrupo(grupos_Animalistas.get(i).getId_Grupo(), grupos_Animalistas.get(i).getNombre_Grupo(), i);
        }
    }

    public void siguienteGrupo(int posicionGrupo){
        posicionGrupo++;
        if(posicionGrupo<=grupos_Animalistas.size()-1){
            obtenerInfoGrupo(grupos_Animalistas.get(posicionGrupo).getId_Grupo(), grupos_Animalistas.get(posicionGrupo).getNombre_Grupo(), posicionGrupo);
        }
    }

    public void filtrarGruposYMostrar(ArrayList<GroupItem> listaContenido){ //Filtra los grupos, los separa en listas y actualiza el View actual (castraciones)
        for(int i=0; i<=listaContenido.size()-1; i++){
            GroupItem itemActual = listaContenido.get(i);

            Boolean encontrado = false;

            String contenido = listaContenido.get(i).getStatus();

            //ANALIZANDO CON FILTROS
            for(int j=0; j<=lista_Filtros.size()-1; j++){ //recorriendo encabezados de filtros, 0 = castraciones, 1 = vacunaciones, 2= adopciones
                Filtro filtro_Actual = lista_Filtros.get(j);

                for(int u=0; u<=filtro_Actual.getLista_Filtros().size()-1; u++){
                    String palabra_Filtro_Actual = filtro_Actual.getLista_Filtros().get(u);

                    if(contenido != null){
                        //se hace uso de la busqueda de la palabra en si, para ello se usan \\b
                        Pattern regex = Pattern.compile("\\b" + Pattern.quote(palabra_Filtro_Actual) + "\\b", Pattern.CASE_INSENSITIVE);
                        Matcher match = regex.matcher(contenido);

                        if(match.find()){
                            if(j==0){
                                listaCastraciones.add(itemActual);
                            }
                            if(j==1){
                                listaAdopciones.add(itemActual);
                            }
                            if(j==2){
                                listaVacunaciones.add(itemActual);
                            }
                            encontrado = true;
                            break;
                        }
                    }

                }
                if(encontrado==true)
                    break;
            }
            if(encontrado==false) //no coincide con los filtros
                listaGeneral.add(itemActual);

        }
    }

    public void mostrarCastraciones(){
        if(main_Act.listaCastraciones.size()==0){

            Calendar cal = new GregorianCalendar();
            Date date = cal.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            //long time_Time_Actual = parsedDate.getTime() + TimeZone.getTimeZone("America/Guatemala").getRawOffset();

            String formattedDate = Long.toString(date.getTime());
            System.out.println(formattedDate);

            GroupItem feed = new GroupItem("000", "No encontradas", "https://www.facebook.com/rsrc.php/v3/yz/r/YYyCT7UcAGY.png", "Lo sentimos no se encontraron publicaciones de Jornadas de Castración en este momento, recargue de nuevo o visite nuestra página en Facebook Castrapp", "https://image.freepik.com/iconos-gratis/cara-triste-en-la-pagina-del-calendario_318-58090.jpg", formattedDate, "https://www.facebook.com/castrapp");
            groupItems.add(feed);
            listAdapter.notifyDataSetChanged();
        }
        else{
            for(int i=0; i<=listaCastraciones.size()-1; i++){
                groupItems.add(listaCastraciones.get(i));
                listAdapter.notifyDataSetChanged();
            }
        }

    }

    //FILTRAR CADA VEZ QUE TERMINO DE RECORRER UN GRUPO Y AGREGAR A LISTAS, TAMBIEN ACTUALIZAR DE UNA VEZ EL VIEW

    public void crearFiltros(){
        Filtro filtro1 = new Filtro("Castracion");
        filtro1.setFiltro("castración");
        filtro1.setFiltro("castracion");
        filtro1.setFiltro("esterilizacion");
        filtro1.setFiltro("esterilización");
        //filtro1.setFiltro("castrar");
        filtro1.setFiltro("castra");
        filtro1.setFiltro("esterilizar");

        lista_Filtros.add(filtro1);

        Filtro filtro2 = new Filtro("Vacunacion");
        filtro2.setFiltro("vacunación");
        filtro2.setFiltro("vacunacion");
        filtro2.setFiltro("salud");
        filtro2.setFiltro("vacunar");
        filtro2.setFiltro("vacuna");



        Filtro filtro3 = new Filtro("Adopcion");
        filtro3.setFiltro("adopción");
        filtro3.setFiltro("adopcion");
        filtro3.setFiltro("adopta");
        filtro3.setFiltro("adoptar");
        filtro3.setFiltro("hogar");
        filtro3.setFiltro("adoptante");
        filtro3.setFiltro("temporal");
        filtro3.setFiltro("adopciones@adoptaunamascotagt.org");

        lista_Filtros.add(filtro3);
        lista_Filtros.add(filtro2);
    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public void llenarViews(){
        if(main_Act.listaCastraciones != null){
            if(main_Act.listaCastraciones.size()==0){
                //TextView text1 = (TextView) findViewById(R.id.textView2);

                //text1.setText("Lo sentimos, no existen jornadas de salud");

                Calendar cal = new GregorianCalendar();
                Date date = cal.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                //long time_Time_Actual = parsedDate.getTime() + TimeZone.getTimeZone("America/Guatemala").getRawOffset();

                String formattedDate = Long.toString(date.getTime());
                System.out.println(formattedDate);

                GroupItem feed = new GroupItem("000", "No encontradas", "https://www.facebook.com/rsrc.php/v3/yz/r/YYyCT7UcAGY.png", "Lo sentimos no se encontraron publicaciones de Jornadas de Salud en este momento, visite nuestro página en Facebook de Castrapp", "https://image.freepik.com/iconos-gratis/cara-triste-en-la-pagina-del-calendario_318-58090.jpg", formattedDate, "https://www.facebook.com/castrapp");
                groupItems.add(feed);
                listAdapter.notifyDataSetChanged();
            }
            else{
                for(int i=0; i<=main_Act.listaCastraciones.size()-1; i++){
                    GroupItem feedActual = main_Act.listaCastraciones.get(i);

                    groupItems.add(feedActual);
                    listAdapter.notifyDataSetChanged();
                }
            }
        }
        else {
            Toast.makeText(main_Act.this,
                    "Recargando todas las publicaciones...", Toast.LENGTH_SHORT).show();
            Intent actividadCastraciones = new Intent(this, main_Act.class);
            startActivity(actividadCastraciones);
            finish();
        }

    }
}
