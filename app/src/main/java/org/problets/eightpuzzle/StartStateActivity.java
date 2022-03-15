package org.problets.eightpuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.problets.eightpuzzle.databinding.ActivityMainBinding;

/**
 * @author Amruth
 */
public class StartStateActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "org.problets.helloworld.STARTSTATE";
    static int[] startState = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    private ActivityMainBinding binding;
    private int counter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Can share layout among activities
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.topleft.setOnClickListener(this);
        binding.topcenter.setOnClickListener(this);
        binding.topright.setOnClickListener(this);
        binding.midleft.setOnClickListener(this);
        binding.midcenter.setOnClickListener(this);
        binding.midright.setOnClickListener(this);
        binding.bottomleft.setOnClickListener(this);
        binding.bottomcenter.setOnClickListener(this);
        binding.bottomright.setOnClickListener(this);

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
        binding.topleft.setText(convert(startState[0]));
        binding.topcenter.setText(convert(startState[1]));
        binding.topright.setText(convert(startState[2]));
        binding.midleft.setText(convert(startState[3]));
        binding.midcenter.setText(convert(startState[4]));
        binding.midright.setText(convert(startState[5]));
        binding.bottomleft.setText(convert(startState[6]));
        binding.bottomcenter.setText(convert(startState[7]));
        binding.bottomright.setText(convert(startState[8]));

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