package androidmysql.fernandowebdeveloper.com.br.androidcommysql;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button btnCadastro = (Button)findViewById(R.id.btnCadastro);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText txtNome = (EditText)findViewById(R.id.txvNome);
                EditText txtEmail = (EditText) findViewById(R.id.txvEmail);

                Ion.with(getBaseContext())
                        .load("http://192.168.25.52/Android/inserir.php?")
                        .setBodyParameter("nome", txtNome.getText().toString())
                        .setBodyParameter("email", txtEmail.getText().toString())
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if(result.get("retorno").getAsString().equals("YES")){
                                    Toast.makeText(getBaseContext(),"Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(), ListarActivity.class));
                                }
                            }
                        });

                /*
                try {
                    StringBuilder strURL = new StringBuilder();
                    strURL.append("http://192.168.25.52/Android/inserir.php?");
                    strURL.append("nome=");
                    strURL.append(URLEncoder.encode(txtNome.getText().toString(), "UTF-8"));
                    strURL.append("&email=");
                    strURL.append(URLEncoder.encode(txtEmail.getText().toString(), "UTF-8"));
                    new HttpRequest().execute(strURL.toString());
                }catch (Exception ex){

                }
                */
            }
        });
    }

    private class HttpRequest extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String retorno  = null;
            try {
                String urlHttp = params[0];
                URL url = new URL(urlHttp);
                HttpURLConnection htttp = (HttpURLConnection) url.openConnection();
                InputStreamReader ips = new InputStreamReader(htttp.getInputStream());
                BufferedReader bfr = new BufferedReader(ips);
                retorno = bfr.readLine();

            }catch (Exception ex){

            }
            return retorno;
        }

        @Override
        protected void onPostExecute(String result){
            if(result.equals("YES")){
                Toast.makeText(getBaseContext(),"Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), ListarActivity.class));
            }else{
                Toast.makeText(getBaseContext(),"Erro ao cadastrar cliente!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
