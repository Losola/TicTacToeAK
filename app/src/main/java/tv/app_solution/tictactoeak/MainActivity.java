package tv.app_solution.tictactoeak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    Button btn;
    TextView tv;
    boolean gameActivity = false;

    int activePlayer = 1;//1: Spieler 1 (money), 2: Spieler 2 (bitcoin)
    int[] statusSpielfelder = {0,0,0,0,0,0,0,0,0};//0: leer, 1: Spieler 1, 2: Spieler 2 //gameState
    int[][] gewinnZustaende = { {0,1,2}, {3,4,5}, {6,7,8},{0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6} };//winningPositions

    public void onTouch(View view) {
        //Log.i("Tag", view.getTag().toString());//um zu checken, ob Tags fuer Spielfelder richtig gesetzt sind
        ImageView imageView = (ImageView) view;
        imageView.setTranslationY(-1500);//wird nur gebraucht, wenn Startbilder gesetzt

        if(gameActivity) {
            if (activePlayer == 1) {
                imageView.setImageResource(R.drawable.money);
                activePlayer = 2;
                statusSpielfelder[Integer.parseInt(imageView.getTag().toString())] = 2;
            } else {
                imageView.setImageResource(R.drawable.bitcoin);
                activePlayer = 1;
                statusSpielfelder[Integer.parseInt(imageView.getTag().toString())] = 1;
            }
            imageView.animate().translationYBy(1500);

            for (int[] gewinnzustand : gewinnZustaende) {
                Log.i("Gewinnzustand: ", Arrays.toString(gewinnzustand));
                if (statusSpielfelder[gewinnzustand[0]] == statusSpielfelder[gewinnzustand[1]] &&
                        statusSpielfelder[gewinnzustand[0]] == statusSpielfelder[gewinnzustand[2]] &&
                        statusSpielfelder[gewinnzustand[0]] != 0) {
                    tv.setVisibility(View.VISIBLE);
                    //Toast.makeText(this, "Gewonnen", Toast.LENGTH_LONG).show();
                    tv.setText("Gewonnen");
                    tv.animate().rotation(720).setDuration(1000);//geht nur beim 1. Mal, warum?

                    btn.setVisibility(View.VISIBLE);
                    gameActivity = false;
                }
            }
        }
    }

    public void btnPlayAgain(View view) {
        android.support.v7.widget.GridLayout gl = (android.support.v7.widget.GridLayout) findViewById(R.id.gridLayout);
        gameActivity = true;
        btn.setVisibility(View.INVISIBLE);
        tv.setVisibility(View.INVISIBLE);
        for(int i=0; i < statusSpielfelder.length; i++) {
            statusSpielfelder[i] = 0;
        }
        Log.i("int[] statusSpielfelder", Arrays.toString(statusSpielfelder));
        for(int i=0; i < gl.getChildCount(); i++) {
            ImageView zelle = (ImageView) gl.getChildAt(i);
            zelle.setImageDrawable(null);//Zelle leeren
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textView);

        gameActivity = true;

    }
}
