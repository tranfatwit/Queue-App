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
 * Utility construct for a pair of corresponding minimum and maximum limits for a
 * parameter. This class is immutable; no getters are provided.
 * <p>
 * NOTE: You may use this class, with or without modification, in your Comp 2000,
 * Queue application/Train Simulation solution. You must retain all authorship
 * comments. If you modify this, add your authorship and modification history tags
 * after the existing tags.
 *
 * @author Dave Rosenberg
 * @version 1.0.0 2021-10-30 extracted from Configuration.java
 */
public final class PairedLimit
    {

    /** The minimum/lower limit (inclusive). */
    public final int minimum ;
    /** The maximum/upper limit (inclusive). */
    public final int maximum ;

    /**
     * Sets the minimum/lower and maximum/upper limits (inclusive).
     *
     * @param minimumValue
     *     the minimum/lower limit value
     * @param maximumValue
     *     the maximum/upper limit value
     */
    public PairedLimit( int minimumValue, int maximumValue )
        {
        this.minimum = minimumValue ;
        this.maximum = maximumValue ;

        }   // end constructor


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return String.format( ( this.minimum == this.maximum
                                    ? "%,d"
                                    : "%,d..%,d" ),
                              this.minimum, this.maximum ) ;

        }   // end toString()

    }   // end class PairedLimit