package signal;

import java.util.LinkedList;
import java.util.Queue;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ASYNCHRONOUS TRANSMITTER CLASS +++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public abstract class AsyncTransmitter
implements Transmitter
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // NESTED CLASSES +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // SYNCHRONOUS RECEIVER CLASS ---------------------------------------------
    
    private static class SyncReceiver
    implements Receiver
    {
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // RECEIVE ------------------------------------------------------------
        
        @Override
        public void receive( Signal signal )
        {
            try
            {
                signal.getReceiver().receive( signal );
            }
            catch( Throwable t ){}
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
    
    // ASYNCHRONOUS RECEIVER CLASS --------------------------------------------
    
    private static class AsyncReceiver
    implements Receiver
    {
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // RECEIVE ------------------------------------------------------------
        
        @Override
        public void receive( Signal signal )
        {
            synchronized( queue )
            {
                // Add signal to queue
                
                queue.offer( signal );
                
                // Notify any waiting threads
                
                queue.notifyAll();
            }
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
    
    // ASYNCHRONOUS TRANSMITTER THREAD CLASS ----------------------------------
    
    private static class AsyncTransmitterThread
    implements TransmitterThread, Runnable
    {
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // CONSTRUCTORS -------------------------------------------------------
        
        public AsyncTransmitterThread()
        {
            // Create new thread
            
            thread = new Thread( this );
            stop = new Boolean( false );
        }
        
        // START --------------------------------------------------------------
        
        @Override
        public void start()
        {
            // Start the thread object
            
            thread.start();
        }
        
        // JOIN ---------------------------------------------------------------
        
        @Override
        public void join()
        throws InterruptedException
        {
            // Set stop flag
            
            synchronized( stop )
            {
                stop = true;
            }
            
            // Signal thread if waiting
            
            synchronized( AsyncTransmitter.queue )
            {
                AsyncTransmitter.queue.notify();
            }
            
            // Join thread object
            
            thread.join();
        }
        
        // RUN ----------------------------------------------------------------
        
        @Override
        public void run()
        {
            // Synchronise on thread counter
            
            synchronized( AsyncTransmitter.threadCount )
            {
                // If no other threads exist, change receiver
                
                if( AsyncTransmitter.threadCount < 1 )
                {
                    AsyncTransmitter.receiver = AsyncTransmitter.asyncReceiver;
                }
                
                // Increment thread count
                
                AsyncTransmitter.threadCount = 1;
            }
            
            // Set stop flag
            
            synchronized( stop )
            {
                stop = false;
            }
            
            // Enter main loop
                
            Signal signal = null;
            
            boolean active = true;
            
            while( active )
            {
                // Get signal from queue
                
                synchronized( AsyncTransmitter.queue )
                {
                    signal = AsyncTransmitter.queue.poll();
                }
                
                // If no signal was returned, wait on queue
                    
                if( signal == null )
                {
                    try
                    {
                        AsyncTransmitter.queue.wait();
                    }
                    catch( Throwable t ){}
                }
                else
                {
                    // Signal was returned, transmit it
                    
                    try
                    {
                        signal.getReceiver().receive( signal );
                    }
                    catch( Throwable t ){}
                }
                
                // Check stop condition
                
                synchronized( stop )
                {
                    if( stop )
                    {
                        active = false;
                    }
                }
            }
            
            // Synchronise on thread counter
            
            synchronized( AsyncTransmitter.threadCount )
            {
                // Decrement thread count
                
                AsyncTransmitter.threadCount -= 1;
                
                // If no other threads exist, change receiver
                
                if( AsyncTransmitter.threadCount < 1 )
                {
                    AsyncTransmitter.threadCount = 0;
                    AsyncTransmitter.receiver = AsyncTransmitter.syncReceiver;
                }
            }
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        private Thread         thread;
        private Boolean     stop;
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CREATE THREAD ----------------------------------------------------------
    
    public static TransmitterThread createThread()
    {
        return new AsyncTransmitterThread();
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // TRANSMIT ---------------------------------------------------------------
    
    protected void transmit( Signal signal )
    {
        receiver.receive( signal );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    private static Queue< Signal >     queue;
    private static Integer             threadCount;
    private static Receiver            receiver;
    private static SyncReceiver        syncReceiver;
    private static AsyncReceiver    asyncReceiver;
    
    static
    {
        queue             = new LinkedList< Signal >();
        threadCount     = new Integer( 0 );
        syncReceiver     = new SyncReceiver();
        asyncReceiver    = new AsyncReceiver();
        receiver        = syncReceiver;
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
