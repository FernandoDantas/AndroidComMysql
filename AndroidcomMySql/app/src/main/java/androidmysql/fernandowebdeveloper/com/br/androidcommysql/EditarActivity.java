package androidmysql.fernandowebdeveloper.com.br.androidcommysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class EditarActivity extends AppCompatActivity {

    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        Intent intent = getIntent();


        final EditText txtNome = (EditText)findViewById(R.id.txvNome);
        final EditText txtEmail = (EditText) findViewById(R.id.txvEmail);

        codigo = intent.getStringExtra("codigo");
        txtNome.setText(intent.getStringExtra("nome"));
        txtEmail.setText(intent.getStringExtra("email"));

        Button btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.with(getBaseContext())
                        .load("http://192.168.25.52/Android/atualizar.php?")
                        .setBodyParameter("codigo", codigo)
                        .setBodyParameter("nome", txtNome.getText().toString())
                        .setBodyParameter("email", txtEmail.getText().toString())
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if(result.get("retorno").getAsString().equals("YES")){
                                    Toast.makeText(getBaseContext(),"Cliente atualizado com sucesso!", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        });
            }
        });

        Button btnApagar = (Button) findViewById(R.id.btnApagar);
        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.with(getBaseContext())
                        .load("http://192.168.25.52/Android/deletar.php?")
                        .setBodyParameter("codigo", codigo)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if(result.get("retorno").getAsString().equals("YES")){
                                    Toast.makeText(getBaseContext(),"Cliente apagado com sucesso!", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
