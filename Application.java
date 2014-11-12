import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Font;

import java.io.PrintStream;

import gui.TextAreaOutputStream;

import signal.awt.WindowTransmitter;

import signal.TransmitterThread;
import signal.AsyncTransmitter;
import signal.Receiver;
import signal.Signal;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// APPLICATION CLASS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class Application
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public Application()
    {
        // Create windows
        
        createConsoleWindow();
        createMainWindow();
        
        // Set close flag
        
        close = new Boolean( false );
    }
    
    // CREATE CONSOLE WINDOW --------------------------------------------------
    
    public void createConsoleWindow()
    {
        // Create console window
        
        console = new JFrame();
        console.setTitle( "Console" );
        console.setBounds( 10, 10, 500, 500 );
        
        // Create text area and add to console
        
        textArea = new JTextArea();
        textArea.setFont( new Font( "Monospaced", Font.PLAIN, 14 ) );
        textArea.setEditable( false );
        
        console.add( textArea );
        console.add( new JScrollPane( textArea ) );
        
        // Create output stream and redirect system.out
        
        outputStream = new TextAreaOutputStream( textArea );
        System.setOut( new PrintStream( outputStream, true ) );
    }
    
    // CREATE MAIN WINDOW -----------------------------------------------------
    
    public void createMainWindow()
    {
        // Create main window
        
        mainWindow = new JFrame();
        mainWindow.setTitle( "Main Window" );
        mainWindow.setBounds( 100, 100, 500, 500 );
        
        // Create window transmitter
        
        windowTransmitter = new WindowTransmitter();
        
        // Register receivers
        
        windowTransmitter.register
        (
            new Receiver()
            {
                public void receive( Signal signal )
                {
                    System.out.print( "Main window activated.\n" );
                }
            },
            WindowTransmitter.WINDOW_ACTIVATED
        );
        
        windowTransmitter.register
        (
            new Receiver()
            {
                public void receive( Signal signal )
                {
                    System.out.print( "Main window closing.\n" );
                    
                    synchronized( close )
                    {
                        close.notify();
                    }
                }
            },
            WindowTransmitter.WINDOW_CLOSING
        );
        
        // Add transmitter as listener
        
        mainWindow.addWindowListener( windowTransmitter );
    }
    
    // START ------------------------------------------------------------------
    
    public void start()
    {
        // Show windows
        
        console.setVisible( true );
        mainWindow.setVisible( true );
        
        // Create and start transmitter threads
        
        TransmitterThread thread1 = AsyncTransmitter.createThread();
        TransmitterThread thread2 = AsyncTransmitter.createThread();
        TransmitterThread thread3 = AsyncTransmitter.createThread();
        
        thread1.start();
        thread2.start();
        thread3.start();
        
        // Wait for close
        
        try
        {
            synchronized( close )
            {
                close.wait();
            }
        }
        catch( Throwable t )
        {
            System.out.print( "Application wait state interrupted.\n" );
        }
        
        // Dispose of windows
        
        mainWindow.dispose();
        console.dispose();
        
        // Stop transmitter threads
        
        try
        {
            thread1.join();
            thread2.join();
            thread3.join();
        }
        catch( Throwable t ){}
    }
    
    // MAIN -------------------------------------------------------------------
    
    public static void main( String[] argv )
    {
        Application app = new Application();
        app.start();
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    private JFrame                     console;
    private JTextArea                 textArea;
    private TextAreaOutputStream     outputStream;
    
    private JFrame                    mainWindow;
    private WindowTransmitter        windowTransmitter;
    
    private Boolean                    close;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
