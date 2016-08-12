package androidmysql.fernandowebdeveloper.com.br.androidcommysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ListarActivity extends AppCompatActivity {

    private ArrayAdapter<JsonObject> clientesAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);



    }
        protected void onResume(){
          super.onResume();
        clientesAd = new ArrayAdapter<JsonObject>(this,0){
            @Override
            public View getView(int position, View view, ViewGroup viewGroup){
                if(view == null){
                    view = getLayoutInflater().inflate(R.layout.listview_clientes, null);
                }

                JsonObject obj = getItem(position);

                TextView txvNome = (TextView) view.findViewById(R.id.txvNome);
                txvNome.setText(obj.get("nome").getAsString());

                TextView txvEmail = (TextView) view.findViewById(R.id.txvEmail);
                txvEmail.setText(obj.get("email").getAsString());

                TextView txvId = (TextView) view.findViewById(R.id.txvId);
                txvId.setText(obj.get("id").getAsString());

                return   view;
            }
        };

        Ion.with(getBaseContext()).load("http://192.168.25.52/Android/listar.php")
                .asJsonArray().setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                for(int i = 0; i < result.size(); i ++){
                    JsonObject jsonObject = result.get(i).getAsJsonObject();
                    clientesAd.add(jsonObject);
                }

                ListView ltwClientes = (ListView) findViewById(R.id.ltwClientes);
                ltwClientes.setAdapter(clientesAd);

                ltwClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        JsonObject obj = (JsonObject)  parent.getItemAtPosition(position);
                        String codigo = obj.get("id").getAsString();
                        String nome = obj.get("nome").getAsString();
                        String email = obj.get("email").getAsString();

                        Intent intent = new Intent(getBaseContext(), EditarActivity.class);
                        intent.putExtra("codigo", codigo);
                        intent.putExtra("nome", nome);
                        intent.putExtra("email", email);

                        startActivity(intent);
                    }
                });

                //JsonObject retorno = result.get(0).getAsJsonObject();
                //Toast.makeText(getBaseContext(), retorno.get("nome").toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}