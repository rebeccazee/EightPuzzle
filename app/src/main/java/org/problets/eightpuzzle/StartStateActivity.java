package org.problets.eightpuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Amruth
 */
public class StartStateActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "org.problets.helloworld.STARTSTATE";
    static int[] startState = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    private int counter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Can share layout among activities
        setContentView(R.layout.activity_main);

        (findViewById(R.id.topleft)).setOnClickListener(this);
        (findViewById(R.id.topcenter)).setOnClickListener(this);
        (findViewById(R.id.topright)).setOnClickListener(this);
        (findViewById(R.id.midleft)).setOnClickListener(this);
        (findViewById(R.id.midcenter)).setOnClickListener(this);
        (findViewById(R.id.midright)).setOnClickListener(this);
        (findViewById(R.id.bottomleft)).setOnClickListener(this);
        (findViewById(R.id.bottomcenter)).setOnClickListener(this);
        (findViewById(R.id.bottomright)).setOnClickListener(this);

        // Counter used to populate the start state
        counter = 1;

    }

    /**
     * Respond to the button that is pressed
     */
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.topleft) {
            updateButton(0);
        } else if (view.getId() == R.id.topcenter) {
            updateButton(1);
        } else if (view.getId() == R.id.topright) {
            updateButton(2);
        } else if (view.getId() == R.id.midleft) {
            updateButton(3);
        } else if (view.getId() == R.id.midcenter) {
            updateButton(4);
        } else if (view.getId() == R.id.midright) {
            updateButton(5);
        } else if (view.getId() == R.id.bottomleft) {
            updateButton(6);
        } else if (view.getId() == R.id.bottomcenter) {
            updateButton(7);
        } else if (view.getId() == R.id.bottomright) {
            updateButton(8);
        }

        if (isDone()) {
            // Can have a chain of parent-child activities - what about ActivityGroup?
            Intent intent = new Intent(this, MainActivity.class);

            // TO DO: Pass startState to MainActivity

            intent.putExtra(EXTRA_MESSAGE, startState);
            startActivity(intent);
        }
    }

    private void updateButton(int tileIndex) {
        if (startState[tileIndex] == -1) {
            startState[tileIndex] = counter;
            counter++;
            updateButtons();
        }
    }

    /**
     * Updates button faces with appropriate strings
     */
    private void updateButtons() {
        ((Button) findViewById(R.id.topleft)).setText(convert(startState[0]));
        ((Button) findViewById(R.id.topcenter)).setText(convert(startState[1]));
        ((Button) findViewById(R.id.topright)).setText(convert(startState[2]));
        ((Button) findViewById(R.id.midleft)).setText(convert(startState[3]));
        ((Button) findViewById(R.id.midcenter)).setText(convert(startState[4]));
        ((Button) findViewById(R.id.midright)).setText(convert(startState[5]));
        ((Button) findViewById(R.id.bottomleft)).setText(convert(startState[6]));
        ((Button) findViewById(R.id.bottomcenter)).setText(convert(startState[7]));
        ((Button) findViewById(R.id.bottomright)).setText(convert(startState[8]));

    }

    /**
     * Converts number to string, 0 to blank string
     */
    private String convert(int number) {
        if (number <= 0)
            return " ";
        else
            return Integer.toString(number);
    }

    /**
     * Returns false if more than one tile has not been specified.
     * If only one tile remains to be filled, it is initialized to 0 for blank
     * and true is returned
     */
    private boolean isDone() {
        // Last tile whose face value has not yet been specified
        int lastUnfilledTileIndex = -1;

        for (int index = 0; index < 9; index++) {
            if (startState[index] == -1) {
                // If no previous unfilled space was seen, update lastUnfilledTileIndex
                if (lastUnfilledTileIndex == -1)
                    lastUnfilledTileIndex = index;
                    // Otherwise, return false
                else
                    return false;
            }

        }

        // We have only one unfilled tile left
        startState[lastUnfilledTileIndex] = 0;
        return true;
    }
}