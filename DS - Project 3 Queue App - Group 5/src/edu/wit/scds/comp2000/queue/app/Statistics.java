
package edu.wit.scds.comp2000.queue.app ;

import java.util.ArrayList ;
import java.util.Arrays ;

/**
 * Class to manage statistics for train simulation. Calculates the minimum, maximum,
 * median, and average for wait, ride, and total times. In addition, it will also
 * report the total number of remaining passengers at the stations or on the trains
 * at the end of the simulation (passengers who did not complete their journey).
 *
 * @author Fabio Tran
 * @version 1.0.0 2021-11-03 Initial implementation
 */
public class Statistics
    {

    /** indicates that the time has no meaningful value */
    public static int UNSPECIFIED = -1 ;

    /** stores valid wait, ride, and total times */
    private ArrayList<Integer> waitTimes ;
    private ArrayList<Integer> rideTimes ;
    private ArrayList<Integer> totalTimes ;

    /**
     * stores how many passengers are still at any station or train at the end of the
     * simulation
     */
    private int stillAtStations ;
    private int stillOnTrains ;

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

        this.waitTimes = new ArrayList<>() ;
        this.rideTimes = new ArrayList<>() ;
        this.totalTimes = new ArrayList<>() ;

        this.stillAtStations = UNSPECIFIED ;
        this.stillOnTrains = UNSPECIFIED ;

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
     * Calculates remaining passengers on trains and at stations. Also calculates
     * final wait, ride, and total time statistics since this method will be called
     * when the simulation has concluded.
     *
     * @param stations
     *     array of stations from simulation
     * @param trains
     *     array of trains from simulation
     */
    public void calculateStatistics( Station[] stations,
                                     Train[] trains )
        {
        calculateStillAtStations( stations ) ; // remaining passengers at stations
        calculateStillOnTrains( trains ) ; // remaining passengers on trains

        /** assigns all field values their appropriate statistical value */
        // calculateWaitTimeStats() ;
        // calculateRideTimeStats() ;
        // calculateTotalTimeStats() ;
        // calculateWaitTimeStats() ;
        // calculateRideTimeStats() ;
        // calculateTotalTimeStats() ;

        } // end calculateStatistics()


    /**
     * Calculates how many passengers are still at stations at end of the simulation
     *
     * @param stations
     *     array of stations from the simulation
     */
    public void calculateStillAtStations( Station[] stations )
        {
        int result = 0 ;

        for ( Station station : stations )
            {
            result = result + station.getPassengerCount() ;
            } // end for

        this.stillAtStations = result ;

        } // end calculateStillAtStations()


    /**
     * Calculates how many passengers are still on trains at end of the simulation
     *
     * @param trains
     *     array of trains from the simulation
     */
    public void calculateStillOnTrains( Train[] trains )
        {
        int result = 0 ;

        for ( Train train : trains )
            {
            result = result + train.getPassengerCount() ;
            } // end for

        this.stillOnTrains = result ;

        } // end calculateStillOnTrains()


    /**
     * Retrieves data fields with regards to times from passenger
     *
     * @param passenger
     *     passenger to get times from
     */
    public void getTimes( Passenger passenger )
        {
        // all of passenger's time values are meaningful if they exited simulation
        if ( passenger.getTimeExited() != UNSPECIFIED )
            {
            this.waitTimes.add( passenger.getTimeWaiting() ) ;
            this.rideTimes.add( passenger.getTimeRiding() ) ;
            this.totalTimes.add( passenger.getTotalTime() ) ;
            } // end if

        } // end getTimes()


    /**
     * Displays all statistics into string form
     *
     * @return string
     */
    public String results()
        {
        // returns statistics in string format
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
     * Utility method that determines the median
     *
     * @param list
     *     list to search for median
     * @return median
     */
    private static int calculateMedian( ArrayList<Integer> list )
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
    private static int calculateAverage( ArrayList<Integer> list )
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