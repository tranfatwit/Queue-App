
package edu.wit.scds.comp2000.queue.app ;

import edu.wit.scds.comp2000.queue.app.utilities.Direction ;

import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.Queue ;

/**
 * Class to manage statistics for train simulation. Current implementation calculates
 * statistics using data from only passengers who completed their journey. Future
 * implementation will take statistics of passenger still on platform and train into
 * consideration.
 *
 * @author Fabio Tran
 * @version 1.0.0 2021-11-03 Initial implementation
 */
public class Statistics
    {

    /** indicates that the time has no meaningful value */
    public static int UNSPECIFIED = -1 ;

    /**
     * stores how many passengers are still at any station or train at the end of the
     * simulation
     */
    private int stillAtStations ;
    private int stillOnTrains ;

    private ArrayList<Passenger> listOfPassengers ; // stores passengers that
                                                    // completed their journeys

    /** stores valid wait, ride, and total times */
    private ArrayList<Integer> waitTimes ;
    private ArrayList<Integer> rideTimes ;
    private ArrayList<Integer> totalTimes ;

    /** stores wait time statistics */
    private int minWaitTime ;
    private int maxWaitTime ;
    private int medWaitTime ;
    private int avgWaitTime ;

    /** stores ride time statistics */
    private int minRideTime ;
    private int maxRideTime ;
    private int medRideTime ;
    private int avgRideTime ;

    /** stores total time statistics */
    private int minTotalTime ;
    private int maxTotalTime ;
    private int medTotalTime ;
    private int avgTotalTime ;

    /**
     * no-arg constructor
     */
    public Statistics()
        {
        // initialize fields that holds simulation statistics

        this.stillAtStations = UNSPECIFIED ;
        this.stillOnTrains = UNSPECIFIED ;

        this.listOfPassengers = new ArrayList<>() ;
        this.waitTimes = new ArrayList<>() ;
        this.rideTimes = new ArrayList<>() ;
        this.totalTimes = new ArrayList<>() ;

        this.minWaitTime = UNSPECIFIED ;
        this.maxWaitTime = UNSPECIFIED ;
        this.medWaitTime = UNSPECIFIED ;
        this.avgWaitTime = UNSPECIFIED ;

        this.minRideTime = UNSPECIFIED ;
        this.maxRideTime = UNSPECIFIED ;
        this.medRideTime = UNSPECIFIED ;
        this.avgRideTime = UNSPECIFIED ;

        this.minTotalTime = UNSPECIFIED ;
        this.maxTotalTime = UNSPECIFIED ;
        this.medTotalTime = UNSPECIFIED ;
        this.avgTotalTime = UNSPECIFIED ;

        } // end constructor


    /**
     * Calculates how many passengers are still at stations at end of the simulation
     *
     * @param stations
     *     array of stations in the simulation
     */
    public void calculateStillAtStations( Station[] stations )
        {
        for ( Station station : stations )
            {
            HashMap<Direction, Queue<Passenger>> platforms = station.getPlatforms() ;

            // goes through each platform and totals the amount of passengers still
            // waiting at stations
            for ( Queue<Passenger> platform : platforms.values() )
                {
                this.stillAtStations = this.stillAtStations + platform.size() ;
                } // end for

            } // end for

        } // end calculateStillAtStations()


    /**
     * Calculates how many passengers are still on trains at end of the simulation
     *
     * @param trains
     *     array of trains in the simulation
     */
    public void calculateStillOnTrains( Train[] trains )
        {
        for ( Train train : trains )
            {
            this.stillOnTrains = this.stillOnTrains + train.getPassengerCount() ;
            } // end for

        } // end calculateStillOnTrains()


    /**
     * Adds passenger to list of passengers
     *
     * @param passenger
     *     passenger to add to list
     */
    public void updateListOfPassengers( Passenger passenger )
        {
        this.listOfPassengers.add( passenger ) ;

        } // end updateListOfPassengers()


    /**
     * Displays all statistics into string form
     *
     * @return string
     */
    public String results()
        {
        // System.out.println( this.listOfPassengers.size() ) ; // DEBUG 
        
        // TODO uncomment code below once Train.java is fully implemented.
        
        // assigns all field values their appropriate value 
        // calculateWaitTimeStats() ;
        // calculateRideTimeStats() ;
        // calculateTotalTimeStats() ;

        // returns values in string format
        return String.format( "Wait Time Statistics:%n    Minimum: %s, Maximum: %s, Median: %s, Average: %s%n" +
                              "Ride Time Statistics:%n    Minimum: %s, Maximum: %s, Median: %s, Average: %s%n" +
                              "Total Time Statistics:%n    Minimum: %s, Maximum: %s, Median: %s, Average: %s%n" +
                              "Passengers still on trains: %s%n" +
                              "Passengers still at stations: %s%n",
                              this.minWaitTime,
                              this.maxWaitTime,
                              this.medWaitTime,
                              this.avgWaitTime,
                              this.minRideTime,
                              this.maxRideTime,
                              this.medRideTime,
                              this.avgRideTime,
                              this.minTotalTime,
                              this.maxTotalTime,
                              this.medTotalTime,
                              this.avgTotalTime,
                              this.stillOnTrains,
                              this.stillAtStations ) ;

        } // end results()


    /**
     * Uses list of wait times to determine minimum, maximum, median, and average
     * wait times.
     */
    private void calculateWaitTimeStats()
        {
        getWaitTimes() ; // gets valid wait times and stores in a list

        Object[] tempArray = this.waitTimes.toArray() ;
        Arrays.sort( tempArray ) ; // sorts array from smallest to largest times

        // minimum wait time will be first index in array
        this.minWaitTime = (int) tempArray[ 0 ] ;

        // maximum wait time will be last index in array
        this.maxWaitTime = (int) tempArray[ tempArray.length - 1 ] ;

        // assigns median wait time
        this.medWaitTime = calculateMedian( this.waitTimes ) ;

        // assigns average wait time
        this.avgWaitTime = calculateAverage( this.waitTimes ) ;

        } // end calculateWaitTimeStats()


    /**
     * Uses list of ride times to determine minimum, maximum, median, and average
     * ride times.
     */
    private void calculateRideTimeStats()
        {
        getRideTimes() ; // gets valid ride times and stores in a list

        Object[] tempArray = this.rideTimes.toArray() ;
        Arrays.sort( tempArray ) ; // sorts array from smallest to largest times

        // minimum ride time will be first index in array
        this.minRideTime = (int) tempArray[ 0 ] ;

        // maximum ride time will be last index in array
        this.maxRideTime = (int) tempArray[ tempArray.length - 1 ] ;

        // assigns median ride time
        this.medRideTime = calculateMedian( this.rideTimes ) ;

        // assigns average ride time
        this.avgRideTime = calculateAverage( this.rideTimes ) ;

        } // end calculateRideTimeStats()


    /**
     * Uses list of total times to determine minimum, maximum, median, and average
     * total times.
     */
    private void calculateTotalTimeStats()
        {
        getTotalTimes() ; // gets valid total times and stores in a list

        Object[] tempArray = this.totalTimes.toArray() ;
        Arrays.sort( tempArray ) ; // sorts array from smallest to largest times

        // minimum total time will be first index in array
        this.minTotalTime = (int) tempArray[ 0 ] ;

        // maximum total time will be last index in array
        this.maxTotalTime = (int) tempArray[ tempArray.length - 1 ] ;

        // assigns median total time
        this.medTotalTime = calculateMedian( this.totalTimes ) ;

        // assigns average total time
        this.avgTotalTime = calculateAverage( this.totalTimes ) ;

        } // end calculateTotalTimeStats()


    /**
     * Adds valid wait times to array list
     */
    private void getWaitTimes()
        {
        // loops through list of passengers
        for ( Passenger element : this.listOfPassengers )
            {
            // System.out.println( element.getTimeWaiting() ) ; // DEBUG
            // exit time not being -1 means that the passenger completed their
            // journey so all of their fields have valid and useful values
            if ( element.getTimeExited() != -1 )
                {
                this.waitTimes.add( element.getTimeWaiting() ) ;
                System.out.println( "good" ) ;
                } // end if

            } // end for

        } // end getWaitTimes()


    /**
     * Adds valid ride times to array list
     */
    private void getRideTimes()
        {
        // loops through list of passengers
        for ( Passenger element : this.listOfPassengers )
            {
            // exit time not being -1 means that the passenger completed their
            // journey so all of their fields have valid and useful values
            if ( element.getTimeExited() != -1 )
                {
                this.rideTimes.add( element.getTimeRiding() ) ;
                } // end if

            } // end for

        } // end getRideTimes()


    /**
     * Adds valid total times to array list
     */
    private void getTotalTimes()
        {
        // loops through list of passengers
        for ( Passenger element : this.listOfPassengers )
            {
            // exit time not being -1 means that the passenger completed their
            // journey so all of their fields have valid and useful values
            if ( element.getTimeExited() != -1 )
                {
                this.totalTimes.add( element.getTotalTime() ) ;
                } // end if

            } // end for

        } // end getTotalTimes()


    /**
     * Utility method that determines the median
     *
     * @param list
     *     list to search for median
     * @return median
     */
    private int calculateMedian( ArrayList<Integer> list )
        {
        int result ;

        Object[] tempArray = list.toArray() ;
        Arrays.sort( tempArray ) ; // sorts array from smallest to largest times

        // checks if there is even/odd entries before calculating median
        if ( ( tempArray.length % 2 ) == 0 )
            {
            result = ( (int) tempArray[ tempArray.length / 2 ] +
                       (int) tempArray[ ( tempArray.length / 2 ) - 1 ] ) /
                     2 ;
            } // end if
        else
            {
            result = (int) tempArray[ tempArray.length / 2 ] ;
            } // end else

        return result ;

        } // end calculateMedian()


    /**
     * Utility method that determines the average
     *
     * @param list
     *     list to calculate average
     * @return average
     */
    private int calculateAverage( ArrayList<Integer> list )
        {
        int total = 0 ;

        // loop that calculates total
        for ( Integer element : list )
            {
            total = total + element ;

            } // end for

        // calculates and returns average
        return total / list.size() ;

        } // end calculateAverage()


    /**
     * @param args
     *     not used
     */
    public static void main( String[] args )
        {
        // OPTIONAL to test code

        } // end main()

    }
// end class Statistics