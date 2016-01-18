package rnicolas.equation_solver;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class GetInputPolynomial extends AppCompatActivity {

    private int n;
    private EditText[] editTexts;
    private TextView[] textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_input_polynomial);

        int i;
        Intent intent = getIntent();
        n = intent.getIntExtra(GetPolynomialDegree.EXTRA_DEGREE, 0);

        editTexts = new EditText[n + 1];
        textViews = new TextView[n + 1];

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(2);
        gridLayout.setRowCount(n + 1);

        for (i = n;i >= 0; i--) {
            final EditText tempEditText = new EditText(this);
            final TextView tempTextView = new TextView(this);

            tempEditText.setHint(Html.fromHtml("a<sub><small>" + i + "</small></sub>"));
            tempEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

            if (i == 0)
                tempTextView.setText(Html.fromHtml("= 0"));
            else if (i == 1)
                tempTextView.setText(Html.fromHtml("* x +"));
            else
                tempTextView.setText(Html.fromHtml("* x<sup><small>" + i + "</small></sup> +"));

            gridLayout.addView(tempEditText);
            gridLayout.addView(tempTextView);
            editTexts[i] = tempEditText;
            textViews[i] = tempTextView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_input_polynomial, menu);
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

    public void launchSolver(View view) {
        double[] tab = new double[n + 1];
        double[] sol;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        String resultString = "";
        int i;
        DecimalFormat df = new DecimalFormat("#.##");

        for (i = 0;i <= n;i++) {
            try {
                tab[i] = Double.parseDouble(editTexts[i].getText().toString());
            } catch (NumberFormatException e) {
                tab[i] = 0;
            }
        }
        PolynomialSolver solver = new PolynomialSolver(n, tab);
        sol = solver.solve();
        if (sol.length == 0)
            resultString = "Pas de solution dans R";
        else {
            for (i = 0;i < sol.length;i++) {
                resultString += "x<sub><small>" + (i + 1) + "</small></sub>" + " = " + df.format(sol[i]) + "<br />";
            }
        }
        alertBuilder.setMessage(Html.fromHtml(resultString));
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
