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

import java.io.File ;
import java.io.FileNotFoundException ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.Scanner ;

/**
 * Configuration file parser.
 * <p>
 * NOTE: This parser assumes that all specifications in the configuration file are
 * valid (e.g. positive, non-zero whole numbers are greater than 0). Future
 * enhancements to this class could enforce the documented constraints and/or provide
 * reasonable defaults to override invalid specifications. As an alternative, it can
 * throw an exception tbd.
 * <p>
 * NOTE: You may use this class, with or without modification, in your Comp 2000,
 * Queue application/Train Simulation solution. You must retain all authorship
 * comments. If you modify this, add your authorship and modification history tags
 * after the existing tags.
 *
 * @author Dave Rosenberg
 * @version 1.0.0 initial version
 * @version 1.1.0 track changes to the configuration file layout
 * @version 1.2.0 add {@code toString()}
 * @version 2.0.0 2021-10-30 move PairedLimit, RouteSpec, and TrainSpec to separate classes
 */
public final class Configuration
    {

    // Parameter name:value mapping
    private HashMap<String, String> parameters ;

    // indices into the PairedLimits for Passengers
    /**
     * Index into the array of PairedLimits returned by getPassengers() for the
     * number of Passengers to create during the creation/initial population phase of
     * the TrainSimulation.
     */
    public final static int PASSENGERS_INITIAL = 0 ;
    /**
     * Index into the array of PairedLimits returned by getPassengers() for the
     * number of Passengers to create during each clock tick/iteration of the run
     * phase of the TrainSimulation.
     */
    public final static int PASSENGERS_PER_TICK = 1 ;


    /**
     * Sets up the instance with the default configuration filename
     * (./data/TrainSimulation.config) in the data folder. The configuration file is
     * opened, read, then closed. Parsing of the individual specifications is delayed
     * until the corresponding getXxx() method is invoked.
     * 
     * @throws FileNotFoundException
     *     if the default configuration file (TrainSimulation.config) is missing or
     *     can't be opened for reading
     */
    public Configuration() throws FileNotFoundException
        {
        // the default configuration file is in the data folder
        this( "./data/TrainSimulation.config" ) ;
        
        }	// end Configuration no-arg constructor


    /**
     * Sets up the instance with the contents of the specified configuration
     * filename. The configuration file is opened, read, then closed. Parsing of the
     * individual specifications is delayed until the corresponding getXxx() method
     * is invoked.
     * 
     * @param configurationFilename
     *     including a path specification, typically the data folder; if a path is
     *     specified, it must be relative to the default location (e.g.
     *     "./data/TrainSimulation.config")
     *     <p>
     *     Note: Absolute paths will work but are not acceptable for this lab.
     * @throws FileNotFoundException
     *     if the configuration file is missing or can't be opened for reading
     */
    public Configuration( String configurationFilename ) throws FileNotFoundException
        {
        this.parameters = new HashMap<>() ;

        try ( Scanner input = new Scanner( new File( configurationFilename ) ) ; )
            {

            String inputLine = null ;	// whole line buffer
            String[] inputLineParts = null ;   // parameter specification and comment
                                               // - both are optional
            String[] parameterParts = null ;   // [0] name, [1] value(s)
            String parameterName = null ;
            String parameterValue = null ;

            while ( input.hasNextLine() )
                {
                inputLine = input.nextLine().trim() ;
                if ( ( inputLine.length() == 0 ) ||
                     ( inputLine.charAt( 0 ) == '#' ) )		// just a comment
                    {
                    continue ;
                    }

                inputLineParts = inputLine.split( "#" ) ;
                if ( inputLineParts.length == 0 )		// null specification
                    {
                    continue ;
                    }

                parameterParts = inputLineParts[ 0 ].split( "=" ) ;
                if ( parameterParts.length == 0 )		// null specification
                    {
                    continue ;
                    }

                // we have a parameter name = value(s) pair
                parameterName = parameterParts[ 0 ].trim() ;
                parameterValue = parameterParts.length == 1
                                    ? ""
                                    : parameterParts[ 1 ].trim() ;

                // add the parameter to the map - replaces any previous specification
                this.parameters.put( parameterName, parameterValue ) ;
                }	// end while()

            }   // end try
        
        }	// end Configuration 1-arg constructor


    /**
     * Parses the initial and per-tick limits (min, max) for the number of Passengers
     * to instantiate
     * 
     * @return an array containing the initial and per-tick limits
     */
    public PairedLimit[] getPassengers()
        {
        PairedLimit[] passengerParameters = new PairedLimit[ 2 ] ;	// initial and
                                                                  	// per-tick

        // retrieve the specification string and break it into its comma-separated
        // parts
        String[] passengerSpecification = this.parameters.get( "passengers" )
                                                         .split( "," ) ;

        // initial limits are separated by a colon (:)
        String[] specificationLimits = passengerSpecification[ PASSENGERS_INITIAL ]
                                            .trim()
                                            .split( ":" ) ;
        
        int minimumLimit = Integer.parseInt( specificationLimits[ 0 ].trim() ) ;
        int maximumLimit = Integer.parseInt( specificationLimits[ 1 ].trim() ) ;
        
        passengerParameters[ PASSENGERS_INITIAL ] = new PairedLimit( minimumLimit,
                                                                     maximumLimit ) ;

        // per tick limits are separated by a colon (:)
        specificationLimits = passengerSpecification[ PASSENGERS_PER_TICK ]
                                            .trim()
                                            .split( ":" ) ;
        
        minimumLimit = Integer.parseInt( specificationLimits[ 0 ].trim() ) ;
        maximumLimit = Integer.parseInt( specificationLimits[ 1 ].trim() ) ;
        
        passengerParameters[ PASSENGERS_PER_TICK ] = new PairedLimit( minimumLimit,
                                                                      maximumLimit ) ;

        return passengerParameters ;
        
        }	// end getPassengers()


    /**
     * Parses the style and length of the Route
     * 
     * @return the style and length of the Route
     */
    public RouteSpecification getRoute()
        {
        // the style and length specifications are separated by a colon (:)
        String[] routeSpecifications = this.parameters.get( "route" ).split( ":" ) ;

        RouteStyle routeStyle = RouteStyle.parse( routeSpecifications[ 0 ] ) ;
        int routeLength = Integer.parseInt( routeSpecifications[ 1 ].trim() ) ;

        return new RouteSpecification( routeStyle, routeLength ) ;
        
        }	// end getRoute()


    /**
     * Parses the value to use to seed the random number generator. The default is 0,
     * which should be treated as a sentinel value to either (1) not seed the random
     * number generator or (2) seed it with the current time.
     * 
     * @return the specified seed value for the random number generator or 0 if none
     *     specified
     */
    public int getSeed()
        {
        return Integer.parseInt( this.parameters.getOrDefault( "seed", "0" ).trim() ) ;
        
        }	// end getSeed()


    /**
     * Parses the comma-separated list of Station positions along the route
     * 
     * @return the positions of the Stations along the route, where each position is in
     *     1..RouteSpec.length, inclusive
     */
    public int[] getStations()
        {
        // the specification is a comma-separated list of positive, non-zero whole
        // numbers
        String[] stationSpecifications = this.parameters.get( "stations" ).split( "," ) ;
        int[] stationLocations = new int[ stationSpecifications.length ] ;

        // convert each Station location from text to numeric form
        for ( int i = 0 ; i < stationSpecifications.length ; i++ )
            {
            stationLocations[ i ] = Integer.parseInt( stationSpecifications[ i ].trim() ) ;
            }	// end for()

        return stationLocations ;
        
        }	// end getStations()


    /**
     * Parses the number of clock ticks for the TrainSimulation loop
     * 
     * @return the number of clock ticks
     */
    public int getTicks()
        {
        return Integer.parseInt( this.parameters.get( "ticks" ).trim() ) ;
        
        }	// end getTicks()


    /**
     * Parses the comma-separate list of Train specifications
     * 
     * @return the locations, directions, and capacities of the Trains, where each
     *     location is in 1..RouteSpec.length, inclusive, and the capacity is a
     *     positive, non-zero whole number
     */
    public TrainSpecification[] getTrains()
        {
        // the list of Train specifications is comma-separated
        String[] trainParameters = this.parameters.get( "trains" ).split( "," ) ;

        // allocate an array large enough to hold all of the Train specifications
        TrainSpecification[] trainSpecifications =
                                        new TrainSpecification[ trainParameters.length ] ;

        // parse each Train specification (location, direction, capacity)
        for ( int i = 0 ; i < trainParameters.length ; i++ )
            {
            // each Train specification is colon-delimited (:)
            String[] trainSpecificationParts = trainParameters[ i ].split( ":" ) ;

            // location, direction, capacity
            trainSpecifications[ i ] = new TrainSpecification(
                              Integer.parseInt( trainSpecificationParts[ 0 ].trim() ),
                              Direction.parse( trainSpecificationParts[ 1 ] ),
                              Integer.parseInt( trainSpecificationParts[ 2 ].trim() ) ) ;
            }	// end for()

        return trainSpecifications ;
        
        }	// end getTrains()


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        StringBuilder buffer = new StringBuilder() ;

        // General
        buffer.append( String.format( "Ticks: %,d%n", getTicks() ) ) ;
        buffer.append( String.format( "Seed: %,d%n", getSeed() ) ) ;

        // Route
        buffer.append( String.format( "Route: %s%n", getRoute() ) ) ;

        // Stations
        buffer.append( String.format( "Stations: %s%n",
                                      Arrays.toString( getStations() ) ) ) ;

        // Trains
        buffer.append( String.format( "Trains:%n" ) ) ;
        for ( TrainSpecification aTrainSpec : getTrains() )
            {
            buffer.append( String.format( "\t%s%n", aTrainSpec ) ) ;
            }	// end for()

        // Passengers
        buffer.append( String.format( "Passengers: %s%n",
                                      Arrays.toString( getPassengers() ) ) ) ;

        return buffer.toString() ;
        
        }	// end toString()


    /**
     * Test driver
     * 
     * @param args
     *     -unused-
     * @throws FileNotFoundException
     *     if TrainSimulation.config is missing or can't be opened for reading
     */
    public static void main( String[] args ) throws FileNotFoundException
        {
        Configuration theConfiguration = new Configuration() ;

        System.out.printf( "Individual specifications:%n" ) ;

        // General
        System.out.printf( "Ticks: %,d%n", theConfiguration.getTicks() ) ;
        System.out.printf( "Seed: %,d%n", theConfiguration.getSeed() ) ;

        // Route
        System.out.printf( "Route: %s%n", theConfiguration.getRoute() ) ;

        // Stations
        System.out.printf( "Stations: %s%n",
                            Arrays.toString( theConfiguration.getStations() ) ) ;

        // Trains
        System.out.printf( "Trains:%n" ) ;
        for ( TrainSpecification aTrainSpecification : theConfiguration.getTrains() )
            {
            System.out.printf( "\t%s%n", aTrainSpecification ) ;
            }	// end for()

        // Passengers
        System.out.printf( "Passengers: %s%n",
                            Arrays.toString( theConfiguration.getPassengers() ) ) ;

        // test toString()
        System.out.printf( "%n----------%ntoString():%n%n" ) ;
        System.out.printf( "%s%n", theConfiguration ) ;
        
        }	// end main()

    }	// end class Configuration