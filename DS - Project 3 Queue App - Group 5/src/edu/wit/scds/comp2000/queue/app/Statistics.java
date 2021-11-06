
package edu.wit.scds.comp2000.queue.app ;

import java.util.ArrayList ;

/**
 * Class to accumulate and manage statistics for train simulation
 *
 * @author Fabio Tran
 * @version 1.0.0 2021-11-03 Initial implementation
 */
public class Statistics
    {

    private int totalCompletedTrips ; // total passengers that completed their trip

    // stores data for platform wait time
    private int minWaitTime ;
    private int maxWaitTime ;
    private int medWaitTime ;
    private ArrayList waitTimes ;

    // stores data for train ride time
    private int minRideTime ;
    private int maxRideTime ;
    private int medRideTime ;
    private ArrayList rideTimes ;

    // stores data for total time
    private int minTotalTime ;
    private int maxTotalTime ;
    private int medTotalTime ;
    private ArrayList totalTimes ;

    /**
     * no-arg constructor
     */
    public Statistics()
        {
        this.totalCompletedTrips = 0 ;

        } // end constructor


    /**
     * Increments total completed trips by 1
     */
    public void updateCompletedTrips()
        {
        this.totalCompletedTrips++ ;
        
        } // end updateCompletedTrips()


    /**
     * Updates minimum, maximum, and median numbers for wait time
     *
     * @param number
     *     number to compare to current min, max, or median
     */
    public void updateWaitTime( int number )
        {
        if ( this.totalCompletedTrips == 0 )
            {
            this.minWaitTime = number ;
            this.maxWaitTime = number ;
            this.medWaitTime = number ;
            }
        else
            {
            this.minWaitTime = minimumComparison( this.minWaitTime, number ) ;
            this.maxWaitTime = minimumComparison( this.maxWaitTime, number ) ;
            }
        
        } // end updateWaitTime()


    /**
     * Updates minimum, maximum, and median numbers for ride time
     * 
     * @param number
     *     number to compare to current min, max, or median
     */
    public void updateRideTime( int number )
        {
        if ( this.totalCompletedTrips == 0 )
            {
            this.minRideTime = number ;
            this.maxRideTime = number ;
            this.medRideTime = number ;
            }
        else
            {
            this.minRideTime = minimumComparison( this.minRideTime, number ) ;
            this.maxRideTime = minimumComparison( this.maxRideTime, number ) ;
            }
        
        } // end updateRideTime()

    
    /**
     * Updates minimum, maximum, and median numbers for total time
     * 
     * @param number
     *     number to compare to current min, max, or median
     */
    public void updateTotalTime( int number )
        {
        if ( this.totalCompletedTrips == 0 )
            {
            this.minTotalTime = number ;
            this.maxTotalTime = number ;
            this.medTotalTime = number ;
            }
        else
            {
            this.minTotalTime = minimumComparison( this.minTotalTime, number ) ;
            this.maxTotalTime = minimumComparison( this.maxTotalTime, number ) ;
            }
        
        } // end updateTotalTime()

    
    /**
     * Compares two numbers to determine which is smaller
     */
    private int minimumComparison( int firstNumber,
                                   int secondNumber )
        {
        if ( secondNumber < firstNumber )
            {
            return secondNumber ;
            }
        return firstNumber ;

        }


    /**
     * Compares two numbers to determine which is larger
     */
    private int maximumComparison( int firstNumber,
                                   int secondNumber )
        {
        if ( secondNumber > firstNumber )
            {
            return secondNumber ;
            }
        return firstNumber ;
        }


    /**
     * Determines the median
     */
    private void medianComparison()
        {
        
        }


    /**
     * @param args
     */
    public static void main( String[] args )
        {
        // TODO Auto-generated method stub

        } // end main()

    }
// end class Statistics