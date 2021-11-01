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
 * Utility construct for Route specifications. This class is immutable; no getters
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
public final class RouteSpecification
    {

    /** The style of the Route, typically LINEAR or CIRCULAR, as defined by RouteStyle */
    public final RouteStyle style ;
    /** The length of the Route in units; positive, non-zero */
    public final int length ;

    /**
     * Sets the style and length for the Route.
     *
     * @param routeStyle
     *     typically LINEAR or CIRCULAR
     * @param routeLength
     *     in positive, non-zero units
     */
    public RouteSpecification( RouteStyle routeStyle, int routeLength )
        {
        this.style = routeStyle ;
        this.length = routeLength ;

        }   // end constructor


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return String.format( "%s with length %,d",
                              this.style.toString(),
                              this.length ) ;

        }   // end toString()

    }   // end inner class RouteSpecification