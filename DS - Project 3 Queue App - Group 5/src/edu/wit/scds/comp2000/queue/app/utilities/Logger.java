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
import java.io.IOException ;
import java.io.PrintStream ;
import java.nio.file.Files ;
import java.nio.file.Path ;
import java.util.Calendar ;


/**
 * Logger utility.
 * <p>
 * NOTE: You may use this class, with or without modification, in your Comp 2000,
 * Queue application/Train Simulation solution. You must retain all authorship
 * comments. If you modify this, add your authorship and modification history tags
 * after the existing tags.
 * 
 * @author Dave Rosenberg
 * @version 1.0.0 initial version
 * @version 1.1.0 track enhancement to Configuration
 * @version 2.0.0 2021-10-30
 *     <ul>
 *     <li>switch from functionality from static to instance</li>
 *     <li>clean up and enhance functionality</li>
 *     <li>switch log from PrintWriter to PrintStream</li>
 *     <li>move log to {@code logs} folder</li>
 *     <li>append timestamp to log file name to enable multiple logs to exist</li>
 *     <li>rename {@code write()} to {@code printf()}
 *     <li>add 2-arg {@code printf()}</li>
 *     </ul>
 */
public final class Logger
    {

    // data fields
    private PrintStream logStream ;


    /**
     * @param baseLogFilename
     *     base for the log filename
     * @throws IOException
     *     if the logs folder doesn't already exist and can't be created
     */
    public Logger( final String baseLogFilename ) throws IOException
        {
        create( baseLogFilename ) ;
        
        }   // end 1-arg constructor


    /**
     * Creates and opens the log file TrainSimulation-{timestamp}.log in the .logs
     * folder.
     * 
     * @param baseLogFilename
     *     base for the log filename
     * @throws IOException
     *     if the logs folder doesn't already exist and can't be created
     */
    public void create( final String baseLogFilename ) throws IOException
        {
        // create the log - name is TrainSimulation-yyyy-mm-dd-hhmmss.log
        Calendar now = Calendar.getInstance() ;
        String timestamp = String.format( "%TF-%<TH%<TM%<TS", now ) ;

        Path logsPath = new File( "./logs" ).toPath().toAbsolutePath().normalize() ;

        String logFilename = String.format( "%s%c%s-%s.log",
                                               logsPath,
                                               File.separatorChar,
                                               baseLogFilename,
                                               timestamp ) ;

        // create the logs folder (if necessary) and the log file
        // if either operation fails, a message will display on the console then the
        // exception will be rethrown
        try
            {
            logsPath = Files.createDirectories( logsPath ) ;

            this.logStream = new PrintStream( logFilename ) ;
            System.out.printf( "Log in: %s%n%n", logFilename ) ;
            }
        catch ( FileNotFoundException e )
            {
            System.err.printf( "Unable to create log file: %s%n\t%s%n",
                               e.getMessage(),
                               logFilename ) ;
            
            throw e ;
            }
        catch ( IOException e )
            {
            System.err.printf( "Unable to create log folder: %s%n\t%s%n",
                               e.getMessage(),
                               logsPath ) ;
            
            throw e ;
            }

        } // end create()


    /**
     * Closes the log file, releasing all allocated resources.
     */
    public void close()
        {
        if ( this.logStream != null )
            {
            this.logStream.close() ;
            this.logStream = null ;
            }
        
        } // end close()


    /**
     * Writes all of the configuration parameters into the log
     * 
     * @param theConfiguration the configuration for this execution
     */
    public void logConfiguration( Configuration theConfiguration )
        {
        // introduce the information
        printf( "%n----------%nConfiguration:%n", "" ) ;

        // let Configuration format the output
        printf( theConfiguration.toString() ) ;

        // separate the configuration from the rest of the log
        printf( "----------%n", "" ) ;

        } // end logConfiguration()


    /**
     * Appends the provided text to the log, including a terminating newline.
     * 
     * @param logEntry the text to append to the log.
     */
    public void printf( String logEntry )
        {
        this.logStream.printf( logEntry ) ;
        
        } // end write()


    /**
     * Appends the provided text to the log, including a terminating newline.
     * 
     * @param logFormat
     *     the format specifier
     * @param logData
     *     the arguments used by the logFormat
     */
    public void printf( String logFormat, Object... logData )
        {
        this.logStream.printf( logFormat, logData ) ;
        
        } // end write()


    /**
     * Test driver.
     * 
     * @param args
     *     -unused-
     * @throws IOException
     *     if the logs folder doesn't exist and can't be created
     */
    public static void main( String[] args ) throws IOException
        {
        System.out.printf( "Starting Logger testing%n%n" ) ;

        System.out.printf( "create the log and insert an intro message%n" ) ;
        
        Logger testLogger = null ;
        try
            {
            testLogger = new Logger( "LoggerTest" ) ;
            testLogger.printf( "Start Logger test" ) ;

            // we'll write the configuration to the log file
            System.out.printf( "dump the configuration into the log%n" ) ;
            testLogger.logConfiguration( new Configuration() ) ;

            System.out.printf( "insert concluding message and close the log%n" ) ;
            testLogger.printf( "Finish Logger test" ) ;
            }
        finally
            {
            if ( testLogger != null )
                {
                testLogger.close() ;
                }
            }

        System.out.printf( "%nFinished Logger testing%n" ) ;
        
        } // end main()

    }  // end class Logger
