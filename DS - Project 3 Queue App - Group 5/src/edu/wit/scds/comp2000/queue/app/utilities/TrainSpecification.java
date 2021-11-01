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
 * Utility construct for Train specifications. This class is immutable; no getters
 * are provided.
 * <p>
 * NOTE: You may use this class, with or without modification, in your Comp 2000,
 * Queue application/Train Simulation solution. You must retain all authorship
 * comments. If you modify this, add your authorship and modification history tags
 * after the existing tags.
 *
 * @author Dave Rosenberg
 * @version 1.0.0 2021-10-30 extracted from Configuration.java
 */
public final class TrainSpecification
    {

    /** The initial location on the Route: 1..RouteSpec.length */
    public final int location ;
    /** The initial direction of travel as defined by Direction; typically INBOUND or
     * OUTBOUND for linear/end-to-end routes, or CLOCKWISE or COUNTER_CLOCKWISE for 
     * circular routes */
    public final Direction direction ;
    /** The maximum number of Passengers the Train can hold simultaneously */
    public final int capacity ;

    
    /**
     * Sets the initial location, direction, and the capacity of a Train
     *
     * @param initialPositionOnRoute
     *     1..RouteSpec.length
     * @param initialDirection
     *     INBOUND or OUTBOUND for linear/end-to-end routes, CLOCKWISE or
     *     COUNTER_CLOCKWISE for circular routes
     * @param passengerCapacity
     *     a positive, non-zero value
     */
    public TrainSpecification( int initialPositionOnRoute,
                               Direction initialDirection,
                               int passengerCapacity )
        {
        this.location = initialPositionOnRoute ;
        this.direction = initialDirection ;
        this.capacity = passengerCapacity ;

        }   // end constructor


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return String.format( "%s at location %,d with capacity %,d",
                              this.direction.toString(),
                              this.location,
                              this.capacity ) ;

        }   // end toString()

    }   // end class TrainSpecification