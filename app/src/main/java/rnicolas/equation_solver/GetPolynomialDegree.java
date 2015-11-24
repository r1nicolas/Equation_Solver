package rnicolas.equation_solver;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class GetPolynomialDegree extends AppCompatActivity {

    public final static String EXTRA_DEGREE = "DEGREE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_polynomial_degree);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_polynomial_degree, menu);
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

    public void getInputPolynomial(View view) {
        Intent intent = new Intent(this, GetInputPolynomial.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String dialogString = "";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        try {
            int degree = Integer.parseInt(editText.getText().toString());
            if (degree <= 50) {
                intent.putExtra(EXTRA_DEGREE, degree);
                startActivity(intent);
            }
            else  {
                dialogString = (degree) + " is to big. Maximum number is 50.";
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
