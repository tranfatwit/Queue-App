/* @formatter:off
 *
 * Dave Rosenberg
 * Comp 2000 - Data Structures
 * Lab: Queue App
 * Fall, 2021
 * 
 * Usage restrictions:
 * 
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 * 
 * Further, you may not post nor otherwise share this code with anyone other than
 * current students in my sections of this course. Violation of these usage
 * restrictions will be considered a violation of the Wentworth Institute of
 * Technology Academic Honesty Policy.
 *
 * Do not remove this notice.
 *
 * @formatter:on
 */

package edu.wit.scds.comp2000.queue.app.utilities ;

/**
 * A general purpose enumeration for representing the direction an entity is moving
 * along a TrainRoute.
 * <p>
 * NOTE: You may use this class, with or without modification, in your Comp 2000,
 * Queue application/Train Simulation solution. You must retain all authorship
 * comments. If you modify this, add your authorship and modification history tags
 * after the existing tags.
 *
 * @author Dave Rosenberg
 * @version 1.0.0 initial version
 * @version 1.1.0 added {@code reverse()}
 * @version 1.2.0 2021-10-30 add movable indication
 */
public enum Direction
    {

    /** default value; not movable */
    NOT_SPECIFIED ( "Not specified", false ),
    /** direction does not apply to this entity, not movable */
    NOT_APPLICABLE ( "Not applicable", false ),
    /** the entity has a permanent location; not movable */
    STATIONARY ( "Stationary", false ),
    /** the entity is associated with moving toward a hub as determined by the route;
     * typically applies to linear/end-to-end routes; movable */
    INBOUND ( "Inbound", true ),
    /** the entity is associated with moving away from a hub as determined by the route;
     * typically applies to linear/end-to-end routes; movable */
    OUTBOUND ( "Outbound", true ),
    /** the entity is associated with moving in a clockwise direction as determined by
     * the route - typically applies to circular routes; movable */
    CLOCKWISE ( "Clockwise", true ),
    /** the entity is associated with moving in a counter-clockwise direction as
     * determined by the route - typically applies to circular routes; movable */
    COUNTER_CLOCKWISE ( "Counter-clockwise", true );

    // instance variables

    private final String displayText ;  // user-friendly version for display
    private final boolean movable ;     // to simplify testing in Location

    /**
     * Set the user-friendly display text and whether the entity can move
     * 
     * @param textToDisplay
     *     user-friendly text
     * @param canMove
     *     flag to simplify determination of whether an entity can move
     */
    private Direction( String textToDisplay,
                       boolean canMove )
        {
        this.displayText = textToDisplay ;
        this.movable = canMove ;

        }	// end constructor


    /**
     * @return this Direction's display name
     */
    public String getDisplayText()
        {
        return this.displayText ;
        
        }	// end getDisplayText()

    
    /**
     * @return this Direction's movable attribute
     */
    public boolean isMovable()
        {
        return this.movable ;
        
        }   // end isMovable()
    
    
    // public utility methods


    /**
     * Parses a string representing a Direction. The string is matched against the
     * display text for each member of the enumeration in order of declaration. The
     * match is case-insensitive and is restricted to a substring of the display text
     * starting with the first character and ending with the number of characters
     * specified in the provided string.
     * <p>
     * NOTE: Substring matching will succeed on the first match, which for this
     * enumeration will not behave correctly when only the first character/a single
     * character string is provided.
     * 
     * @param directionText
     *     the text to parse
     * @return if successfully parsed, the corresponding enumeration element;
     *     otherwise null
     */
    public static Direction parse( String directionText )
        {
        Direction parsedDirection = null ;

        for ( Direction aDirection : Direction.values() )
            {
            int comparisonLength = Math.min( directionText.length(),
                                             aDirection.displayText.length() ) ;
            if ( directionText.equalsIgnoreCase(
                                aDirection.displayText.substring( 0,
                                                                  comparisonLength ) ) )
                {
                parsedDirection = aDirection ;

                break ;							// found one so done
                }
            }

        return parsedDirection ;
        
        }	// end parse()


    /**
     * For directions with a logical opposite, enables easily reverse()ing a Train.
     * 
     * @return the logically opposite direction if applicable, otherwise
     *     currentDirection
     */
    public Direction reverse()
        {
        switch ( this )
            {
            case NOT_SPECIFIED:
            case NOT_APPLICABLE:
            case STATIONARY:
                return this ;	// not movable so not reversible

            case INBOUND:
                return Direction.OUTBOUND ;

            case OUTBOUND:
                return Direction.INBOUND ;

            case CLOCKWISE:
                return Direction.COUNTER_CLOCKWISE ;

            case COUNTER_CLOCKWISE:
                return Direction.CLOCKWISE ;

            default:
                // there are no other possible cases
                throw new IllegalStateException( String.format( "unexpected instance: %s",
                                                                this.toString() ) ) ;
            }
        
        }	// end reverse()


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return this.displayText ;
        
        }	// end toString()


    /**
     * Test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( String[] args )
        {
        // display column headers
        System.out.printf( "%-5s %-20s %-20s %-20s %-10s %-20s %-20s%n",
                           "#",
                           "Name",
                           "Direction",
                           "Display Text",
                           "Movable",
                           "Parsed",
                           "Reverse" ) ;

        // display each element of the enumeration
        for ( Direction aDirection : Direction.values() )
            {
            System.out.printf( "%-5d %-20s %-20s %-20s %-10b %-20s %-20s%n",
                               aDirection.ordinal(),
                               aDirection.name(),
                               aDirection,
                               aDirection.displayText,
                               aDirection.movable,
                               Direction.parse( aDirection.displayText ),
                               aDirection.reverse() ) ;
            }

        // test the parser
        System.out.println( "\n----------\n" ) ;
        System.out.println( "parse(\"C\") returns " + Direction.parse( "C" ) ) ;
        System.out.println( "parse(\"Co\") returns " + Direction.parse( "Co" ) ) ;
        System.out.println( "parse(\"Not Spec\") returns " +
                            Direction.parse( "Not Spec" ) ) ;
        System.out.println( "parse(\"Not Specifically\") returns " +
                            Direction.parse( "Not Specifically" ) ) ;
        System.out.println( "parse(\"nonesuch\") returns " +
                            Direction.parse( "nonesuch" ) ) ;
        System.out.println( "parse(\"a very long string which may or may not match\") returns " +
                            Direction.parse( "a very long string which may or may not match" ) ) ;
        System.out.println( "parse(\"\") returns " + Direction.parse( "" ) ) ;

        // test comparisons and reverse()
        System.out.println( "\n----------\n" ) ;

        Direction in = Direction.INBOUND ;
        Direction alsoIn = Direction.INBOUND ;
        Direction out = Direction.OUTBOUND ;
        Direction notOut = out.reverse() ;
        Direction clockwise = Direction.CLOCKWISE ;
        Direction notClockwise = clockwise.reverse() ;
        System.out.printf( "in: %s%nalsoIn: %s%nout: %s%nnotOut: %s%nclockwise: %s%nnotClockwise: %s%n%n",
                           in,
                           alsoIn,
                           out,
                           notOut,
                           clockwise,
                           notClockwise ) ;
        System.out.println( "in == alsoIn is " + ( in == alsoIn ) ) ;
        System.out.println( "in.equals( alsoIn ) is " + ( in.equals( alsoIn ) ) ) ;
        System.out.println( "in == out is " + ( in == out ) ) ;
        System.out.println( "in.equals( out ) is " + ( in.equals( out ) ) ) ;
        System.out.println( "in == notOut is " + ( in == notOut ) ) ;
        System.out.println( "in.equals( notOut ) is " + ( in.equals( notOut ) ) ) ;

        }	// end main()

    }	// end enum Direction
