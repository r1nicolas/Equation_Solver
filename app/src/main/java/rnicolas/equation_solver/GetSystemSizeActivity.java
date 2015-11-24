package rnicolas.equation_solver;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class GetSystemSizeActivity extends AppCompatActivity {

    public final static String EXTRA_SIZE = "SIZE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_system_size);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_system_size, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getInputSystem(View view) {
        Intent intent = new Intent(this, GetInputSystem.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String dialogString = "";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        try {
            int size = Integer.parseInt(editText.getText().toString());
            if (size <= 50 && size != 0) {
                intent.putExtra(EXTRA_SIZE, size);
                startActivity(intent);
            }
            else if (size == 0) {
                dialogString = "Please enter non-null integer.";
            }
            else  {
                dialogString = (size) + " is to big. Maximum number is 50.";
            }
        } catch (NumberFormatException e) {
            dialogString = "Error invalid input. Please try again";
        }
        finally {
            if (dialogString != "") {
                alertDialogBuilder.setMessage(dialogString);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
    }
}
