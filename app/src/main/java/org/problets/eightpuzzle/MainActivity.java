package org.problets.eightpuzzle;

import org.problets.eightpuzzle.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class MainActivity extends Activity {
	public static final String EXTRA_MESSAGE = "org.problets.helloworld.MESSAGE";
	// private static final int GET_START_STATE = 0;
    // private int currentState[] = {1, 3, 5, 7, 0, 2, 4, 6, 8};
	private int currentState[] = null;
    private final int finalState[] = {1, 2, 3, 4, 0, 5, 6, 7, 8}; 
    private int attempts;
    private String attemptDescription;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Simple way to transfer data between activities - static class variables
		currentState = StartStateActivity.startState;
		/*
		if( currentState == null )
		{
			startActivityForResult( new Intent(), GET_START_STATE );
		}
		*/
		
		updateButtons( currentState );
		attempts = 0;
		attemptDescription = "";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	/** Respond to the button that is pressed */
	public void respond( View view ){
				
		switch( view.getId() )
		{
		case R.id.topleft: makeMove( 0 );
		break;
		case R.id.topcenter: makeMove( 1 );
		break;
		case R.id.topright: makeMove( 2 );
		break;
		case R.id.midleft: makeMove( 3 );
		break;
		case R.id.midcenter: makeMove( 4 );
		break;
		case R.id.midright: makeMove( 5 );
		break;
		case R.id.bottomleft: makeMove( 6 );
		break;
		case R.id.bottomcenter: makeMove( 7 ); 
		break;	
		case R.id.bottomright: makeMove( 8 );
		break;
		}
		// ((Button) view).setText( "" );
		
		// Increment the number of attempts
		attempts++;
		
		if( isDone() )
		{
			/*
			// WORKING VERSION - SET ASIDE TO TRY ALTERNATIVE
			Intent intent = new Intent(this, DisplayMessageActivity.class);

			String message =  "Congratulations, you took " + attempts + " attempts to solve the puzzle\n"
					+ attemptDescription;
			intent.putExtra(EXTRA_MESSAGE, message );
			startActivity( intent );
			*/
			
			Intent summaryIntent = new Intent(this, DisplayMessageActivity.class);
		    // Intent summaryIntent = new Intent( android.content.Intent.ACTION_SEND_MULTIPLE );
			summaryIntent.setType( "plain/text" );
			summaryIntent.putExtra( Intent.EXTRA_TEXT, "Congratulations!" );
			summaryIntent.putExtra( Intent.EXTRA_TEXT, "You took " + attempts + " attempts to solve the puzzle" );
			summaryIntent.putExtra( Intent.EXTRA_TEXT, "You moved 4 left" );
			summaryIntent.putExtra( Intent.EXTRA_TEXT, "You moved 5 right" );
			summaryIntent.putExtra( Intent.EXTRA_TEXT, "You moved 6 up" );
			summaryIntent.putExtra( Intent.EXTRA_TEXT, "You moved 7 down!" );
			startActivity( summaryIntent );
			
		}
	}

	/** Destination tile is touched. 
	 * Check if it is next to blank tile.
	 * If not, do nothing.
	 * If it is, swap destination with blank.
	 * updateButtons */
	private void makeMove( int destination )
	{
		int blankLocation = getBlankLocation();
		if( isNeighbor( destination, blankLocation ) )
		{
			currentState[blankLocation] = currentState[destination];
			currentState[destination] = 0;
			attemptDescription += currentState[blankLocation] + " was moved " 
					+ getDirection( destination, blankLocation )+ "\n";
			
			updateButtons( currentState );
		}
		else	
			attemptDescription += currentState[destination] + " was not moved\n"; 
					
	}
	
	/* destination is the location of the tile to be moved,
	 * source is the location of the blank space
	 * Returns the direction in which the tile was moved */
	private String getDirection( int destination, int source )
	{
		switch( destination - source )
		{
		case -1: return "right";
		case 1: return "left";
		case -3: return "down";
		case 3: return "up";
		default: return "";
		}
	}
	
	/** Returns the index of the blank tile */
	private int getBlankLocation()
	{
	int	 index;
	for( index = 0; index < 9; index++ )
		if( 0 == currentState[index] )
			return index;
	
	return -1;
	}
	
	/** Returns true if destination and source are neighbors and false otherwise */
	private boolean isNeighbor( int destination, int source )
	{
		switch( destination )
		{
		case 0: 
			switch( source )
			{
			case 1:
			case 3:
				return true;
			default:
				return false;
			}
		case 1:
			switch( source )
			{
			case 0:
			case 2:
			case 4:
				return true;
			default:
				return false;
			}
		case 2:
			switch( source )
			{
			case 1:
			case 5:
				return true;
			default:
				return false;
			}
		case 3:
			switch( source )
			{
			case 0:
			case 4:
			case 6:
				return true;
			default:
				return false;
			}
		case 4:
			switch( source )
			{
			case 1:
			case 3:
			case 5:
			case 7:
				return true;
			default:
				return false;
			}
		case 5:
			switch( source )
			{
			case 2:
			case 4:
			case 8:
				return true;
			default:
				return false;
			}
		case 6:
			switch( source )
			{
			case 3:
			case 7:
				return true;
			default:
				return false;
			}
		case 7:
			switch( source )
			{
			case 4:
			case 6:
			case 8:
				return true;
			default:
				return false;
			}
		case 8:
		default:	
			switch( source )
			{
			case 5:
			case 7:
				return true;
			default:
				return false;
			}
		}
	}
	
	/** Returns true if current state is same as final state */
	private boolean isDone()
	{
	   boolean done = false;	
	   for( int index = 0; index < 9; index++ )
		   if( currentState[index] != finalState[index] )
			   return false;
	   return true;
	}
	
	/** Updates button faces with appropriate strings */
	private void updateButtons( int [] currentState ){
		((Button) findViewById( R.id.topleft )).setText( convert( currentState[0] ) );
		((Button) findViewById( R.id.topcenter)).setText( convert( currentState[1] ) );
		((Button) findViewById( R.id.topright)).setText( convert( currentState[2] ) );
		((Button) findViewById( R.id.midleft )).setText( convert( currentState[3] ) );
		((Button) findViewById( R.id.midcenter)).setText( convert( currentState[4] ) );
		((Button) findViewById( R.id.midright)).setText( convert( currentState[5] ) );
		((Button) findViewById( R.id.bottomleft )).setText( convert( currentState[6] ) );
		((Button) findViewById( R.id.bottomcenter)).setText( convert( currentState[7] ) );
		((Button) findViewById( R.id.bottomright)).setText( convert( currentState[8] ) );
		
	}
	
	/** Converts number to string, 0 to blank string */
	private String convert( int number )
	{
		if( 0 == number )
			return " ";
		else
			return Integer.toString( number );
	}
}