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

public class GetInputSystem extends AppCompatActivity {

    private int size;
    private EditText[][] editTexts;
    private TextView[][] textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_input_system);

        int i, j;
        Intent intent = getIntent();
        size = intent.getIntExtra(GetSystemSizeActivity.EXTRA_SIZE, 0);

        editTexts = new EditText[size][size + 1];
        textViews = new TextView[size][size];

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(2 * size + 1);
        gridLayout.setRowCount(size);

        for (i = 0;i < size;i++) {
            for (j = 0;j < size;j++) {
                final EditText tempEditText = new EditText(this);
                final TextView tempTextView = new TextView(this);

                tempEditText.setHint(Html.fromHtml("a<sub><small>" + (i + 1) + " " + (j + 1) + "</small></sub>"));
                tempEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

                if (j + 1 != size)
                    tempTextView.setText(Html.fromHtml("x<sub><small>" + (j + 1) + "</small></sub> + "));
                else
                    tempTextView.setText(Html.fromHtml("x<sub><small>" + (j + 1) + "</small></sub> = "));
                tempTextView.setPadding(0, 0, 0, 3);

                gridLayout.addView(tempEditText);
                gridLayout.addView(tempTextView);
                editTexts[i][j] = tempEditText;
                textViews[i][j] = tempTextView;
            }
            final EditText tempEditText = new EditText(this);

            tempEditText.setHint(Html.fromHtml("n<sub><small>" + (i + 1) + "</small></sub>"));
            tempEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

            gridLayout.addView(tempEditText);
            editTexts[i][size] = tempEditText;
        }
    }

    public void launchSolver(View view) {
        double[][] tab = new double[size][size + 1];
        double[] sol;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        String resultString = "";
        int i, j;
        DecimalFormat df = new DecimalFormat("#.##");

        for (i = 0;i < size;i++) {
            for (j = 0;j <= size;j++) {
                try {
                    tab[i][j] = Double.parseDouble(editTexts[i][j].getText().toString());
                } catch (NumberFormatException e) {
                    tab[i][j] = 0;
                }
            }
        }
        SystemSolver solver = new SystemSolver(size, tab);
        try {
            sol = solver.solve();
            for (i = 0;i < size;i++) {
                resultString += "x<sub><small>" + (i + 1) + "</small></sub>" + " = " + df.format(sol[i]) + "<br />";
            }
        } catch (Exception e) {
            resultString = "Infinité ou pas de résultat, on ne sait pas encore";
        }
        alertBuilder.setMessage(Html.fromHtml(resultString));
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_input_system, menu);
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
}
