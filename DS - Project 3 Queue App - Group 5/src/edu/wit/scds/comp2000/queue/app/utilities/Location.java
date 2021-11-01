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

import edu.wit.scds.comp2000.queue.app.TrainRoute ;

import java.io.FileNotFoundException ;
import java.util.Objects ;

/**
 * Generic positional information for an entity
 * <p>
 * NOTE: You may use this class, with or without modification, in your Comp 2000,
 * Queue application/Train Simulation solution. You must retain all authorship
 * comments. If you modify this, add your authorship and modification history tags
 * after the existing tags.
 *
 * @author Dave Rosenberg
 * @version 1.0.0 initial version
 * @version 1.1.0 2021-10-30
 *     <ul>
 *     <li>revise logic in </li>
 *     <li>change {@code Direction.NOT_SPECIFIED} to {@code Direction.NOT_APPLICABLE}
 *     in 1-arg constructor</li>
 *     <li>add {@code hashCode()}</li>
 *     <li>reorder methods to better organize them</li>
 *     </ul>
 */
public final class Location implements Comparable<Location>
    {

    // class-wide/shared information
    private static int nextId = 1 ; // enables automatic id assignment

    // per-instance fields
    private final int id ;          // unique id for this location

    private TrainRoute onRoute ;    // enable callback to TrainRoute information
                                    // FUTURE support multiple routes, make this an
                                    // array or create TrainSystem class
    private int atPosition ;		// TrainRoute-centric position in 1..TrainRoute.length
    private Direction inDirection ; // TrainRoute-centric direction

    /**
     * Sets up the new instance referencing the provided TrainRoute, a position of 1
     * (beginning of the TrainRoute), and OUTBOUND for a LINEAR route, CLOCKWISE for
     * a CIRCULAR route, NOT_APPLICABLE otherwise
     *
     * @param theRoute
     *     the TrainRoute on which this Location exists
     */
    public Location( TrainRoute theRoute )
        {
        this( theRoute, 
              1,
              switch ( theRoute.getStyle() )
                  {
                  case LINEAR -> Direction.OUTBOUND ;
                  case CIRCULAR -> Direction.CLOCKWISE ;
                  default -> Direction.NOT_APPLICABLE ;
                  } ) ;
        
        }	// end 1-arg constructor


    /**
     * Sets up the new instance referencing the provided TrainRoute, position, and
     * direction
     *
     * @param theRoute
     *     the TrainRoute on which this Location exists
     * @param initialPosition
     *     the position on the TrainRoute in the range 1..theRoute.length
     * @param initialDirection
     *     the direction in which this entity will travel
     */
    public Location( TrainRoute theRoute,
                     int initialPosition,
                     Direction initialDirection )
        {
        this.id = Location.nextId++ ;

        this.onRoute = theRoute ;
        this.atPosition = initialPosition ;
        this.inDirection = initialDirection ;
        
        }	// end 3-arg constructor


    /**
     * @return the entity's direction
     */
    public Direction getDirection()
        {
        return this.inDirection ;
        
        }	// end getDirection()


    /**
     * @return the id
     */
    public int getId()
        {
        return this.id ;
        
        }	// end getId()


    /**
     * @return the entity's position on the route
     */
    public int getPosition()
        {
        return this.atPosition ;
        
        }	// end getPosition()


    /**
     * @return the entity's route
     */
    public TrainRoute getRoute()
        {
        return this.onRoute ;
        
        }	// end getRoute()


    /**
     * @param newDirection
     *     the new direction for this entity to travel
     */
    public void setDirection( Direction newDirection )
        {
        this.inDirection = newDirection ;
        
        }   // end setDirection()


    /**
     * @param newPosition
     *     the new position along the route for this entity
     */
    public void setPosition( int newPosition )
        {
        this.atPosition = newPosition ;
        
        }   // end setPosition()


    /**
     * @param newRoute
     *     the new route for this entity
     */
    public void setRoute( TrainRoute newRoute )
        {
        this.onRoute = newRoute ;
        
        }   // end setRoute()


    /**
     * Tests for position at either end of the route and moving toward that end
     *
     * @return true if in position 1 or route length; false otherwise
     */
    public boolean isAtEnd()
        {
        if ( !this.inDirection.isMovable() )// non-moving entity - just check for position
            {
            return ( this.atPosition == 1 ) ||
                   ( this.atPosition == this.onRoute.getLength() ) ;
            }
        
        if ( this.onRoute.getStyle() == RouteStyle.LINEAR )
            {
            return ( ( this.atPosition == 1 ) &&
                     ( this.inDirection == Direction.INBOUND ) ) ||
                   ( ( this.atPosition == this.onRoute.getLength() ) &&
                     ( this.inDirection == Direction.OUTBOUND ) ) ;
            
            }	// end if( linear )
        
        if ( this.onRoute.getStyle() == RouteStyle.CIRCULAR )
            {
            return ( ( this.atPosition == 1 ) &&
                     ( this.inDirection == Direction.COUNTER_CLOCKWISE ) ) ||
                   ( ( this.atPosition == this.onRoute.getLength() ) &&
                     ( this.inDirection == Direction.CLOCKWISE ) ) ;
            
            }	// end if( circular )
        
        throw new UnsupportedOperationException( String.format( "can't determine if at end: %s",
                                                                this.toString() ) ) ;
        
        }	// end isAtEnd()


//    /**
//     * Convenience method to check if the instance and argument locations are the
//     * same
//     *
//     * @param otherLocation
//     *     another Location to test for sameness
//     * @return true if the two Locations are the same, false otherwise
//     */
//    public boolean isAt( Location otherLocation )
//        {
//        return this.compareTo( otherLocation ) == 0 ;
//        
//        }   // end isAt()


    /**
     * Updates the location to reflect moving one position in the current direction,
     * reversing direction (linear) or wrapping (circular) if at an 'end'.
     */
    public void move()
        {
        if ( !this.inDirection.isMovable() )    // only care about movable entities
            {
            return ;
            }
        
        if ( ( this.onRoute.getStyle() == RouteStyle.LINEAR ) ||
             ( this.onRoute.getStyle() == RouteStyle.CIRCULAR ) )
            {
            if ( isAtEnd() )
                {
                if ( this.onRoute.getStyle() == RouteStyle.LINEAR )
                    {
                    reverse() ;
                    }
                else    // CIRCULAR
                    {
                    this.atPosition = this.atPosition == 1
                        ? this.onRoute.getLength()
                        : 1 ;
                    }
                }
            else	// not at an end
                {
                if ( ( this.inDirection == Direction.OUTBOUND ) ||
                     ( this.inDirection == Direction.CLOCKWISE ) )	// moving 'up'
                    {
                    this.atPosition++ ;
                    }
                else	// moving 'down'
                    {
                    this.atPosition-- ;
                    }
                }	// end if/else( at end )
            }	// end if/else( LINEAR or CIRCULAR )
        
        }	// end move()


    /**
     * Reverses the direction of travel
     */
    public void reverse()
        {
        this.inDirection = this.inDirection.reverse() ;
        
        }   // end reverse()


    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo( Location otherLocation )
        {
        // same route? compare positions on the route
        if ( this.onRoute == otherLocation.onRoute )
            {
            return this.atPosition - otherLocation.atPosition ;
            }
        
        // FUTURE different routes - need to build a graph of the train system to calculate
        throw new UnsupportedOperationException(
                            String.format( "comparing locations across routes (%s, %s)",
                                            this.onRoute,
                                            otherLocation.onRoute ) ) ;
        
        }   // end compareTo()


    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object otherObject )
        {
        if ( this == otherObject )  // they're the same instance
            {
            return true ;
            }

        if ( otherObject == null )  // nothing to compare to
            {
            return false ;
            }

        if ( otherObject instanceof Location )  // can only be equal if they're the
                                                // same class
            {
            Location otherLocation = (Location) otherObject ;

            return this.onRoute.equals( otherLocation.onRoute ) &&
                   ( this.atPosition == otherLocation.atPosition ) ;
            }

        return false ;
        
        }   // end equals()


    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
        {
        return Objects.hash( this.onRoute, this.atPosition ) ;
        
        }   // end hashCode()


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return String.format( "%s at position %,d on %s",
                              this.inDirection.toString(),
                              this.atPosition,
                              this.onRoute.toString() ) ;
        
        }	// end toString()
    

    /**
     * Test driver
     *
     * @param args
     *     -unused -
     * @throws FileNotFoundException
     *     {@link Configuration#Configuration()}
     */
    @SuppressWarnings( "unlikely-arg-type" )
    public static void main( String[] args ) throws FileNotFoundException
        {
        Configuration theConfig = new Configuration() ;
        TrainRoute aRoute = new TrainRoute( theConfig.getRoute() ) ;
        TrainRoute anotherRoute = new TrainRoute( theConfig.getRoute() ) ;

        Location where = new Location( aRoute ) ;
        Location here = new Location( aRoute,
                                      aRoute.getLength() / 2,
                                      Direction.OUTBOUND ) ;
        Location alsoHere = new Location( aRoute,
                                          aRoute.getLength() / 2,
                                          Direction.INBOUND ) ;
        Location elsewhere = new Location( anotherRoute,
                                           anotherRoute.getLength() / 2,
                                           Direction.INBOUND ) ;
        Location couldBeAStation = new Location( anotherRoute,
                                                 Math.min( anotherRoute.getLength(),
                                                           3 ),
                                                 Direction.STATIONARY ) ;

        // FUTURE - tests are poorly organized and barely commented - redo
        System.out.printf( "%s: %s%n", aRoute, theConfig.getRoute() ) ;
        System.out.printf( "%s: %s%n", "where", where ) ;
        System.out.printf( "%s: %s%n", "here", here ) ;
        System.out.printf( "%s: %s%n", "alsoHere", alsoHere ) ;
        System.out.printf( "%s: %s%n", "elsewhere", elsewhere ) ;
        System.out.printf( "%s: %s%n", "couldBeAStation", couldBeAStation ) ;

        where.reverse() ;
        System.out.printf( "%n%s: (reversed direction) %s%n", "where", where ) ;
        couldBeAStation.reverse() ;
        System.out.printf( "%s: (reversed direction) %s%n",
                           "couldBeAStation",
                           couldBeAStation ) ;

        System.out.println() ;

        Location aliasHere = here ;
        System.out.printf( "%s: %b%n",
                           "here.equals( here )",
                           here.equals( here ) ) ;
        System.out.printf( "%s: %b%n",
                           "here == here",
                           ( here == aliasHere ) ) ;
        System.out.printf( "%s: %b%n",
                           "here.equals( alsoHere )",
                           here.equals( alsoHere ) ) ;
        System.out.printf( "%s: %b%n",
                           "here == alsoHere",
                           ( here == alsoHere ) ) ;
        System.out.printf( "%s: %b%n",
                           "where.equals( here )",
                           where.equals( here ) ) ;
        System.out.printf( "%s: %b%n",
                           "where == here",
                           ( where == here ) ) ;
        System.out.printf( "%s: %b%n",
                           "alsoHere.equals( elsewhere )",
                           alsoHere.equals( elsewhere ) ) ;
        System.out.printf( "%s: %b%n",
                           "here.equals( aRoute )",
                           here.equals( aRoute ) ) ;

        System.out.println() ;

        Location endAndMoveTest = new Location( anotherRoute,
                                                1,
                                                Direction.OUTBOUND ) ;
        System.out.println( "Linear route:" ) ;

        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           endAndMoveTest.isAtEnd() ) ;

        endAndMoveTest.atPosition = endAndMoveTest.getRoute().getLength() ;
        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           endAndMoveTest.isAtEnd() ) ;

        endAndMoveTest.atPosition /= 2 ;				// roughly the middle
        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           endAndMoveTest.isAtEnd() ) ;

        endAndMoveTest.atPosition = 1 ;
        endAndMoveTest.inDirection = Direction.INBOUND ;

        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           endAndMoveTest.isAtEnd() ) ;

        endAndMoveTest.atPosition = endAndMoveTest.getRoute().getLength() ;
        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           endAndMoveTest.isAtEnd() ) ;

        endAndMoveTest.atPosition /= 2 ;				// roughly the middle
        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           endAndMoveTest.isAtEnd() ) ;

        System.out.println() ;

        endAndMoveTest.atPosition = 1 ;
        endAndMoveTest.inDirection = Direction.INBOUND ;

        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        endAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           endAndMoveTest ) ;

        endAndMoveTest.atPosition = endAndMoveTest.getRoute().getLength() ;
        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        endAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           endAndMoveTest ) ;

        endAndMoveTest.atPosition /= 2 ;				// roughly the middle
        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        endAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           endAndMoveTest ) ;

        endAndMoveTest.reverse() ;						// reverse direction
        endAndMoveTest.atPosition = 1 ;
        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        endAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           endAndMoveTest ) ;

        endAndMoveTest.atPosition = endAndMoveTest.getRoute().getLength() ;
        endAndMoveTest.inDirection = Direction.INBOUND ;

        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        endAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           endAndMoveTest ) ;

        endAndMoveTest.atPosition /= 2 ;				// roughly the middle
        endAndMoveTest.inDirection = Direction.OUTBOUND ;
        System.out.printf( "%s: %s%n",
                           "endAndMoveTest",
                           endAndMoveTest ) ;
        endAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           endAndMoveTest ) ;

        System.out.println() ;
        System.out.println( "Circular route:" ) ;

        TrainRoute circularRoute =
                        new TrainRoute( new RouteSpecification( RouteStyle.CIRCULAR,
                                                                    20 ) ) ;
        Location circularEndAndMoveTest = new Location( circularRoute,
                                                        1,
                                                        Direction.CLOCKWISE ) ;

        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           circularEndAndMoveTest.isAtEnd() ) ;

        circularEndAndMoveTest.atPosition = circularEndAndMoveTest.getRoute()
                                                                  .getLength() ;
        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           circularEndAndMoveTest.isAtEnd() ) ;

        circularEndAndMoveTest.atPosition /= 2 ;		// roughly the middle
        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           circularEndAndMoveTest.isAtEnd() ) ;

        circularEndAndMoveTest.atPosition = 1 ;
        circularEndAndMoveTest.inDirection = Direction.COUNTER_CLOCKWISE ;

        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           circularEndAndMoveTest.isAtEnd() ) ;

        circularEndAndMoveTest.atPosition = circularEndAndMoveTest.getRoute()
                                                                  .getLength() ;
        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           circularEndAndMoveTest.isAtEnd() ) ;

        circularEndAndMoveTest.atPosition /= 2 ;		// roughly the middle
        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        System.out.printf( "\tis at end: %b%n",
                           circularEndAndMoveTest.isAtEnd() ) ;

        System.out.println() ;

        circularEndAndMoveTest.atPosition = 1 ;
        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        circularEndAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           circularEndAndMoveTest ) ;

        circularEndAndMoveTest.atPosition = circularEndAndMoveTest.getRoute()
                                                                  .getLength() ;
        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        circularEndAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           circularEndAndMoveTest ) ;

        circularEndAndMoveTest.atPosition /= 2 ;		// roughly the middle
        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        circularEndAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           circularEndAndMoveTest ) ;

        circularEndAndMoveTest.reverse() ;				// reverse direction
        circularEndAndMoveTest.atPosition = 1 ;
        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        circularEndAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           circularEndAndMoveTest ) ;

        circularEndAndMoveTest.atPosition = circularEndAndMoveTest.getRoute()
                                                                  .getLength() ;
        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        circularEndAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           circularEndAndMoveTest ) ;

        circularEndAndMoveTest.atPosition = circularEndAndMoveTest.getRoute()
                                                                  .getLength() /
                                            2 ;
        // roughly the middle
        System.out.printf( "%s: %s%n",
                           "circularEndAndMoveTest",
                           circularEndAndMoveTest ) ;
        circularEndAndMoveTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           circularEndAndMoveTest ) ;
        
        
        // test non-moving entities
        Location nonMovingTest = new Location( anotherRoute, 1, Direction.STATIONARY ) ;
        System.out.printf( "%n%s: %s%n",
                           "non-movingEndAndMoveTest",
                           nonMovingTest ) ;
        nonMovingTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           nonMovingTest ) ;
        nonMovingTest.reverse() ;
        System.out.printf( "\treversed: %s%n",
                           nonMovingTest ) ;
        
        nonMovingTest.atPosition = anotherRoute.getLength() ;
        System.out.printf( "%s: %s%n",
                           "non-movingEndAndMoveTest",
                           nonMovingTest ) ;
        nonMovingTest.move() ;
        System.out.printf( "\tmoved to: %s%n",
                           nonMovingTest ) ;
        nonMovingTest.reverse() ;
        System.out.printf( "\treversed: %s%n",
                           nonMovingTest ) ;

        }	// end test driver main()

    }	// end class Location
